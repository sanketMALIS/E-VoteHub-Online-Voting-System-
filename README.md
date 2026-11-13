# 🗳️ E-VoteHub – Online Voting System

**E-VoteHub** is a Java-based Online Voting System designed to make the voting process simple, user-friendly, and efficient. It allows users to register, log in, view candidates, and cast their votes electronically.

The application uses Java Swing for its graphical user interface and Microsoft SQL Server for storing voter information and managing vote counts.

## 🚀 Features

- **Voter Registration:** Register voters by entering personal details such as name, Aadhaar number, date of birth, email, mobile number, and address.
- **Automatic Voter ID Generation:** Generates a unique Voter ID during registration.
- **Login System:** Provides a voter login interface with Voter ID, password, and OTP fields.
- **Candidate Display:** Displays available candidates and their political parties.
- **Online Voting:** Allows registered voters to cast their votes electronically.
- **One-Time Voting:** Prevents a voter from casting multiple votes during the same application session.
- **Voting Status:** Checks whether voting is open or closed for a candidate.
- **Vote Counting:** Automatically updates vote counts in the database.
- **Vote Results:** Displays the total votes received by each candidate.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core application development |
| Java Swing | Graphical User Interface |
| AWT | GUI components and event handling |
| JDBC | Database connectivity |
| Microsoft SQL Server | Database management |
| SQL | Storing and retrieving voter and voting information |
| VS Code | Development environment |

## 📂 Project Structure

```text
E-VoteHub-Online-Voting-System/
│
├── Screenshots/              # Application screenshots
│
├── lib/                      # External JAR dependencies
│   └── mssql-jdbc.jar        # SQL Server JDBC driver
│
├── RegistrationForm.java     # Voter registration
├── LoginPage.java            # Voter login
├── OnlineVotingSystem.java   # Voting dashboard
│
├── README.md                 # Project documentation
└── .gitignore                # Git ignored files
```

## ⚙️ Installation and Setup

### 1. Prerequisites

Make sure you have installed:

- Java JDK
- Visual Studio Code or any Java IDE
- Microsoft SQL Server
- SQL Server Management Studio (SSMS)
- Microsoft SQL Server JDBC Driver

### 2. Clone the Repository

Open your terminal and execute:

```bash
git clone https://github.com/sanketMALIS/E-VoteHub-Online-Voting-System-.git
```

Navigate to the project directory:

```bash
cd E-VoteHub-Online-Voting-System-
```

### 3. Database Configuration

1. Open SQL Server Management Studio.
2. Create a database named `VoterDB`.
3. Create the required `Users` and `CandidateVotes` tables with the columns used by the Java application.
4. Configure SQL Server authentication and JDBC connectivity.

**Important:** Update the database connection URL in the Java files according to your SQL Server hostname, port, and authentication settings.

Example:

```java
String url =
"jdbc:sqlserver://localhost:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
```

### 4. Add JDBC Driver

Add the Microsoft SQL Server JDBC driver JAR file to your project's classpath.

### 5. Run the Application

Run the following Java files in order:

1. `RegistrationForm.java`
2. `LoginPage.java`
3. `OnlineVotingSystem.java`

You can also open the project in VS Code and run the Java files using the Java Extension Pack.

## 🔄 Application Workflow

```text
        Start Application
               |
               ▼
       Voter Registration
               |
               ▼
       Generate Voter ID
               |
               ▼
          Voter Login
               |
               ▼
       View Candidates
               |
               ▼
          Cast Vote
               |
               ▼
       Update Vote Count
               |
               ▼
        Display Results
```

## 📸 Screenshots

Application screenshots are available in the `Screenshots` folder.

## 🎯 Project Objectives

- To develop a user-friendly digital voting application.
- To simplify the voter registration and voting process.
- To understand Java GUI development using Swing and AWT.
- To implement database connectivity using JDBC.
- To manage and maintain voting records using SQL Server.

## 🔮 Future Enhancements

- Email-based OTP verification.
- Face recognition and fingerprint authentication.
- Improved voter identity verification.
- Secure voter ID card generation and downloading.
- Enhanced admin dashboard and election management.

## 👨‍💻 Developer

**Sanket Mali**

- GitHub: [@sanketMALIS](https://github.com/sanketMALIS)
- Project Repository: [E-VoteHub – Online Voting System](https://github.com/sanketMALIS/E-VoteHub-Online-Voting-System-)

## 📌 Disclaimer

This project is developed for educational and academic purposes. It is a demonstration application and is not certified or intended for conducting real-world public elections.

---

⭐ If you find this project interesting, feel free to explore the repository!
