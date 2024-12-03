import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
    private Customer centroid; // Centroid of the cluster
    private final List<Customer> customers = new ArrayList<>(); // List of customers in the cluster

    //Inicializa un nuevo cluster con un centroide dado
    public Cluster(Customer centroid) {
        this.centroid = centroid;
    }

    //Añade un cliente a la lista de clientes del cluster
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Elimina todos los clientes de la lista, se utiliza en cada iteracion del algoritmo KMeans para reasignar clientes
    public void clearCustomers() {
        customers.clear();
    }

    //Devuelve el centroide del cluster
    public Customer getCentroid() {
        return centroid;
    }

    //Devuelve la lista de clientes del cluster
    public List<Customer> getCustomers() {
        return customers;
    }

    //Calcula la distancia ecuclidiana entre el centroide del cluster y un cliente dado
    public double calculateDistance(Customer customer) {
        double ageDiff = centroid.getAge() - customer.getAge();
        double incomeDiff = centroid.getAnnualIncome() - customer.getAnnualIncome();
        double scoreDiff = centroid.getSpendingScore() - customer.getSpendingScore();
        return Math.sqrt(ageDiff * ageDiff + incomeDiff * incomeDiff + scoreDiff * scoreDiff);
    }

    //Actualiza el centroide del cluster con la media de los clientes del cluster
    public boolean updateCentroid() {
        if (customers.isEmpty()) return false;
        int sumAge = 0, sumIncome = 0, sumScore = 0;
        for (Customer customer : customers) {
            sumAge += customer.getAge();
            sumIncome += customer.getAnnualIncome();
            sumScore += customer.getSpendingScore();
        }
        // Create a new centroid with the average values
        Customer newCentroid = new Customer(0, "", sumAge / customers.size(), sumIncome / customers.size(), sumScore / customers.size());
        boolean changed = !newCentroid.equals(centroid);
        centroid = newCentroid;
        return changed;
    }

    //Devuelve el genero con mayor cantidad de clientes en el cluster, se utiliza hashmap para contar la cantidad de generos
    public String getMajorityGender() {
        Map<String, Integer> genderCount = new HashMap<>();
        for (Customer customer : customers) {
            genderCount.put(customer.getGender(), genderCount.getOrDefault(customer.getGender(), 0) + 1);
        }
        return genderCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Data");
    }
    
}