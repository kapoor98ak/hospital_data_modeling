import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test_connection_GDC {

    static String vmysql1 = "jdbc:mysql://34.41.125.106:3306";
    static String vmysql2 = "jdbc:mysql://34.106.240.4:3306";
    static String cloud_username = "root";
    static String cloud_password = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query1 = "Select * from Clinic where ClinicID = 1;";
            String query2 = "Select * from Instance2.Clinic where ClinicID = 2;";
            executeClinicQuery(query1);
            executeClinicQuery(query2);
            // Create a Scanner object to read input from the command line
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the SQL query:");
                String query = scanner.nextLine();
                if (query.equalsIgnoreCase("exit")) {
                    break;
                }
                executeClinicQuery(query);
            }
            System.out.println("Connection initiated...");
            Connection con = DriverManager.getConnection(vmysql1, cloud_username, cloud_password);
            storeDetailsToFile();
            String instanceName = getInstanceNameForClinic(1);
            System.out.println(instanceName);
            insertHospitalAndClinicData();
            see_hospital_location_mapping_server_1();
            see_hospital_server2();
            System.out.println("Connected to the database!");
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
    }


    private static void see_hospital_location_mapping_server_1() {
        try {
            Connection con = DriverManager.getConnection(vmysql1, cloud_username, cloud_password);
            Statement stmt = con.createStatement();
            System.out.println("HospitalClinicLocationMapping ->");
            stmt.execute("use sprint2");

            ResultSet rs = stmt.executeQuery("SELECT * FROM HospitalClinicLocationMapping");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println(); // Move to next line for data

            // Print data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println(); // Move to next line for next row
            }

            ResultSet rs1 = stmt.executeQuery("select * from Clinic");
            System.out.println("Clinics from the server 1");

            ResultSetMetaData rsmd1 = rs1.getMetaData();
            int columnCount1 = rsmd1.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount1; i++) {
                System.out.print(rsmd1.getColumnName(i) + "\t");
            }
            System.out.println(); // Move to next line for data

            // Print data
            while (rs1.next()) {
                for (int i = 1; i <= columnCount1; i++) {
                    System.out.print(rs1.getString(i) + "\t");
                }
                System.out.println(); // Move to next line for next row
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void see_hospital_server2() {
        try {
            Connection con = DriverManager.getConnection(vmysql2, cloud_username, cloud_password);
            Statement stmt = con.createStatement();
            System.out.println("Hospital Server 2 ->");
            stmt.execute("use sprint2");

            ResultSet rs1 = stmt.executeQuery("select * from Clinic");
            System.out.println("Clinics from the server 2");

            ResultSetMetaData rsmd = rs1.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println(); // Move to next line for data

            // Print data
            while (rs1.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs1.getString(i) + "\t");
                }
                System.out.println(); // Move to next line for next row
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void insertHospitalAndClinicData() {
        try {
            // Establish connection to the databases
            Connection conInstance1 = DriverManager.getConnection(vmysql1, cloud_username, cloud_password);
            Connection conInstance2 = DriverManager.getConnection(vmysql2, cloud_username, cloud_password);

            //Delete All Data Server
            deleteAllDataFromServer1(conInstance1);
            deleteAllDataFromServer2(conInstance2);
            //Dummy data in server 1
            insertLocation(conInstance1, 1, "Street 1", "PostalCode 1", "City 1", "Province 1", 0.0, 0.0); // Dummy location for instance 1
            insertHospital(conInstance1, 1, "Hospital Name 1", 1, "1234567890", "hospital1@example.com");
            insertClinic(conInstance1, 1, "Clinic Name 1", "1234567890", "clinic1@example.com", 1);
            insertClinic(conInstance1, 3, "Clinic Name 3", "1234567890", "clinic3@example.com", 1);

            //Dummy data in server 2
            insertLocation(conInstance2, 2, "Street 2", "PostalCode 2", "City 2", "Province 2", 0.0, 0.0); // Dummy location for instance 2
            insertHospital(conInstance2, 2, "Hospital Name 2", 2, "1234567890", "hospital2@example.com");
            insertClinic(conInstance2, 2, "Clinic Name 2", "1234567890", "clinic2@example.com", 2);
            insertClinic(conInstance2, 4, "Clinic Name 4", "1234567890", "clinic4@example.com", 2);

            // Insert data for instance 1
//            insertHospitalClinicLocationMapping(conInstance1, 1, 1, 1, 0.0, 0.0, "City", "State", "Country", "PINCode", "Instance1", "Active");
//            insertHospitalClinicLocationMapping(conInstance1, 1, 3, 1, 0.0, 0.0, "City", "State", "Country", "PINCode", "Instance1", "Active");
//
//            // Insert data for instance 2
//            insertHospitalClinicLocationMapping(conInstance1, 2, 2, 2, 0.0, 0.0, "City", "State", "Country", "PINCode", "Instance2", "Active");
//            insertHospitalClinicLocationMapping(conInstance1, 2, 4, 2, 0.0, 0.0, "City", "State", "Country", "PINCode", "Instance2", "Active");

            //connection cancel
            conInstance1.close();
            conInstance2.close();
        } catch (Exception e) {
            System.out.println("THis is exception thown " + e);
        }
    }

    private static void insertClinic(Connection connection, int clinicId, String clinicName, String phoneNumber, String email, int hospitalId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Clinic (ClinicID, Name, PhoneNumber, Email, HospitalID) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, clinicId);
        stmt.setString(2, clinicName);
        stmt.setString(3, phoneNumber);
        stmt.setString(4, email);
        stmt.setInt(5, hospitalId);
        int result = stmt.executeUpdate();
        System.out.println("This is inserted ${}" + clinicId + ":" + result);
        stmt.close();
    }


    private static void insertHospital(Connection connection, int hospitalId, String name, int location, String phoneNumber, String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Hospital (HospitalID, Name, LocationID, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, hospitalId);
        stmt.setString(2, name);
        // Assuming LocationID is an integer field, update accordingly
        stmt.setInt(3, location); // Assuming LocationID is not used or it's auto-generated
        stmt.setString(4, phoneNumber);
        stmt.setString(5, email);
        int result = stmt.executeUpdate();
        System.out.println("This is inserted insertHospital ${}" + hospitalId + ":" + result);

        stmt.close();
    }

    private static void insertLocation(Connection connection, int locationId, String street, String postalCode, String city, String province, double latitude, double longitude) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Location (LocationID, Street, PostalCode, City, Province, Lat, Longitude) VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, locationId);
        stmt.setString(2, street);
        stmt.setString(3, postalCode);
        stmt.setString(4, city);
        stmt.setString(5, province);
        stmt.setDouble(6, latitude);
        stmt.setDouble(7, longitude);
        int result = stmt.executeUpdate();
        System.out.println("This is inserted insertLocation ${}" + locationId + ":" + result);
        stmt.close();
    }

    private static void deleteAllDataFromServer1(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("use sprint2");

        int result = stmt.executeUpdate("DELETE FROM Clinic");
        System.out.println("This is deleted Clinic ${}" + result);

        int result2 = stmt.executeUpdate("DELETE FROM Hospital");
        System.out.println("This is deleted Hospital ${}" + result2);

        int result3 = stmt.executeUpdate("DELETE FROM Location");
        System.out.println("This is deleted Location ${}" + result3);

        int result1 = stmt.executeUpdate("DELETE FROM HospitalClinicLocationMapping");
        System.out.println("This is deleted deleteAllDataFromHospitalClinicLocationMapping ${}" + result1);

        stmt.close();
    }

    private static void deleteAllDataFromServer2(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("use sprint2");

        int result = stmt.executeUpdate("DELETE FROM Clinic");
        System.out.println("This is deleted Clinic ${}" + result);

        int result2 = stmt.executeUpdate("DELETE FROM Hospital");
        System.out.println("This is deleted Hospital ${}" + result2);


        int result3 = stmt.executeUpdate("DELETE FROM Location");
        System.out.println("This is deleted Location ${}" + result3);

        stmt.close();
    }

    private static void insertHospitalClinicLocationMapping(Connection connection, int hospitalId, int clinicId, int locationId, double latitude, double longitude, String city, String state, String country, String pincode, String instanceName, String instanceStatus) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO HospitalClinicLocationMapping (HospitalID, ClinicID, LocationID, Latitude, Longitude, City, State, Country, PINCode, InstanceName, InstanceStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, hospitalId);
        stmt.setInt(2, clinicId);
        stmt.setInt(3, locationId);
        stmt.setDouble(4, latitude);
        stmt.setDouble(5, longitude);
        stmt.setString(6, city);
        stmt.setString(7, state);
        stmt.setString(8, country);
        stmt.setString(9, pincode);
        stmt.setString(10, instanceName);
        stmt.setString(11, instanceStatus);
        stmt.executeUpdate();
        stmt.close();
    }

    private static void storeDetailsToFile() {
        try (PrintWriter writer = new PrintWriter("hospital_clinic_details.txt")) {
            writer.println("HospitalID,ClinicID,LocationID,Latitude,Longitude,City,State,Country,PINCode,InstanceName,InstanceStatus");
            writer.println("1,1,1,0.0000,0.0000,City,State,Country,PINCode,Instance1,Active");
            writer.println("1,3,1,0.0000,0.0000,City,State,Country,PINCode,Instance1,Active");
            writer.println("2,2,2,0.0000,0.0000,City,State,Country,PINCode,Instance2,Active");
            writer.println("2,4,2,0.0000,0.0000,City,State,Country,PINCode,Instance2,Active");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
    }

    private static String getInstanceNameForClinic(int clinicId) {
        String instanceName = null;
        try (Scanner scanner = new Scanner(new File("hospital_clinic_details.txt"))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int currentClinicId = Integer.parseInt(parts[1]);
                if (currentClinicId == clinicId) {
                    instanceName = parts[9];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
        return instanceName;
    }


    public static void executeClinicQuery(String query) {
        try {
            String instanceName = "";
            // Parse the query to extract the instance name
            QueryInfo queryInfo = parseQuery(query);

            // If instance name not provided, retrieve it from the parser file based on clinic ID
            if (queryInfo.getInstanceName() == null) {
                instanceName = getInstanceNameForClinic(
                        Integer.parseInt(queryInfo.getKeyValue())); // You need to implement this method
            }else{
                instanceName = queryInfo.getInstanceName();
            }
            // Determine the connection details based on instance name
            String jdbcURL = null;
            if ("instance1".equalsIgnoreCase(instanceName)) {
                jdbcURL = vmysql1;
            } else if ("instance2".equalsIgnoreCase(instanceName)) {
                jdbcURL = vmysql2;
            } else {
                System.out.println("Invalid instance name.");
                return;
            }

            String queryToExecute = removeInstanceName(query);

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcURL, cloud_username, cloud_password);

            // Execute the query
            Statement statement = connection.createStatement();
            statement.execute("use sprint2");
            ResultSet resultSet = statement.executeQuery(queryToExecute);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println(); // Move to next line for data

            // Print data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println(); // Move to next line for next row
            }

            // Close connections
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static QueryInfo parseQuery(String query) {
        QueryInfo info = new QueryInfo();

        // Define the regex pattern to match table name, instance name, key, and value
        Pattern pattern = Pattern.compile("from\\s*(\\S+)\\s*where\\s*(\\S+)\\s*=\\s*(\\d+)");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1);
            String instanceName = null;

            // Extract instance name if available
            String[] parts = tableName.split("\\.");
            if (parts.length > 1) {
                instanceName = parts[0];
                tableName = parts[1];
            }

            String key = matcher.group(2);
            String value = matcher.group(3);

            info.setTableName(tableName);
            info.setInstanceName(instanceName);
            info.setKeyName(key);
            info.setKeyValue(value);

        } else {
            System.out.println("Invalid query: " + query);
        }
        return info;
    }



    public static String removeInstanceName(String query) {
        // Convert the query to lowercase to handle case-insensitive matching
        String lowerCaseQuery = query.toLowerCase();

        // Find the index of the first dot after "instance" (indicating the end of the instance name)
        int firstIndex = lowerCaseQuery.indexOf("instance");
        int instanceDotIndex = lowerCaseQuery.indexOf("instance") + "instance".length();
        int nextDotIndex = lowerCaseQuery.indexOf(".", instanceDotIndex);

        if (nextDotIndex != -1) {
            // If a dot is found after "instance", remove the instance name and return the modified query
            String beforeInstanceName = query.substring(0, firstIndex);
            String afterInstanceName = query.substring(nextDotIndex + 1);
            return beforeInstanceName + afterInstanceName;
        } else {
            // If no dot is found after "instance", return the original query
            return query;
        }
    }
}


class QueryInfo {
    private String tableName;
    private String instanceName;
    private String keyName;
    private String keyValue;

    // Getters and setters
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}