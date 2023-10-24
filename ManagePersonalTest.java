import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagePersonalTest {
    private MongoDatabase database;
    private ManagePersonal managePersonal;

    @BeforeEach
    void setUp() {
        // Initialize and connect to a test MongoDB database
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("test_database");

        // Create an instance of the implementation class
        managePersonal = new ManagePersonalImpl(database);
    }

    @AfterEach
    void tearDown() {
        // Clean up and drop the test database after each test
        database.drop();
    }

    @Test
    void testCreateAndReadSalesMan() {
        // Create a SalesMan record
        SalesMan salesMan = new SalesMan("John", "Doe", 1);
        managePersonal.createSalesMan(salesMan);

        // Read the SalesMan record
        SalesMan retrievedSalesMan = managePersonal.readSalesMan(1);

        // Verify that the retrieved SalesMan matches the original one
        assertEquals(salesMan, retrievedSalesMan);
    }

    @Test
    void testCreateAndReadPerformanceRecord() {
        // Create a SalesMan record
        SalesMan salesMan = new SalesMan("Jane", "Smith", 2);
        managePersonal.createSalesMan(salesMan);

        // Create an EvaluationRecord for the SalesMan
        EvaluationRecord evaluationRecord = new EvaluationRecord(/* Provide necessary data */);
        managePersonal.addPerformanceReord(evaluationRecord, 2);

        // Read the EvaluationRecord for the SalesMan
        EvaluationRecord retrievedEvaluationRecord = managePersonal.readEvaluationRecords(2);

        // Verify that the retrieved EvaluationRecord matches the original one
        assertEquals(evaluationRecord, retrievedEvaluationRecord);
    }

    @Test
    void testNoDataDuplicates() {
        // Create a SalesMan record
        SalesMan salesMan = new SalesMan("Alice", "Johnson", 3);
        managePersonal.createSalesMan(salesMan);

        // Attempt to create the same SalesMan record again
        managePersonal.createSalesMan(salesMan);

        // Read the SalesMan record
        SalesMan retrievedSalesMan = managePersonal.readSalesMan(3);

        // Verify that no data duplicates exist
        assertEquals(salesMan, retrievedSalesMan);
    }
}
