# 🏦 Banking Management System

A comprehensive console-based banking application built with Java and MySQL, featuring secure user authentication, transaction management, and administrative controls.

## ✨ Features

### 👤 User Features
- **Account Management**
  - Create new bank accounts with unique account numbers
  - Secure login with SHA-256 password hashing
  - Update personal information (name, email, password)
  - Delete account functionality

- **Banking Operations**
  - Check account balance
  - Deposit money with validation
  - Withdraw money (insufficient balance protection)
  - Transfer money between accounts
  - Real-time balance updates

### 👨‍💼 Admin Features
- **Account Management**
  - View all user accounts
  - Search accounts by account number
  - Delete user accounts
  - Account status monitoring

- **System Monitoring**
  - View all transaction logs
  - Activity tracking with timestamps
  - Success/failure status monitoring

### 🔒 Security Features
- Password hashing using SHA-256 algorithm
- Account status validation (active/inactive)
- Input validation and error handling
- SQL injection prevention with PreparedStatements

## 🛠️ Technologies Used

- **Programming Language**: Java
- **Database**: MySQL
- **JDBC Driver**: MySQL Connector/J
- **Security**: SHA-256 Hashing
- **Architecture**: Console-based application

## 📋 Prerequisites

Before running this application, ensure you have:

- Java Development Kit (JDK) 8 or higher
- MySQL Server 8.0 or higher
- MySQL Connector/J driver
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🚀 Setup Instructions

### 1. Database Setup

Create a MySQL database and tables:

```sql
-- Create database
CREATE DATABASE forBanking;
USE forBanking;

-- Create users table
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

-- Create admins table
CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create logs table for transaction tracking
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

-- Insert default admin (username: admin, password: 801913)
INSERT INTO admins (username, password) VALUES 
('admin', '039df8f1999cb4a6675860fe51c00f48ca7a9c3489062e476182ee1e8ba5e89a'); 
```

### 2. Project Setup

1. Clone the repository:
```bash
git clone https://github.com/sal12321/BankIt-JavaEditionJDBC
cd BankIT
```

2. Add MySQL Connector/J to your classpath:
   - Download from [MySQL official website](https://dev.mysql.com/downloads/connector/j/)
   - Add to your IDE's classpath or use Maven/Gradle

3. Update database credentials in `BankingApp.java`:
```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/forBanking";
private static final String DATABASE_USERNAME = "your_username";
private static final String DATABASE_PASSWORD = "your_password";
```

### 3. Running the Application

1. Compile the Java file:
```bash
javac -cp ".:mysql-connector-java-8.0.33.jar" BankingApp.java
```

2. Run the application:
```bash
java -cp ".:mysql-connector-java-8.0.33.jar" BankingApp
```

## 📱 Usage Examples

### Main Menu
```
╔═════════════════════════════════════╗
║         ...MAIN MENU...             ║
╠═════════════════════════════════════╣
║  1  Create New Account              ║
║  2  User Login                      ║
║  3  Admin Login                     ║
║  0  Exit                            ║
╚═════════════════════════════════════╝
```

### User Dashboard
```
╔══════════════════════════════════════╗
║           USER DASHBOARD...          ║
║           Account: 1001              ║
╠══════════════════════════════════════╣
║  1  Check Account Balance            ║
║  2  Deposit Money                    ║
║  3  Withdraw Money                   ║
║  4  Transfer Money                   ║
║  5  View Transaction History         ║
║  6  Update Account Information       ║
║  7  Delete Account                   ║
║  0  Logout                           ║
╚══════════════════════════════════════╝
```

## 🗂️ Project Structure

```
banking-management-system/
│
├── src/
│   └── com/banking/
│       └── BankingApp.java
├── lib/
│   └── mysql-connector-java-8.0.33.jar
├── database/
│   └── schema.sql
├── docs/
│   └── screenshots/
├── README.md
├── .gitignore
└── LICENSE
```

## 📊 Database Schema

### Users Table
| Column | Type         | Description           |
|--------|------        |-------------          |
| acc_no | INT (PK, AI) | Unique account number |
| name   | VARCHAR(100) | User's full name      |
| email  | VARCHAR(100) | User's email address  |
| phone  | BIGINT       | User's phone number   |
| balance| DOUBLE       | Current account balance |
| pin    | VARCHAR(64)  | Hashed password       |
| is_active| BOOLEAN    | Account status        |
| created_at| TIMESTAMP | Account creation date |

### Logs Table
| Column | Type           | Description           |
|--------|------         |-------------           |
| id      | INT (PK, AI) | Log entry ID           |
| Who     | VARCHAR(20) | User type (User/Admin)  |
| WhoseId | INT         | Account performing action |
| What    | VARCHAR(50) | Action performed        |
| WhomeId | INT         | Target account (if applicable) |
| message | VARCHAR(200)| Action description     |
| status   | VARCHAR(20)| Success/Failed         |
| timestamp| TIMESTAMP  | When action occurred   |

## 🔧 Key Features Explained

### Security Implementation
- **Password Hashing**: All passwords are hashed using SHA-256 before storage
- **SQL Injection Prevention**: PreparedStatements used throughout
- **Account Validation**: Active account checks before operations

### Error Handling
- Input validation with custom methods
- Database connection error handling
- Transaction rollback capabilities
- Comprehensive logging system

### User Experience
- Intuitive menu-driven interface
- Clear success/error messages
- Formatted currency display
- Account information confirmation for transfers

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact 7050046913

Your Name - [aaqib alam](mailto:your.aaqibalam290@gmail.com)

Project Link: [https://github.com/sal12321/BankIt-JavaEditionJDBC](https://github.com/sal12321/BankIt-JavaEditionJDBC)

## 🙏 Acknowledgments

- MySQL for the robust database system
- Java community for excellent documentation
- Contributors and testers who helped improve this project

---

⭐ **If you found this project helpful, please consider giving it a star!** ⭐