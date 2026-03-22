package Banking;

import java.util.Scanner;

public class BankingManagement {

    public static void main(String[] args) {

        DBConnection db = new DBConnection();
        Scanner sc = new Scanner(System.in);

        System.out.println("✅ Connected Successfully!");

        while (true) {

            System.out.println("\n------ BANKING SYSTEM ------");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int role = sc.nextInt();

            if (role == 3) {
                System.out.println("Thank you for using the system!");
                db.closeConnection();
                sc.close();
                break;
            }

            switch (role) {

                // ================= ADMIN =================
                case 1:
                    System.out.print("Enter Admin Username: ");
                    String aUsername = sc.next();

                    System.out.print("Enter Admin Password: ");
                    String aPassword = sc.next();

                    if (db.adminLogin(aUsername, aPassword)) {
                        System.out.println("✅ Admin Login Successful");

                        while (true) {
                            System.out.println("\n------ ADMIN MENU ------");
                            System.out.println("1. Create Customer");
                            System.out.println("2. View Customers");
                            System.out.println("3. Delete Customer");
                            System.out.println("4. Logout");
                            System.out.print("Enter choice: ");

                            int ch = sc.nextInt();

                            switch (ch) {
                                case 1:
                                    System.out.print("Enter Account Number: ");
                                    int accNo = sc.nextInt();

                                    System.out.print("Enter Username: ");
                                    String name = sc.next();

                                    System.out.print("Enter Password: ");
                                    String pass = sc.next();

                                    System.out.print("Enter Initial Balance: ");
                                    double bal = sc.nextDouble();

                                    db.addCustomer(accNo, name, pass, bal);
                                    break;

                                case 2:
                                    db.viewCustomers();
                                    break;

                                case 3:
                                    System.out.print("Enter Account Number to delete: ");
                                    int delAcc = sc.nextInt();
                                    db.deleteCustomer(delAcc);
                                    break;

                                case 4:
                                    System.out.println("Logged out successfully!");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }

                            if (ch == 4) break;
                        }

                    } else {
                        System.out.println("❌ Invalid Admin Credentials");
                    }
                    break;

                // ================= CUSTOMER =================
                case 2:
                    System.out.print("Enter Username: ");
                    String cUsername = sc.next();

                    System.out.print("Enter Password: ");
                    String cPassword = sc.next();

                    if (db.customerLogin(cUsername, cPassword)) {
                        System.out.println("✅ Customer Login Successful");

                        while (true) {
                            System.out.println("\n------ CUSTOMER MENU ------");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. Logout");
                            System.out.print("Enter choice: ");

                            int op = sc.nextInt();

                            switch (op) {
                                case 1:
                                    System.out.print("Enter Account Number: ");
                                    int acc = sc.nextInt();
                                    db.checkBalance(acc);
                                    break;

                                case 2:
                                    System.out.print("Enter Account Number: ");
                                    int depAcc = sc.nextInt();

                                    System.out.print("Enter Amount: ");
                                    double depAmt = sc.nextDouble();

                                    db.deposit(depAcc, depAmt);
                                    break;

                                case 3:
                                    System.out.print("Enter Account Number: ");
                                    int witAcc = sc.nextInt();

                                    System.out.print("Enter Amount: ");
                                    double witAmt = sc.nextDouble();

                                    db.withdraw(witAcc, witAmt);
                                    break;

                                case 4:
                                    System.out.println("Logged out successfully!");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }

                            if (op == 4) break;
                        }

                    } else {
                        System.out.println("❌ Invalid Customer Credentials");
                    }
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
