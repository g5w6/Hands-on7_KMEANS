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

    public KMeans(int k, List<Customer> customers) {
        this.k = k;
        this.customers = customers;
    }

    public void run() {
        initializeClusters();
        boolean centroidsChanged;
        do {
            assignCustomersToClusters();
            centroidsChanged = updateCentroids();
            iterationCount++; // Increment the iteration count
            double precision = calculatePrecision();
            
            // Print the first iteration
            if (!firstIterationPrinted) {
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
        System.out.println('\n'+"Total number of iterations: " + iterationCount);
        System.out.println('\n'+"Best iteration: " + bestIteration + " with precision: " + bestPrecision + "%");
    }

    public double getFinalPrecision() {
        return bestPrecision;
    }

    public void printFinalResults(double precision) {
        System.out.println("Final precision: " + precision + "%");
        printResults();
    }

    private void initializeClusters() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster(customers.get(random.nextInt(customers.size()))));
        }
    }

    private void assignCustomersToClusters() {
        clusters.forEach(Cluster::clearCustomers);
        for (Customer customer : customers) {
            Cluster nearestCluster = clusters.stream()
                    .min((c1, c2) -> Double.compare(c1.calculateDistance(customer), c2.calculateDistance(customer)))
                    .orElse(null);
            if (nearestCluster != null) nearestCluster.addCustomer(customer);
        }
    }

    private boolean updateCentroids() {
        return clusters.stream().map(Cluster::updateCentroid).reduce(false, (a, b) -> a || b);
    }

    private double calculatePrecision() {
        double idealClusterSize = (double) customers.size() / k;
        double totalDeviation = clusters.stream()
                .mapToDouble(cluster -> Math.abs(cluster.getCustomers().size() - idealClusterSize))
                .sum();
        double maxDeviation = customers.size() * (k - 1) / k;
        return 100.0 * (1.0 - totalDeviation / maxDeviation);
    }

    public void printResults() {
        System.out.println("Number of clusters: " + k);
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            System.out.println("Cluster " + i + " centroid: " + cluster.getCentroid());
            System.out.println("Majority Gender in this cluster: " + cluster.getMajorityGender());
            System.out.println("Customers in this cluster: " + cluster.getCustomers().size());
        }
    }
}