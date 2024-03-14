//import org.apache.spark.SparkConf;
//
//import org.apache.spark.api.java.JavaSparkContext;
//
//import org.apache.spark.sql.Dataset;
//
//import org.apache.spark.sql.Row;
//
//import org.apache.spark.sql.SparkSession;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//public class fragmentation {
//
//    Map<String, String> locFragMap = new HashMap<>();
//
//    public static void main(String[] args) {
//
//        // Spark configuration
//
//        SparkConf conf = new SparkConf().setAppName("SelectData").setMaster("local[*]");
//
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        // Spark SQL session
//
//        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
//
//        // JDBC connection properties
//
//        Properties connectionProperties = new Properties();
//
//        connectionProperties.put("user", "root");
//
//        connectionProperties.put("password", "root");
//
//        // JDBC URL for MySQL instances
//
//        String urlInstance1 = "jdbc:mysql://34.72.57.135:3306";
//
////        String urlInstance2 = "jdbc:mysql://instance2_ip:instance2_port/";
//
//        Dataset<Row> selectedData;
//
//        // Fetch data from ShardID 1
//
//        String location;
//        String shardToBeSelected = selectShard();
//
//        if (shardToBeSelected == "shard_1") {
//
//            selectedData = spark.read().jdbc(urlInstance1, "your_table_in_instance1", connectionProperties);
//
//        }
//
//        // Fetch data from ShardID 2
//
//        else if (shardToBeSelected == "shard_2") {
//
////            selectedData = spark.read().jdbc(urlInstance2, "your_table_in_instance2", connectionProperties);
//
//        }
//
//        // Handle other cases or provide a default behavior
//
//        else {
//
//            selectedData = spark.emptyDataFrame();
//
//        }
//
//        // Show the result
//
//        selectedData.show();
//
//        // Stop Spark
//
//        sc.stop();
//
//    }
//
//    private static String selectShard() {
//        return "Shard-1";
//    }
//
//}
