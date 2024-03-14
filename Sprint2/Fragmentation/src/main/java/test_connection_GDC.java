import java.sql.*;

public class test_connection_GDC {

    static String vmysql1 = "jdbc:mysql://34.41.125.106:3306";
    static String vmysql2 = "jdbc:mysql://34.106.240.4:3306";
    static String cloud_username = "root";
    static String cloud_password = "root";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connection initiated...");
            Connection con = DriverManager.getConnection(vmysql1, cloud_username, cloud_password);
            see_hospitallocationmapping_server_1();
            see_hospital_server2();
            System.out.println("Connected to the database!");
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
    }

    private static void see_hospitallocationmapping_server_1(){
        try {
            Connection con = DriverManager.getConnection(vmysql1, cloud_username, cloud_password);
            Statement stmt = con.createStatement();
            System.out.println("HospitalClinicLocationMapping ->");
            stmt.execute("use sprint2");
            ResultSet rs = stmt.executeQuery("select * from HospitalClinicLocationMapping");
            System.out.println(rs);
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void see_hospital_server2(){
        try {
            Connection con = DriverManager.getConnection(vmysql2, cloud_username, cloud_password);
            Statement stmt = con.createStatement();
            System.out.println("Hospital Server 2 ->");
            stmt.execute("use sprint2");
            ResultSet rs = stmt.executeQuery("select * from Hospital");
            System.out.println(rs);
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

