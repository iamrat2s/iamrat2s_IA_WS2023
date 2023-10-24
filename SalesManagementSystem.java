import java.util.Scanner;
import org.bson.Document;
import com.mongodb.client.*;
import org.bson.types.ObjectId;

public class SalesManagementSystem {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> salesmenCollection;
    private final MongoCollection<Document> performanceRecordsCollection;

    public SalesManagementSystem() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("sales_management");
        salesmenCollection = database.getCollection("salesmen");
        performanceRecordsCollection = database.getCollection("performance_records");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Sales Management System Menu:");
            System.out.println("1. Add SalesMan");
            System.out.println("2. Add Performance Record");
            System.out.println("3. View SalesMan");
            System.out.println("4. View Performance Records");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addSalesMan(scanner);
                    break;
                case 2:
                    addPerformanceRecord(scanner);
                    break;
                case 3:
                    viewSalesMan(scanner);
                    break;
                case 4:
                    viewPerformanceRecords(scanner);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    mongoClient.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void addSalesMan(Scanner scanner) {
        System.out.print("Enter SalesMan's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter SalesMan's last name: ");
        String lastName = scanner.nextLine();

        Document salesManDocument = new Document("firstname", firstName)
            .append("lastname", lastName);

        salesmenCollection.insertOne(salesManDocument);

        System.out.println("SalesMan added successfully.");
    }

    private void addPerformanceRecord(Scanner scanner) {
        System.out.print("Enter SalesMan's ID: ");
        String salesManId = scanner.nextLine();

        System.out.print("Enter performance record details: ");
        String performanceDetails = scanner.nextLine();

        Document performanceRecordDocument = new Document("salesman_id", new ObjectId(salesManId))
            .append("details", performanceDetails);

        performanceRecordsCollection.insertOne(performanceRecordDocument);

        System.out.println("Performance record added successfully.");
    }

    private void viewSalesMan(Scanner scanner) {
        System.out.print("Enter SalesMan's ID: ");
        String salesManId = scanner.nextLine();

        Document salesManDocument = salesmenCollection.find(new Document("_id", new ObjectId(salesManId))).first();

        if (salesManDocument != null) {
            System.out.println("SalesMan Details:");
            System.out.println("ID: " + salesManDocument.getObjectId("_id"));
            System.out.println("First Name: " + salesManDocument.getString("firstname"));
            System.out.println("Last Name: " + salesManDocument.getString("lastname"));
        } else {
            System.out.println("SalesMan not found.");
        }
    }

    private void viewPerformanceRecords(Scanner scanner) {
        System.out.print("Enter SalesMan's ID: ");
        String salesManId = scanner.nextLine();

        FindIterable<Document> performanceRecords = performanceRecordsCollection.find(new Document("salesman_id", new ObjectId(salesManId)));

        System.out.println("Performance Records for SalesMan ID " + salesManId + ":");
        for (Document record : performanceRecords) {
            System.out.println("Record ID: " + record.getObjectId("_id"));
            System.out.println("Details: " + record.getString("details"));
        }
    }

    public static void main(String[] args) {
        SalesManagementSystem system = new SalesManagementSystem();
        system.run();
    }
}
