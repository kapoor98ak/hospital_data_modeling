import org.apache.spark.SparkConf;

import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.sql.Dataset;

import org.apache.spark.sql.Row;

import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class fragmentation {

//    Map<String, String> locFragMap = new HashMap<>();

    public static void main(String[] args) {

        // Spark configuration

        SparkConf conf = new SparkConf().setAppName("SelectData").setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        // Spark SQL session

        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // JDBC connection properties

        Properties connectionProperties = new Properties();

        connectionProperties.put("user", "root");

        connectionProperties.put("password", "root");

        // JDBC URL for MySQL instances

        String vmysql1 = "jdbc:mysql://34.41.125.106:3306";
        String vmysql2 = "jdbc:mysql://34.106.240.4:3306";

        Dataset<Row> selectedData;

        // Fetch data from ShardID 1

        String location;
        String shardToBeSelected = selectShard();

        if (shardToBeSelected == "shard_1") {

            selectedData = spark.read().jdbc(vmysql1, "HospitalClinicLocationMapping", connectionProperties);

        }

        // Fetch data from ShardID 2

        else if (shardToBeSelected == "shard_2") {

            selectedData = spark.read().jdbc(vmysql1, "Hospital", connectionProperties);

        }

        else {

            selectedData = spark.emptyDataFrame();

        }

        // Show the result

        selectedData.show();

        // Stop Spark

        sc.stop();

    }

    private static String selectShard() {
        return "Shard-1";
//        return "Shard-2";

    }

}
