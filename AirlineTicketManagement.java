import java.sql.*;

public class AirlineTicketManagement {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/airline_db";
        String user = "root"; 
        String password = "password"; 

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully!");
            
            // Create tables
            Statement stmt = conn.createStatement();
            String createFlightsTable = "CREATE TABLE IF NOT EXISTS flights (id INT PRIMARY KEY AUTO_INCREMENT, flight_name VARCHAR(50), source VARCHAR(50), destination VARCHAR(50), price DECIMAL(10,2))";
            stmt.executeUpdate(createFlightsTable);
            
            String createTicketsTable = "CREATE TABLE IF NOT EXISTS tickets (id INT PRIMARY KEY AUTO_INCREMENT, passenger_name VARCHAR(50), flight_id INT, FOREIGN KEY (flight_id) REFERENCES flights(id))";
            stmt.executeUpdate(createTicketsTable);
            System.out.println("Tables 'flights' and 'tickets' created successfully!");
            
            // Insert data into flights
            String insertFlightSQL = "INSERT INTO flights (flight_name, source, destination, price) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertFlightSQL);
            pstmt.setString(1, "Air India");
            pstmt.setString(2, "Chennai");
            pstmt.setString(3, "Delhi");
            pstmt.setDouble(4, 5000.00);
            pstmt.executeUpdate();
            System.out.println("Flight inserted successfully!");
            
            // Read flights data
            String selectFlightsSQL = "SELECT * FROM flights";
            ResultSet rs = stmt.executeQuery(selectFlightsSQL);
            while (rs.next()) {
                System.out.println("Flight ID: " + rs.getInt("id") + ", Name: " + rs.getString("flight_name") + ", Source: " + rs.getString("source") + ", Destination: " + rs.getString("destination") + ", Price: " + rs.getDouble("price"));
            }
            
            // Insert ticket booking
            String insertTicketSQL = "INSERT INTO tickets (passenger_name, flight_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertTicketSQL);
            pstmt.setString(1, "Kumar sangakkara");
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
            System.out.println("Ticket booked successfully!");
            
            // Read tickets
            String selectTicketsSQL = "SELECT tickets.id, passenger_name, flight_name FROM tickets JOIN flights ON tickets.flight_id = flights.id";
            rs = stmt.executeQuery(selectTicketsSQL);
            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt("id") + ", Passenger: " + rs.getString("passenger_name") + ", Flight: " + rs.getString("flight_name"));
            }
            
            // Close resources
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}