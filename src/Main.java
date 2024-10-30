import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mario GGS\\Downloads\\AviñaUnidad1_MarioGomez\\Hands-on7_KMEANS\\data\\Mall_Customers.csv";
        int dataLimit = 200; // Limitar a las primeras 100 líneas
        List<Customer> customers = csvReader.readCSV(filePath, dataLimit);

        int numberOfClusters = 4; // Reducir el número de clústeres a 3
        KMeans kMeans = new KMeans(numberOfClusters, customers);
        kMeans.run();
        double finalPrecision = kMeans.getFinalPrecision();
        kMeans.printFinalResults(finalPrecision);
    }
}