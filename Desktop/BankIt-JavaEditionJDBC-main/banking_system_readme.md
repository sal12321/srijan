# 🏦 Banking Management System

A robust **console-based banking application** built using Java and MySQL. This system provides secure user and admin functionalities like account creation, balance checking, fund transfers, and detailed logging of all operations.

---

## ✨ Features

### 👤 User Features

- **Account Management**

  - Create bank accounts with unique account numbers
  - Secure login with SHA-256 password hashing
  - Update profile details (name, email, password)
  - Account deletion

- **Banking Operations**

  - Balance inquiry
  - Deposit and withdraw money with validation
  - Fund transfer to other accounts
  - View detailed transaction history

### 👨‍💼 Admin Features

- **Account Management**

  - View and manage all user accounts
  - Search users by account number
  - Delete user accounts
  - Monitor account activity

- **System Monitoring**

  - View full transaction logs
  - Check success/failure statuses
  - Timestamped activity records

### 🔒 Security Features

- Passwords hashed with SHA-256
- SQL injection protection using PreparedStatements
- Active/inactive account status verification
- Input validation for all user actions

---

## 🛠️ Technologies Used

- **Language**: Java (Console-based)
- **Database**: MySQL
- **JDBC Driver**: mysql-connector-j-9.3.0.jar
- **Hashing**: SHA-256 (Java Security Package)

---

## 📃 Prerequisites

Ensure you have the following installed:

- Java JDK 8 or higher
- MySQL Server 8.0+
- MySQL JDBC Driver (v9.3.0 used)
- Any IDE or terminal

---

## 🚀 Setup Instructions

### 1. Database Setup

```sql
CREATE DATABASE forBanking;
USE forBanking;

CREATE TABLE users (
    acc_no INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone BIGINT UNIQUE NOT NULL,
    balance DOUBLE DEFAULT 0.0,
    pin VARCHAR(64) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE logs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Who VARCHAR(20) NOT NULL,
    WhoseId INT NOT NULL,
    What VARCHAR(50) NOT NULL,
    WhomeId INT NOT NULL,
    message VARCHAR(200) NOT NULL,
    status VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default admin (admin/admin123)
INSERT INTO admins (username, password) VALUES
('admin', '039df8f1999cb4a6675860fe51c00f48ca7a9c3489062e476182ee1e8ba5e89a');
``` 
### pass for admin is 801913

### 2. Project Setup

Your folder structure should look like this:

```
bankIT/
├── run.bat
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── src/
│   └── com/
│       └── banking/
│           └── BankingApp.java
```

### 3. Configuration

Update credentials inside `BankingApp.java`:

```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/forBanking";
private static final String DATABASE_USERNAME = "your_mysql_username";
private static final String DATABASE_PASSWORD = "your_mysql_password";
```

### 4. Compile and Run

If you're on Windows, use the provided `run.bat`:

```bat
@echo off
REM Compile the Java file to preserve package structure in output
javac -d . src\com\banking\BankingApp.java

REM Run the compiled class from the correct package and include the lib
java -cp ".;lib\mysql-connector-j-9.3.0.jar" src.com.banking.BankingApp
pause
```

> On Linux/macOS use `:` instead of `;` as the classpath separator.

---

## 📲 Application Interface

### Main Menu

```
1. Create New Account
2. User Login
3. Admin Login
0. Exit
```

### User Dashboard

```
1. Check Account Balance
2. Deposit Money
3. Withdraw Money
4. Transfer Money
5. View Transaction History
6. Update Account Information
7. Delete Account
0. Logout
```

### Admin Dashboard

```
1. View All Accounts
2. Search Account by Number
3. Delete Account
4. View Transaction Logs
0. Logout
```

---

## 📂 Project Structure Summary

```
bankIT/
├── run.bat
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── src/
│   └── com/
│       └── banking/
│           └── BankingApp.java
```

---

## 🔧 Quick Setup for Users

```txt
📦 SETUP INSTRUCTIONS TO RUN THIS JAVA BANKING PROJECT LOCALLY

✅ Prerequisites:
-----------------
1. Install Java JDK (8 or above)
2. Install MySQL Server
3. Create a database named `forBanking` and import the required tables (see below).
4. Clone or download this GitHub repository
5. Open a terminal or PowerShell and navigate into the project folder.

📁 Folder Structure:
--------------------
BankIt-JavaEditionJDBC/
├── run.bat
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── src/
│   └── com/
│       └── banking/
│           └── BankingApp.java

🛠️ How to Run:
---------------
Step 1: Open terminal in this folder  
Step 2: Run the batch file (Windows):

```

.\run.bat

```

Linux/macOS:
```

javac -d . src/com/banking/BankingApp.java java -cp ".\:lib/mysql-connector-j-9.3.0.jar" src.com.banking.BankingApp

````

🧠 NOTE: Make sure your `BankingApp.java` uses:
```java
package src.com.banking;
````

## 📝 Credentials Setup:

Inside `BankingApp.java`, update your DB credentials:

```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/forBanking";
private static final String DATABASE_USERNAME = "your_mysql_username";
private static final String DATABASE_PASSWORD = "your_mysql_password";
```

## 🎉 DONE!

You can now run and test the application!

````

---

## 📊 Database Schema Summary

### `users` Table

| Column      | Type         | Description             |
| ----------- | ------------ | ----------------------- |
| acc\_no     | INT, PK, AI  | Unique Account Number   |
| name        | VARCHAR(100) | User's Full Name        |
| email       | VARCHAR(100) | Unique Email            |
| phone       | BIGINT       | Unique Phone            |
| balance     | DOUBLE       | Account Balance         |
| pin         | VARCHAR(64)  | SHA-256 Hashed Password |
| is\_active  | BOOLEAN      | Account Status          |
| created\_at | TIMESTAMP    | Creation Date           |

### `logs` Table

| Column    | Type         | Description                    |
| --------- | ------------ | ------------------------------ |
| id        | INT, PK, AI  | Log ID                         |
| Who       | VARCHAR(20)  | Actor Type (User/Admin)        |
| WhoseId   | INT          | ID of Performing Account       |
| What      | VARCHAR(50)  | Action Performed               |
| WhomeId   | INT          | Target Account (if applicable) |
| message   | VARCHAR(200) | Action Details                 |
| status    | VARCHAR(20)  | Success or Failed              |
| timestamp | TIMESTAMP    | Timestamp of Action            |

---

## 🔹 Packaging as a JAR File

You can create an executable JAR to simplify distribution:

```bash
jar cfe bankIT.jar src.com.banking.BankingApp src/com/banking/*.class
````

> This creates a JAR file with the entry point `src.com.banking.BankingApp`

To run it:

```bash
java -cp "bankIT.jar;lib/mysql-connector-j-9.3.0.jar" src.com.banking.BankingApp
```

Make sure you've compiled the `.class` files using:

```bash
javac -d . src\com\banking\BankingApp.java
```

---

---

## 📄 License

This project is licensed under the **MIT License**.

## 📞 Contact 7050046913

**Aaqib alam**\
[MAIL TO ](mailto\:your.aaqibalam290@gmail.com)

GitHub: [https://github.com/sal12321/BankIt-JavaEditionJDBC](https://github.com/sal12321/BankIt-JavaEditionJDBC)

---

🌟 **If you found this project helpful, please give it a star!** 🌟

