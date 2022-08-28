package jm.task.core.jdbc;

public class ReportCollector {
    private static StringBuilder reportCollection;

    public static void toReport(String string) {
        if (reportCollection == null){
            reportCollection = new StringBuilder("-----------------\n" + string);
        } else {
            reportCollection.append("\n\n" + string);
        }
    }

    public static void printReport(){
        System.out.println(reportCollection + "\n-----------------");
    }
}
