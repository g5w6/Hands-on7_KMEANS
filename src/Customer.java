
import java.util.Objects;

public class Customer {
    private final int customerId;
    private final String gender;
    private final int age;
    private final int annualIncome;
    private final int spendingScore;

    public Customer(int customerId, String gender, int age, int annualIncome, int spendingScore) {
        this.customerId = customerId;
        this.gender = gender;
        this.age = age;
        this.annualIncome = annualIncome;
        this.spendingScore = spendingScore;
    }

    public int getId() {
        return customerId;
    }

    public int getAge() {
        return age;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public int getSpendingScore() {
        return spendingScore;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return age == customer.age && annualIncome == customer.annualIncome && spendingScore == customer.spendingScore && gender.equals(customer.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, age, annualIncome, spendingScore);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "age=" + age +
                ", annualIncome=" + annualIncome +
                ", spendingScore=" + spendingScore +
                ", gender='" + gender + '\'' +
                '}';
    }
}