//The provided ManagePersonal interface does not entirely fulfill the CRUD (Create, Read, Update, Delete) pattern. We  should implement the missing operations like Update and Delete if they are needed in our application

public interface ManagePersonal {

    public void createSalesMan(SalesMan record);

    public void updateSalesMan(int sid, SalesMan record);

    public void deleteSalesMan(int sid);

    public void addPerformanceRecord(EvaluationRecord record, int sid);

    public void updatePerformanceRecord(int sid, EvaluationRecord record);

    public void deletePerformanceRecord(int sid);

    public SalesMan readSalesMan(int sid);

    public List<SalesMan> querySalesMan(String attribute, String key);

    public List<EvaluationRecord> readEvaluationRecords(int sid);
}
