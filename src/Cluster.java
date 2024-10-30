import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
    private Customer centroid;
    private final List<Customer> customers = new ArrayList<>();

    public Cluster(Customer centroid) {
        this.centroid = centroid;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void clearCustomers() {
        customers.clear();
    }

    public Customer getCentroid() {
        return centroid;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public double calculateDistance(Customer customer) {
        double ageDiff = centroid.getAge() - customer.getAge();
        double incomeDiff = centroid.getAnnualIncome() - customer.getAnnualIncome();
        double scoreDiff = centroid.getSpendingScore() - customer.getSpendingScore();
        return Math.sqrt(ageDiff * ageDiff + incomeDiff * incomeDiff + scoreDiff * scoreDiff);
    }

    public boolean updateCentroid() {
        if (customers.isEmpty()) return false;
        int sumAge = 0, sumIncome = 0, sumScore = 0;
        for (Customer customer : customers) {
            sumAge += customer.getAge();
            sumIncome += customer.getAnnualIncome();
            sumScore += customer.getSpendingScore();
        }
        Customer newCentroid = new Customer(0, "", sumAge / customers.size(), sumIncome / customers.size(), sumScore / customers.size());
        boolean changed = !newCentroid.equals(centroid);
        centroid = newCentroid;
        return changed;
    }

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