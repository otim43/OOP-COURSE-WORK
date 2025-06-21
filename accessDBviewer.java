import java.sql.*;

public class accessDBviewer {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            
            String dbPath = "VUE_Exhibition.accdb"; 
            String url = "jdbc:ucanaccess://" + dbPath;

           
            conn = DriverManager.getConnection(url);
            System.out.println("Connected successfully.\n");

            
            String sql = "SELECT * FROM Participants";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                
                
                while (rs.next()) {
                    System.out.println("Registration ID: " + rs.getString("RegistrationID"));
                    System.out.println("Student Name: " + rs.getString("StudentName"));
                    System.out.println("Faculty: " + rs.getString("Faculty"));
                    System.out.println("Project Title: " + rs.getString("ProjectTitle"));
                    System.out.println("Contact Number: " + rs.getString("ContactNumber"));
                    System.out.println("Email Address: " + rs.getString("EmailAddress"));
                    System.out.println("Image Path: " + rs.getString("ImagePath"));
                    System.out.println("----------------------------------");
                }
                
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found. Add the UCanAccess JAR files.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
    }
}
