package Banking;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASSWORD = "Sri@2000";

    private Connection con;

    // Constructor
    public DBConnection() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }

    // ---------------- ADMIN LOGIN ----------------
    public boolean adminLogin(String username, String password) {
        String query = "SELECT * FROM admin WHERE Username=? AND password=?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error during admin login: " + e.getMessage());
        }
        return false;
    }

    // ---------------- VIEW CUSTOMERS ----------------
    public void viewCustomers() {
        String query = "SELECT * FROM Customer";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Account: %d | Name: %s | Balance: %.2f%n",
                        rs.getInt("AccountNumber"),
                        rs.getString("Username"),
                        rs.getDouble("balance"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
    }

    // ---------------- ADD CUSTOMER ----------------
    public void addCustomer(int accountNumber, String username, String password, double balance) {

        if (balance < 0) {
            System.out.println("Initial balance cannot be negative!");
            return;
        }

        String query = "INSERT INTO Customer VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, accountNumber);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setDouble(4, balance);

            ps.executeUpdate();
            System.out.println("Customer Created Successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    // ---------------- DELETE CUSTOMER ----------------
    public void deleteCustomer(int accountNumber) {
        String query = "DELETE FROM Customer WHERE AccountNumber=?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, accountNumber);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Customer Deleted Successfully!");
            } else {
                System.out.println("Customer Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    // ---------------- CUSTOMER LOGIN ----------------
    public boolean customerLogin(String username, String password) {
        String query = "SELECT * FROM Customer WHERE Username=? AND Password=?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error during customer login: " + e.getMessage());
        }
        return false;
    }

    // ---------------- CHECK BALANCE ----------------
    public void checkBalance(int accountNumber) {
        String query = "SELECT balance FROM Customer WHERE AccountNumber=?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, accountNumber);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Current Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error checking balance: " + e.getMessage());
        }
    }

    // ---------------- DEPOSIT ----------------
    public void deposit(int accountNumber, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        String query = "UPDATE Customer SET balance = balance + ? WHERE AccountNumber=?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setInt(2, accountNumber);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Deposit Successful!");
            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error during deposit: " + e.getMessage());
        }
    }

    // ---------------- WITHDRAW ----------------
    public void withdraw(int accountNumber, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        String checkQuery = "SELECT balance FROM Customer WHERE AccountNumber=?";

        try (PreparedStatement ps1 = con.prepareStatement(checkQuery)) {
            ps1.setInt(1, accountNumber);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance >= amount) {
                    String updateQuery = "UPDATE Customer SET balance = balance - ? WHERE AccountNumber=?";

                    try (PreparedStatement ps2 = con.prepareStatement(updateQuery)) {
                        ps2.setDouble(1, amount);
                        ps2.setInt(2, accountNumber);
                        ps2.executeUpdate();
                        System.out.println("Withdrawal Successful!");
                    }

                } else {
                    System.out.println("Insufficient Balance!");
                }

            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }

    // ---------------- CLOSE CONNECTION ----------------
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Connection Closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
