import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    private final int k;
    private final List<Customer> customers;
    private final List<Cluster> clusters = new ArrayList<>();
    private int iterationCount = 0;
    private double bestPrecision = 0;
    private int bestIteration = 0;
    private boolean firstIterationPrinted = false;


    //Se iniciliza la clase KMeans con el número de clusters (k) y la lista de clientes (customers)
    public KMeans(int k, List<Customer> customers) {
        this.k = k;
        this.customers = customers;
    }

    //Se ejecuta el algoritmo KMeans, se inicializan los clusters, se asignan los clientes a los clusters, se actualizan los centroides y se calcula la precisión
    public void run(boolean silent) {
        initializeClusters();
        boolean centroidsChanged;
        do {
            assignCustomersToClusters(); // Assign customers to clusters
            centroidsChanged = updateCentroids(); // Update centroids
            iterationCount++; // Increment the iteration count
            double precision = calculatePrecision(); // Calculate the precision
            
            // Print the first iteration
            if (!silent && !firstIterationPrinted) {
                System.out.println("First iteration results:");
                printResults();
                firstIterationPrinted = true;
            }
            
            // Track the best iteration
            if (precision > bestPrecision) {
                bestPrecision = precision;
                bestIteration = iterationCount;
            }
        } while (centroidsChanged);
    
        // Print the number of iterations and the best iteration
        if (!silent) {
            System.out.println('\n'+"Total number of iterations: " + iterationCount);
            System.out.println('\n'+"Best iteration: " + bestIteration + " with precision: " + bestPrecision + "%");
        }
    }

    //Se obtiene la precisión final
    public double getFinalPrecision() {
        return bestPrecision;
    }

    //Se imprime la precisión final y los resultados de los clusters
    public void printFinalResults(double precision) {
        System.out.println("Final precision: " + precision + "%");
        printResults();
    }

    //Se inicializan los clusters con los centroides aleatorios
    private void initializeClusters() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster(customers.get(random.nextInt(customers.size()))));
        }
    }

    //Limpia los clusters y asigna a los clientes mas cercanos a los clusters
    private void assignCustomersToClusters() {
        clusters.forEach(Cluster::clearCustomers);
        for (Customer customer : customers) {
            Cluster nearestCluster = clusters.stream()
                    .min((c1, c2) -> Double.compare(c1.calculateDistance(customer), c2.calculateDistance(customer)))
                    .orElse(null);
            if (nearestCluster != null) nearestCluster.addCustomer(customer);
        }
    }

    //Actualiza los centroides de los clusters y devuelve true si el centroide cambio
    private boolean updateCentroids() {
        return clusters.stream().map(Cluster::updateCentroid).reduce(false, (a, b) -> a || b);
    }

    //Calcula la precisión basandose en el tamaño de los clusters
    private double calculatePrecision() {
        double idealClusterSize = (double) customers.size() / k;
        double totalDeviation = clusters.stream()
                .mapToDouble(cluster -> Math.abs(cluster.getCustomers().size() - idealClusterSize))
                .sum();
        double maxDeviation = customers.size() * (k - 1) / k;
        return 100.0 * (1.0 - totalDeviation / maxDeviation); // se calcula la precisión para general resultado en porcentaje
    }

    //Imprime los resultados de los clusters
    public void printResults() {
        System.out.println("Number of clusters: " + k);
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            System.out.println("Cluster " + i + " centroid: " + cluster.getCentroid());
            System.out.println("Majority Gender in this cluster: " + cluster.getMajorityGender());
            System.out.println("Customers in this cluster: " + cluster.getCustomers().size());
        }
    }

    private double calculateWCSS() {
        return clusters.stream()
                .mapToDouble(cluster -> cluster.getCustomers().stream()
                        .mapToDouble(customer -> Math.pow(distance(customer, cluster.getCentroid()), 2))
                        .sum())
                .sum();
    }
    
    private double distance(Customer customer, Customer centroid) {
        // Implementa la función de distancia adecuada (por ejemplo, Euclidiana)
        double ageDiff = customer.getAge() - centroid.getAge();
        double incomeDiff = customer.getAnnualIncome() - centroid.getAnnualIncome();
        double scoreDiff = customer.getSpendingScore() - centroid.getSpendingScore();
        return Math.sqrt(ageDiff * ageDiff + incomeDiff * incomeDiff + scoreDiff * scoreDiff);
    }

    public int elbowMethod(int maxK) {
        List<Double> wcssValues = new ArrayList<>();
        for (int i = 1; i <= maxK; i++) {
            KMeans kMeans = new KMeans(i, customers);
            kMeans.run(true); // Ejecutar en modo silencioso
            double wcss = kMeans.calculateWCSS();
            wcssValues.add(wcss);
        }
    
        // Encuentra el punto donde la variación en WCSS varía muy poco
        int optimalK = 1;
        double minDiff = Double.MAX_VALUE;
        for (int i = 1; i < wcssValues.size() - 1; i++) {
            double diff = Math.abs(wcssValues.get(i) - wcssValues.get(i + 1));
            if (diff < minDiff) {
                minDiff = diff;
                optimalK = i + 1;
            }
        }
    
        return optimalK;
    }
}