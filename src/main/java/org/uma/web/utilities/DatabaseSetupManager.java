package org.uma.web.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseSetupManager {

    private String host = ConfigurationManager.getInstance().getProperty("db.host");
    private String serviceName = ConfigurationManager.getInstance().getProperty("db.serviceName");
    private String userName = ConfigurationManager.getInstance().getProperty("db.username");
    private String password = ConfigurationManager.getInstance().getProperty("db.password");
    private String dbURL = "jdbc:oracle:thin:@" + host + ":1521/" + serviceName;

    public void setupDatabase() {
        Connection con = null;
        Statement st = null;
        ResultSet rs1 = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(dbURL, userName, password);
            st = con.createStatement();

            String updateQuery = "UPDATE actor SET first_name = 'umas' WHERE actor_id = 1";
            int rowsAffected = st.executeUpdate(updateQuery);

            if (rowsAffected > 0) {
                System.out.println("Update successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows updated.");
            }

            String selectQuery = "SELECT last_update FROM actor WHERE actor_id = 1";
            rs1 = st.executeQuery(selectQuery);

            while (rs1.next()) {
                System.out.println(rs1.getString("last_update"));
            }

            System.out.println("Database operations completed successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs1 != null) {
                    rs1.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Program finished.");
    }

    public ArrayList<String> connectToDB(String query) throws Exception {
        ArrayList<String> arrList = null;
        try (Connection con = DriverManager.getConnection(dbURL, userName, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            arrList = new ArrayList<>(columnCount);

            int totalRows = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    arrList.add(rs.getString(i));
                }
                totalRows++;
            }

            System.out.println("Total Number of Records = " + totalRows);
            arrList.add(Integer.toString(totalRows));

        } catch (Exception e) {
            System.out.println("Database connection error: " + e);
            throw e;
        }
        return arrList;
    }

    public void deleteTableRecords(String query) throws Exception {
        try (Connection con = DriverManager.getConnection(dbURL, userName, password);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Database deletion error: " + e);
            throw e;
        }
    }

    public void insertTableRecords(String query) throws Exception {
        try (Connection con = DriverManager.getConnection(dbURL, userName, password);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Database insertion error: " + e);
            throw e;
        }
    }

    public static void main(String[] args) {
        DatabaseSetupManager manager = new DatabaseSetupManager();
        manager.setupDatabase();
    }
}