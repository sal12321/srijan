package src.com.banking;
import java.security.MessageDigest;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.IOException;
import src.com.banking.passHash.PasswordHasher ;

public class BankingApp {


    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String url = "jdbc:mysql://localhost:3306/forBanking"; 

    private static final String username = "root";  //  replace it with your username
    private static final String password = "root@123"; // replace it with your password

    public static boolean isValidAmount(double amount, double balance) {

        return (amount > 0 && amount <= balance);
    }

    public static int getValidInteger(BufferedReader reader) {
        {
            while (true) {
                try {

                    return Integer.parseInt(reader.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.print("Invalid number, try again: ");
                }
            }
        }
    }

    public static double getValidDouble(BufferedReader reader) {
        while (true) {
            try {
                String input = reader.readLine();
                return Double.parseDouble(input);
            } catch (Exception e) {
                System.out.println("Enter a valid number...");
            }
        }
    }

    // A small utility to hash passwords using SHA-256



    public static double getValidAmount(BufferedReader reader) {
        while (true) {
            try {
                String input = reader.readLine();
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    return amount;
                } else {
                    System.out.println("Amount must be greater than 0.");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Enter a valid amount...");
            }
        }
    }

    public static boolean isActiveAccount(int accNo, Connection connection) {
        String query = "SELECT is_active FROM users WHERE acc_no = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_active"); // true if active, false if inactive
            } else {
                System.out.println(" Account not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" Error checking account status: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        System.out.println("Program started...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getStackTrace()); // loading the drivers

        }

        try { /// MY MAIN WORKING METHOD ....

            Connection connection = DriverManager.getConnection(url, username, password);
            // making my connection

            System.out.println("WELCOME TO THE WORLD SAFE AND SECURE BANK");

            while (true) {

                int MainMenuChoice = mainMenu(reader);
                switch (MainMenuChoice) {

                    case 1:
                        System.out.println("\n=================================\n");
                        System.out.println(" Create Account Selected");
                        // call createAccount() // call the account creation method
                        int status1 = createNewAccount(connection, reader);
                        if (status1 == 0) {
                            System.out.println("Try Again !!! ");

                        }

                        break;

                    case 2:
                        System.out.println("\n=================================\n");

                        System.out.println(" User Login Selected");
                        int userAccountNumber = loginUser(connection, reader);

                        if (userAccountNumber == 0) {
                            System.out.println("Try Again !!!");
                        } else {
                            userLoginMenuList(reader, String.valueOf(userAccountNumber), connection); // after login
                                                                                                      // open
                                                                                                      // the menu for
                                                                                                      // the
                                                                                                      // user

                        }

                        break;

                    case 3:
                        System.out.println("\n=================================\n");

                        System.out.println(" Admin Login Selected");
                        boolean status2 = loginAdmin(connection, reader);
                        if (status2 == false) {
                            System.out.println("Try Again !!!");
                        } else {
                            adminMenuList(reader, connection); // move to the admin services
                        }
                        break;

                    case 0:
                        System.out.println("\n=================================\n");

                        System.out.println(" Exiting... Thank you!");
                        try {
                            reader.close();
                            connection.close();
                            System.exit(0);

                        } catch (IOException e) {
                            System.out.println("Error closing resources: " + e.getMessage());
                        }

                        break;

                    default:
                        System.out.println("Please enter the correct choice");

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public int welcomeFunction() {
        System.out.println("Let's create your account !!! ");
        // userLoginMenuList(reader);
        return 0;

    }

    public static void userLoginMenuList(BufferedReader reader, String accountNumber, Connection connection) {

        int choice;
        int accountNum = Integer.parseInt(accountNumber);

        while (true) {


            System.out.println();
            System.out.println("=========== USER DASHBOARD ===========");
            System.out.println("Account: " + accountNumber);
            System.out.println("--------------------------------------");
            System.out.println(" 1. Check Account Balance");
            System.out.println(" 2. Deposit Money");
            System.out.println(" 3. Withdraw Money");
            System.out.println(" 4. Transfer Money");
            System.out.println(" 5. View Transaction History");
            System.out.println(" 6. Update Account Information");
            System.out.println(" 7. Delete Account");
            System.out.println(" 0. Logout");
            System.out.println("======================================");


            System.out.print("Enter your choice : ");
            choice = getValidInteger(reader);

            if ((choice >= 0 && choice <= 7)) {

                switch (choice) {
                    case 1:
                        System.out.println("\n=================================\n");

                        System.out.println(" Checking Account Balance...");
                        // checkBalance(accountNumber , connection);
                        getBalanceByAccountNumber(connection, accountNumber);
                        break;
                    case 2:
                        System.out.println("\n=================================\n");

                        System.out.println(" Deposit Money...");
                        // depositMoney( connection, reader, accountNumber);
                        depositMoney(connection, reader, accountNum); // acc number ko interger banow
                        break;
                    case 3:
                        System.out.println("\n=================================\n");

                        System.out.println(" Withdraw Money...");

                        withdrawMoney(connection, accountNumber);
                        break;
                    case 4:
                        System.out.println("\n=================================\n");

                        System.out.println(" Transfer Money...");
                        transferMoney(accountNum, connection, reader);
                        break;
                    case 5:
                        System.out.println("\n=================================\n");

                        System.out.println(" Viewing Transaction History...");
                        // viewTransactionHistory(accountNumber , connection);
                        break;
                    case 6:
                        System.out.println("\n=================================\n");

                        System.out.println(" Updating Account Info...");

                        updateAccountInfo(connection, accountNumber, reader);
                        break;
                    case 7:
                        System.out.println("\n=================================\n");

                        System.out.println(" Deleting Account...");
                        deleteAccount(connection, accountNumber);
                        break;
                    case 0:
                        System.out.println("\n=================================\n");

                        System.out.println(" Logging out...");
                        return; // Exit the method
                    default:
                        System.out.println(" Invalid choice. Enter a number between 0 and 7.");
                }

            } else {
                System.out.println("Enter the correct choice ");

                System.out.println("=========== X ===========");

            }

        }

    } // userMethod

    public static void adminMenuList(BufferedReader reader, Connection connection) {

        int choice;
        String accN;

        while (true) {


            System.out.println();
            System.out.println("======================================");
            System.out.println("           ...ADMIN PANEL...          ");
            System.out.println("======================================");
            System.out.println(" 1. View All Accounts");
            System.out.println(" 2. Search Account by Number");
            System.out.println(" 3. View All Transactions");
            System.out.println(" 4. Delete User Account");
            System.out.println(" 0. Logout");
            System.out.println("======================================");


            System.out.print("Enter a valid integer: ");
            choice = getValidInteger(reader);

            if (choice >= 1 && choice <= 5) {

                // return choice ;
                try {
                    switch (choice) {
                        case 1:
                            System.out.println("\n=================================\n");

                            System.out.println(" 1 : Viewing all accounts...");
                            viewAllAccounts(connection);
                            break;

                        case 2:
                            System.out.println("\n=================================\n");

                            System.out.println(" 2 :  Search by Account Number...");
                            System.out.println("Enter the account Number");

                            accN = reader.readLine();
                            searchAccountByNumber(connection, accN);
                            break;

                        case 3:
                            System.out.println("\n=================================\n");

                            System.out.println(" 3 :  Viewing all transactions...");
                            viewAllTransactions(connection);
                            break;

                        case 4:
                            System.out.println("\n=================================\n");

                            System.out.println(" 4 :  Deleting a user account...");
                            System.out.println("Enter the account Number");

                            accN = reader.readLine();
                            deleteUserAccount(connection, accN);

                            break;

                        case 0:
                            System.out.println("\n=================================\n");

                            System.out.println(" 5 :  Logging out of admin panel...");
                            return; // exits the menu

                        default:
                            System.out.println("\n=================================\n");

                            System.out.println(" Please enter a number between 0 and 5.");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println(" Invalid choice. Please enter a number between 0 and 5.");
            }

        }
    }

    // reader.close();
    public static int mainMenu(BufferedReader reader) {
        int choice;

        while (true) {

            System.out.println();
            System.out.println("=======================================");
            System.out.println("            ...MAIN MENU...            ");
            System.out.println("=======================================");
            System.out.println(" 1. Create New Account");
            System.out.println(" 2. User Login");
            System.out.println(" 3. Admin Login");
            System.out.println(" 0. Exit");
            System.out.println("=======================================");

            System.out.print("Enter your choice: ");

            choice = getValidInteger(reader);

            if (!(choice < 4 && choice > 0)) {
                System.out.println(" Please enter 0, 1, 2 or 3 only.");

            } else {
                return choice;
            }

        }

    }

    public static void getBalanceByAccountNumber(Connection conn, String accNo) {
        double balance = 0;
        String query = "SELECT balance FROM users WHERE acc_no = ?";

        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, accNo);
            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
                System.out.println(" Account Balance: ₹" + balance);
            } else {
                System.out.println(" Account not found.");
            }

        } catch (SQLException e) {
            System.out.println(" Error while fetching balance: " + e.getMessage());
        }

    }

    public static int createNewAccount(Connection connection, BufferedReader reader) {
        String insertSQL = "INSERT INTO users (name, email, balance, pin , phone) VALUES (?, ?, ?, ?, ?)";
        String getAccNoQuery = "select acc_no from users where phone = ?";
        int generatedAccountNumber = 0;

        try {

            System.out.print("Please enter your full name: ");

            String name = reader.readLine();

            System.out.print("Enter Phone Number: ");
            long phone = getValidInteger(reader);

            System.out.print("Enter Email : ");
            String email = reader.readLine();

            System.out.print("Enter Initial Deposit: ");
            double balance = getValidDouble(reader);

            System.out.print("Set a 8-digit Password: ");
            String password = PasswordHasher.hash(reader.readLine()); // not even saving it here

            // it returns the autogenerated primary key
            PreparedStatement pInsert = connection.prepareStatement(insertSQL);
            PreparedStatement pAcc = connection.prepareStatement(getAccNoQuery);
            pInsert.setString(1, name);
            pInsert.setString(2, email);
            pInsert.setDouble(3, balance);
            pInsert.setString(4, password);
            pInsert.setLong(5, phone); // account is active

            int rowsInserted = pInsert.executeUpdate(); // update the table with new addes user

            if (rowsInserted > 0) {
                pAcc.setLong(1, phone); // get account number of the account using phone number
                ResultSet rs = pAcc.executeQuery(); // it has user account number that was auto generated primary
                                                    // key
                if (rs.next()) { // result set has rows and the pointer need to be pointed on first row
                    generatedAccountNumber = rs.getInt(1); // first row has account number
                    insertLog(connection, "User", generatedAccountNumber, "New Account", 0, "Acc_Cr_Success", 1);

                    System.out
                            .println(" Account created successfully!!!\n Your Account Number: "
                                    + generatedAccountNumber);
                }
            } else {
                insertLog(connection, "User", generatedAccountNumber, "New Account", 0, "Acc_Cr_Failed", 0);
                System.out.println(" Failed to create account.");
                return 0;
            }

        } catch (SQLException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");

            return 0;

        }

        return generatedAccountNumber;
    }

    public static int loginUser(Connection connection, BufferedReader reader) {
        System.out.println(" USER LOGIN");

        System.out.print("Enter your Account Number: ");
        int accountNumber = getValidInteger(reader);

        System.out.print("Enter your password: ");

        try {
            String hashedInputPass = PasswordHasher.hash(reader.readLine());
            String query = "SELECT pin, is_active FROM users WHERE acc_no = ?";

            try {
                PreparedStatement p = connection.prepareStatement(query);
                p.setInt(1, accountNumber);

                ResultSet rs = p.executeQuery();

                if (rs.next()) {
                    String dbPassword = rs.getString("pin");

                    if (!isActiveAccount(accountNumber, connection)) {
                        insertLog(connection, "User", accountNumber, "Login", 0, "Acc_Not_Act", 0);
                        System.out.println(" Your account is deactivated. Please contact admin.");
                        return 0;
                    }

                    if (dbPassword.equals(hashedInputPass)) {
                        insertLog(connection, "User", accountNumber, "Login", 0, "Acc_Login_Success", 1);
                        System.out.println(" Login successful!");
                        return accountNumber;
                    } else {
                        insertLog(connection, "User", accountNumber, "Login", 0, "Wrong pass", 0);

                        System.out.println(" Incorrect password.");
                        return 0;
                    }

                } else {
                    insertLog(connection, "User", accountNumber, "Login", 0, "Acc_Not_Exists", 0);

                    System.out.println(" Account number does not exist.");
                    return 0;
                }

            } catch (SQLException e) {
                insertLog(connection, "User", accountNumber, "Login", 0, e.getMessage(), 0);

                System.out.println("  Database error: " + e.getMessage());
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;

        }

    }

    public static boolean loginAdmin(Connection connection, BufferedReader reader) {

        try {
            System.out.println("\n ADMIN LOGIN");
            System.out.print("Enter Admin Username: ");
            String username = reader.readLine();

            System.out.print("Enter Password: ");
            String hashedInput = PasswordHasher.hash(reader.readLine());
            String query = "SELECT password FROM admins WHERE username = ?";

            try {
                PreparedStatement p = connection.prepareStatement(query);

                p.setString(1, username);

                ResultSet rs = p.executeQuery();

                if (rs.next()) {
                    String dbPassword = rs.getString("password");

                    if (dbPassword.equals(hashedInput)) {
                        insertLog(connection, "Admin", -1, "LoginSuccess", 0, "Acc_Login_Sucess", 1);

                        System.out.println("   \n==================================");

                        System.out.println("   Admin login successful!");
                        return true;
                    } else {
                        insertLog(connection, "Admin", -1, "LoginFailed", 0, "Wrong Pass", 0);

                        System.out.println("   Incorrect password.");
                        return false;
                    }

                } else {
                    insertLog(connection, "Admin", -1, "Login", 0, "adm_Us_not_Fount", 0);

                    System.out.println("   Admin username not found.");
                    return false;
                }

            } catch (SQLException e) {
                insertLog(connection, "Admin", -1, "Login", 0, e.getMessage(), 0);
                System.out.println("  Database error: " + e.getMessage());
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void depositMoney(Connection connection, BufferedReader reader, int accountNumber) {
        try {
            // Step 1: Check if account exists and is active
            String checkSQL = "SELECT balance FROM users WHERE acc_no = ? AND is_active = 1";
            PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
            checkStmt.setInt(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {

                insertLog(connection, "user", 0, "Amt_Deposit", accountNumber, "Reciever_Not_Active_or_Not_Found", 0);

                System.out.println("   Account not found or inactive.");
                return;
            }

            double currentBalance = rs.getDouble("balance");

            // Step 2: Ask for deposit amount
            System.out.print(" Enter amount to deposit: ");
            double depositAmount;

            while (true) {

                depositAmount = getValidDouble(reader);
                if (depositAmount > 0) {
                    break;
                } else {
                    insertLog(connection, "user", 0, "Amt_Deposit", accountNumber, "Enter positive amt", 0);

                    System.out.print("   Enter a positive amount: ");

                }

            }

            // Step 3: Update balance
            double newBalance = currentBalance + depositAmount;
            String updateSQL = "UPDATE users SET balance = ? WHERE acc_no = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setDouble(1, newBalance);
            updateStmt.setInt(2, accountNumber);

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                insertLog(connection, "user", 0, "Amt_Deposit", accountNumber, "Amt_deposited", 1);

                System.out.printf("   Deposit successful. New balance: ₹%.2f%n", newBalance);
            } else {
                insertLog(connection, "user", 0, "Amt_Deposit", accountNumber, "Deposit_Failed", 0);

                System.out.println("  Deposit failed. Please try again.");
            }

            // Optionally log transaction
            // logTransaction(conn, accountNumber, "DEPOSIT", depositAmount);

        } catch (SQLException e) {
            insertLog(connection, "user", 0, "Amt_Deposit", accountNumber, e.getMessage(), 0);

            e.printStackTrace();
            System.out.println("   Database error during deposit.");
        }
    }

    ///////////////-----------------------

    public static void transferMoney(int SenderAccountNumber, Connection connection, BufferedReader reader) {

        try {
            double amount;

            double senderBalance;
            int recieverAccountNumber;
            System.out.println("Enter the recievers account number : ");

            recieverAccountNumber = getValidInteger(reader); // reciever is found

            if (recieverAccountNumber == SenderAccountNumber) {
                System.out.println(" Cannot transfer to the same account.");
                insertLog(connection, "User", SenderAccountNumber, "moneyTransfer", SenderAccountNumber,
                        "Transfer_Success", 1);

                return;
            }

            String query = "select balance from users where acc_no = ?";
            PreparedStatement p = connection.prepareStatement(query);
            p.setString(1, String.valueOf(SenderAccountNumber));
            ResultSet RSender = p.executeQuery();

            if (RSender.next()) {
                senderBalance = RSender.getDouble("balance");
                // got the senders balance

            } else {
                System.out.println("  Unexpected error: sender account not found.");
                return;
            }

            String updateSenderQuery = "UPDATE users SET balance = balance - ? WHERE acc_no = ?"; // update the sender
                                                                                                  // acc
            String updateRecieverQuery = "UPDATE users SET balance = balance + ? WHERE acc_no = ?"; // update the
                                                                                                    // reciever acc
            String refundSender = "UPDATE users SET balance = balance + ? WHERE acc_no = ?"; // if failed credit back
                                                                                             // the amount
            String checkAccountQuery = "SELECT * FROM users WHERE acc_no = ?"; // account is there or not
            PreparedStatement updtSender = connection.prepareStatement(updateSenderQuery);
            PreparedStatement updtReciever = connection.prepareStatement(updateRecieverQuery);
            PreparedStatement updtrefundSender = connection.prepareStatement(refundSender);
            PreparedStatement AccCheck = connection.prepareStatement(checkAccountQuery);

            try {

                AccCheck.setString(1, String.valueOf(recieverAccountNumber));
                ResultSet rsReciever = AccCheck.executeQuery();

                if (rsReciever.next() && isActiveAccount(recieverAccountNumber, connection)) {

                    System.out.println("   Account Found:");
                    System.out.println("Name: " + rsReciever.getString("name"));
                    System.out.println("Email: " + rsReciever.getString("email"));
                    System.out.println("Phone: " + rsReciever.getString("phone"));

                    System.out.println("Enter the amount to transfer : ");

                    amount = getValidAmount(reader);

                    if (isValidAmount(amount, senderBalance)) {
                        System.out.println("Initiating the transfer....");

                        updtSender.setDouble(1, amount);
                        updtSender.setDouble(2, SenderAccountNumber);

                        updtReciever.setDouble(1, amount);
                        updtReciever.setDouble(2, recieverAccountNumber);

                        int rsSenUpdate = updtSender.executeUpdate();
                        int rsRecUpdate = updtReciever.executeUpdate();

                        if (rsSenUpdate == 1) { /// handle if amount was not sent then do back the amount
                            System.out.println("The amount is deducted from sender... ");
                            if (rsRecUpdate == 1) {
                                System.out.println("The amount was sent to the reciever....");
                                insertLog(connection, "User", SenderAccountNumber, "transferMoney",
                                        recieverAccountNumber, "transferMoneySuccess", 1);

                            } else {
                                insertLog(connection, "User", SenderAccountNumber, "moneyTransfer",
                                        recieverAccountNumber, "Transfer_Success", 0);

                                System.out.println("Amount not recieved by the reciever, adding back the amount....");
                                updtrefundSender.setDouble(1, amount);

                                updtrefundSender.setDouble(2, SenderAccountNumber);
                                // add the amount that was deducted but not sent
                                int rsNOTSENT = updtrefundSender.executeUpdate();
                                if (rsNOTSENT == 0) {
                                    System.out.println("the deducted amount was not sent back to the sender....");
                                    System.out.println("You need to contact admin to resolve the same...");
                                } else {
                                    System.out.println("The deducted amount was credited back to YOUR aCCOUNT....");
                                }

                            }

                        } else {
                            insertLog(connection, "User", SenderAccountNumber, "moneyTransfer", recieverAccountNumber,
                                    "moneyTransfer_Failed", 0);
                            System.out
                                    .println("Transaction unSuccessfull, amount was not deducted from sender....");

                        }

                        try { /// show the balance of the sender after transaction 
                            PreparedStatement balStatement = connection
                                    .prepareStatement("SELECT balance FROM users WHERE acc_no = ?");
                            balStatement.setInt(1, SenderAccountNumber);
                            ResultSet afterSending = balStatement.executeQuery();

                            if (afterSending.next()) {
                                double senderCB = afterSending.getDouble("balance");
                                System.out.println("The amount left in your account : " + senderCB);

                            } else {
                                System.out.println("Could not fetch sender account after transaction...");
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                    } else {
                        System.out.println("Amount cannot be 0 or more than balance....");
                        return;
                    }

                } else {
                    insertLog(connection, "User", SenderAccountNumber, "transferMoney", recieverAccountNumber,
                            "Acc_notFound/InActive", 0);

                    System.out.println("Account not found or not active.");
                }

            } catch (SQLException e) {

                System.out.println("  Error during account search: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    ////-------------------
    public static void withdrawMoney(Connection connection, String accNo) {
        double amount = getValidAmount(reader);
        // System.out.println("Enter the amount to withdraw : ");
        // if (reader.hasNextDouble()) {
        // amount = reader.nextDouble();
        // } else {
        // reader.next();
        // System.out.println("Enter the correct amount");
        // return; // if other than double was entered
        // }

        String checkQuery = "SELECT balance FROM users WHERE acc_no = ?";
        String updateQuery = "UPDATE users SET balance = balance - ? WHERE acc_no = ?";

        try {
            PreparedStatement check = connection.prepareStatement(checkQuery);
            check.setString(1, accNo);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    try {
                        PreparedStatement updateSt = connection.prepareStatement(updateQuery);
                        updateSt.setDouble(1, amount);
                        updateSt.setString(2, accNo);
                        updateSt.executeUpdate();
                        insertLog(connection, "user", Integer.parseInt(accNo), "withdrawMoney", 0,
                                "Amt_withdrawn_success", 1);
                        System.out.println("   ₹" + amount + " withdrawn successfully.");
                    } catch (SQLException e) {
                        insertLog(connection, "user", Integer.parseInt(accNo), "withdrawMoney", 0,
                                "Amt_withdrawn_Failed", 0);
                        System.out.println(e.getMessage());
                    }
                } else {
                    insertLog(connection, "user", Integer.parseInt(accNo), "withdrawMoney", 0, "Insufficient_balance",
                            0);

                    System.out.println("   Insufficient balance.");
                }
            } else {
                insertLog(connection, "user", Integer.parseInt(accNo), "withdrawMoney", 0, "Account_not_found", 0);

                System.out.println("   Account not found.");
            }
        } catch (SQLException e) {
            insertLog(connection, "user", Integer.parseInt(accNo), "withdrawMoney", 0, e.getMessage(), 0);

            System.out.println("  Error during withdrawal: " + e.getMessage());
        }
    }

    public static void deleteAccount(Connection connection, String accNo) {
        String query = "DELETE FROM users WHERE acc_no = ?";

        try {
            PreparedStatement p = connection.prepareStatement(query);

            p.setString(1, accNo);
            int rows = p.executeUpdate();

            if (rows > 0) {

                insertLog(connection, "user", Integer.parseInt(accNo), "Delete_Acc", 0, "Acc_Deleted_Sucess", 1);

                System.out.println(" Account deleted successfully.");
                System.out.println("=================================");
            } else {
                insertLog(connection, "user", Integer.parseInt(accNo), "Delete_Acc", 0, "Account_not_found", 0);

                System.out.println("   Account not found.");
            }
        } catch (SQLException e) {
            insertLog(connection, "user", Integer.parseInt(accNo), "Delete_Acc", 0, e.getMessage(), 0);

            System.out.println("  Error deleting account: " + e.getMessage());
        }
    }

    public static void updateAccountInfo(Connection connection, String accNo, BufferedReader reader) {

        if (!isActiveAccount(Integer.parseInt(accNo), connection)) {
            return;
        }
        String query = "UPDATE users SET name = ?, email = ?, pin = ? WHERE acc_no = ?";
        try {

            System.out.print("Enter Full Name: ");
            String name = reader.readLine();

            System.out.print("Enter Email : ");
            String email = reader.readLine();

            System.out.print("Set a Min-6-digit Password: ");
            String password = reader.readLine();
            try {
                PreparedStatement p = connection.prepareStatement(query);

                p.setString(1, name);
                p.setString(2, email);
                p.setString(3, password);
                p.setString(4, accNo);

                int rows = p.executeUpdate();

                if (rows > 0) {

                    System.out.println("=================================");

                    insertLog(connection, "User", Integer.parseInt(accNo), "Info_Update", Integer.parseInt(accNo),
                            "Updated_Info", 1);
                    System.out.println("   Account info updated successfully.");
                } else {

                    insertLog(connection, "user", Integer.parseInt(accNo), "Info_Update", 0, "Account_not_found", 0);

                    System.out.println("   Account not found.");
                }
            } catch (SQLException e) {

                insertLog(connection, "user", Integer.parseInt(accNo), "Info_Update", 0, e.getMessage(), 0);

                System.out.println("  Error updating account: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void viewAllAccounts(Connection connection) {
        String query = "SELECT acc_no, name, email, phone, balance, created_at FROM users";

        try {
            PreparedStatement p = connection.prepareStatement(query);

            ResultSet rs = p.executeQuery();
            if (!rs.next()) {
                System.out.println("No records Found");
                return;

            }

            System.out.println(" All Bank Accounts:");
            do {
                System.out.println("Account No: " + rs.getString("acc_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Balance: ₹" + rs.getDouble("balance"));
                System.out.println("Created At: " + rs.getTimestamp("created_at"));
                System.out.println("----------------------------");
            } while (rs.next());

            insertLog(connection, "Admin", -1, "viewAllAccounts", 0, "View_All_Acc_Success", 1);

        } catch (SQLException e) {
            insertLog(connection, "Admin", -1, "viewAllAccounts", 0, e.getMessage(), 0);

            System.out.println("  Error viewing accounts: " + e.getMessage());
        }
    }

    public static void searchAccountByNumber(Connection conn, String accNo) {
        String query = "SELECT * FROM users WHERE acc_no = ?";

        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, accNo);
            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                insertLog(conn, "Admin", -1, "viewSpecificAccounts", Integer.parseInt(accNo), "Acc_found", 1);

                System.out.println("   Account Found:");
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Balance: ₹" + rs.getDouble("balance"));
                System.out.println("Created At: " + rs.getTimestamp("created_at"));
            } else {
                insertLog(conn, "Admin", -123, "viewSpecificAccounts", Integer.parseInt(accNo), "Acc_not_found", 0);

                System.out.println("   Account not found.");
            }

        } catch (SQLException e) {
            insertLog(conn, "Admin", -123, "viewSpecificAccounts", Integer.parseInt(accNo), e.getMessage(), 0);

            System.out.println("  Error during account search: " + e.getMessage());
        }
    }

    public static void deleteUserAccount(Connection conn, String accNo) {
        String query = "DELETE FROM users WHERE acc_no = ?";

        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, accNo);
            int rows = p.executeUpdate();

            if (rows > 0) {
                insertLog(conn, "Admin", -1, "Delete_Acc", Integer.parseInt(accNo), "Acc_Delete_Success", 1);

                System.out.println("   Account " + accNo + " deleted successfully.");
            } else {

                System.out.println("Account not found or could not be deleted.");
                insertLog(conn, "Admin", -1, "Delete_Acc", Integer.parseInt(accNo), "AccDltFailed", 0);
            }

        } catch (SQLException e) {
            insertLog(conn, "Admin", -1, "Delete_Acc", Integer.parseInt(accNo), e.getMessage(), 0);

            System.out.println("  Error deleting account: " + e.getMessage());
        }
    }

    // this has to be called after every succesfull/unsucessfull transaction ;
    public static void insertLog(Connection conn, String Who, int WhoseId, String What, int WhomeId, String message,
            int status) {
        String sql = "INSERT INTO logs (Who, WhoseId, What, WhomeId, message , status) VALUES (?, ?, ?, ?, ? , ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Who);
            pstmt.setInt(2, WhoseId);
            pstmt.setString(3, What);
            pstmt.setInt(4, WhomeId);
            pstmt.setString(5, message);
            if (status == 1) {
                pstmt.setString(6, "success");
            } else {
                pstmt.setString(6, "Failed");

            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("  Failed to insert log: " + e.getMessage());
        }
    }

    public static void viewAllTransactions1(Connection connection) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the limit : ");
        int limit;

        while (true) {
            try {
                limit = Integer.parseInt(br.readLine());
                break;
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }

        try {

            String query = "select * from logs limit ?";
            PreparedStatement p = connection.prepareStatement(query);
            p.setInt(1, limit);
            ResultSet rs = p.executeQuery();
            if (!rs.next()) {
                System.out.println("Could not fetch logs...");

            } else {
                do {
                    System.out.println("Account type: " + rs.getString("Who"));
                    System.out.println("Acc_No : " + rs.getInt("WhoseId"));
                    System.out.println("Action: " + rs.getString("What"));
                    System.out.println("target_Acc : " + rs.getInt("WhomeId"));
                    System.out.println("message : " + rs.getString("message"));
                    System.out.println("timestamp: " + rs.getTimestamp("timestamp"));
                    System.out.println("status: " + rs.getInt("status"));
                    System.out.println("----------------------------");
                } while (rs.next());

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void viewAllTransactions(Connection connection) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the limit: ");
        int limit;

        while (true) {
            try {
                limit = Integer.parseInt(br.readLine());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }

        try {
            String query = "SELECT * FROM logs LIMIT ?";
            PreparedStatement p = connection.prepareStatement(query);
            p.setInt(1, limit);
            ResultSet rs = p.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Could not fetch logs...");
            } else {
                while (rs.next()) {
                    System.out.println("Account type: " + rs.getString("Who"));
                    System.out.println("Acc_No : " + rs.getInt("WhoseId"));
                    System.out.println("Action: " + rs.getString("What"));
                    System.out.println("target_Acc : " + rs.getInt("WhomeId"));
                    System.out.println("message : " + rs.getString("message"));
                    System.out.println("timestamp: " + rs.getTimestamp("timestamp"));
                    System.out.println("status: " + rs.getString("status"));
                    System.out.println("----------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

}
