import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ManagePersonalImpl implements ManagePersonal {
    private final MongoCollection<Document> salesmenCollection;
    private final MongoCollection<Document> performanceRecordsCollection;

    public ManagePersonalImpl(MongoDatabase database) {
        // Initialize the collections
        salesmenCollection = database.getCollection("salesmen");
        performanceRecordsCollection = database.getCollection("performance_records");
    }

    // Implement the missing CRUD methods
    @Override
    public void updateSalesMan(int sid, SalesMan record) {
        Bson filter = Filters.eq("id", sid);
        Document updateDoc = new Document("$set", record.toDocument());
        salesmenCollection.updateOne(filter, updateDoc);
    }

    @Override
    public void deleteSalesMan(int sid) {
        Bson filter = Filters.eq("id", sid);
        salesmenCollection.deleteOne(filter);
    }

    @Override
    public void addPerformanceRecord(EvaluationRecord record, int sid) {
        Document performanceDoc = record.toDocument();
        performanceDoc.append("sid", sid);
        performanceRecordsCollection.insertOne(performanceDoc);
    }

    @Override
    public void updatePerformanceRecord(int sid, EvaluationRecord record) {
        Bson filter = Filters.and(Filters.eq("sid", sid), Filters.eq("evaluationId", record.getEvaluationId()));
        Document updateDoc = new Document("$set", record.toDocument());
        performanceRecordsCollection.updateOne(filter, updateDoc);
    }

    @Override
    public void deletePerformanceRecord(int sid) {
        Bson filter = Filters.eq("sid", sid);
        performanceRecordsCollection.deleteOne(filter);
    }

    @Override
    public List<EvaluationRecord> readEvaluationRecords(int sid) {
        Bson filter = Filters.eq("sid", sid);
        List<EvaluationRecord> evaluationRecords = new ArrayList<>();
        performanceRecordsCollection.find(filter).iterator().forEachRemaining(document -> {
            EvaluationRecord record = new EvaluationRecord(document.getString("evaluationId"), document.getInteger("score"));
            evaluationRecords.add(record);
        });
        return evaluationRecords;
    }
}
