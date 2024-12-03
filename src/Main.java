import java.util.List;

//Clase principal que ejecuta el algoritmo KMeans
public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mario GGS\\Downloads\\AviñaUnidad1_MarioGomez\\Hands-on7_KMEANS\\data\\Mall_Customers.csv";
        int dataLimit = 50; // Limitar a las primeras 50 líneas
        System.out.println("Leyendo archivo CSV...");
        List<Customer> customers = csvReader.readCSV(filePath, dataLimit);
        System.out.println("Archivo CSV leido. Numero de clientes: " + customers.size());

        int maxK = 10; // Máximo número de clústers a considerar
        KMeans kMeans = new KMeans(1, customers); // Inicializa con k=1 solo para usar el método elbowMethod
        int optimalK = kMeans.elbowMethod(maxK);
        System.out.println("Numero optimo de clusters sugerido: " + optimalK + "\n");

        kMeans = new KMeans(optimalK, customers);
        kMeans.run(false); // Ejecutar en modo normal para imprimir resultados
        kMeans.printResults();
    }
}