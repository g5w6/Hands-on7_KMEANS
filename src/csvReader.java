import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvReader {
    public static List<Customer> readCSV(String filePath, int limit) {
        List<Customer> customers = new ArrayList<>();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null && count < limit) {
                String[] values = line.split(",");
                customers.add(new Customer(
                        Integer.parseInt(values[0]),
                        values[1],
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4])
                ));
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from CSV file: " + e.getMessage());
        }
        return customers;
    }
}