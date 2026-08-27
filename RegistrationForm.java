import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.swing.*;

public class RegistrationForm extends JFrame {

  private JTextField nameField, voterIdField, aadhaarField, dobField, emailField, mobileField;
  private JTextArea addressArea;
  private JPasswordField passwordField, confirmPasswordField;
  private JButton submitButton;
  private JLabel loginLabel, ageLabel, titleLabel;
  private JComboBox<String> stateComboBox;

  public RegistrationForm() {
    setTitle("E-VoteHub - Registration Form");
    setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    // Set Background Color
    getContentPane().setBackground(Color.BLACK);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Title Label
    titleLabel = new JLabel("E-VoteHub - Registration Form", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(Color.WHITE);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(titleLabel, gbc);

    gbc.gridwidth = 1;
    gbc.gridy++;
    addLabel("Voter Name:", gbc);
nameField = createTextField(gbc);

addLabel("Voter ID:", gbc);                      // <-- ADD THIS
voterIdField = createTextField(gbc);             // <-- AND THIS
voterIdField.setEditable(false);                 // Optional


    addLabel("State:", gbc);
    stateComboBox =
      new JComboBox<String>(
        new String[] {
          "Maharashtra",
          "Gujarat",
          "Karnataka",
          "Tamil Nadu",
          "Delhi",
          "Rajasthan",
        }
      );

    stateComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 1;
    add(stateComboBox, gbc);

    addLabel("Aadhaar Number (12 digits):", gbc);
    aadhaarField = createTextField(gbc);

    addLabel("Date of Birth (dd-MM-yyyy):", gbc);
    dobField = createTextField(gbc);

    addLabel("Age:", gbc);
    ageLabel = new JLabel("Enter DOB to calculate");
    ageLabel.setFont(new Font("Arial", Font.BOLD, 16));
    ageLabel.setForeground(Color.WHITE);
    gbc.gridx = 1;
    add(ageLabel, gbc);

    addLabel("Address:", gbc);
    addressArea = new JTextArea(3, 20);
    addressArea.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 1;
    add(new JScrollPane(addressArea), gbc);

    addLabel("Mobile Number (10 digits):", gbc);
    mobileField = createTextField(gbc);

    addLabel("Email:", gbc);
    emailField = createTextField(gbc);

    addLabel("Password:", gbc);
    passwordField = new JPasswordField(20);
    gbc.gridx = 1;
    add(passwordField, gbc);

    addLabel("Confirm Password:", gbc);
    confirmPasswordField = new JPasswordField(20);
    gbc.gridx = 1;
    add(confirmPasswordField, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    submitButton = new JButton("Submit");
    submitButton.setFont(new Font("Arial", Font.BOLD, 16));
    submitButton.setBackground(Color.GREEN);
    submitButton.setForeground(Color.BLACK);
    gbc.gridx = 1;
    add(submitButton, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    loginLabel = new JLabel("Already registered? Login here.", JLabel.CENTER);
    loginLabel.setForeground(Color.CYAN);
    loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
    loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    gbc.gridx = 1;
    add(loginLabel, gbc);

    // Event Listeners
    dobField.addFocusListener(
      new FocusAdapter() {
        public void focusLost(FocusEvent e) {
          calculateAge();
        }
      }
    );

    submitButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          registerUser();
        }
      }
    );

    loginLabel.addMouseListener(
      new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          dispose(); // Close Registration Form
          new LoginPage(); // Open Login Page (make sure LoginPageEVM class exists)
        }
      }
    );

    setVisible(true);
  }

  private void addLabel(String text, GridBagConstraints gbc) {
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel label = new JLabel(text);
    label.setForeground(Color.WHITE);
    add(label, gbc);
  }

  private JTextField createTextField(GridBagConstraints gbc) {
    gbc.gridx = 1;
    JTextField textField = new JTextField(20);
    textField.setFont(new Font("Arial", Font.PLAIN, 16));
    add(textField, gbc);
    return textField;
  }

  private void calculateAge() {
    String dobText = dobField.getText();
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
      sdf.setLenient(false);
      java.util.Date birthDate = sdf.parse(dobText); // <-- FIXED here

      Calendar dob = Calendar.getInstance();
      dob.setTime(birthDate);
      Calendar today = Calendar.getInstance();

      int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
      if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--;
      }

      if (age < 18) {
        ageLabel.setText("Not eligible to vote (Age: " + age + ")");
        ageLabel.setForeground(Color.RED);
        submitButton.setEnabled(false);
      } else {
        ageLabel.setText("Eligible to vote (Age: " + age + ")");
        ageLabel.setForeground(Color.GREEN);
        submitButton.setEnabled(true);
      }
    } catch (ParseException ex) {
      ageLabel.setText("Invalid format! Use dd-MM-yyyy");
      ageLabel.setForeground(Color.RED);
      submitButton.setEnabled(false);
    }
  }

  private void registerUser() {
    String name = nameField.getText();
    String aadhaar = aadhaarField.getText();
    String address = addressArea.getText();
    String email = emailField.getText();
    String mobile = mobileField.getText();
    String password = new String(passwordField.getPassword());
    String confirmPassword = new String(confirmPasswordField.getPassword());
    String state = (String) stateComboBox.getSelectedItem();

    if (
    name.trim().isEmpty() ||
    aadhaar.trim().isEmpty() ||
    dobField.getText().trim().isEmpty() ||
    address.trim().isEmpty() ||
    email.trim().isEmpty() ||
    mobile.trim().isEmpty() ||
    password.trim().isEmpty() ||
    confirmPassword.trim().isEmpty()
) {
    JOptionPane.showMessageDialog(
        this,
        "Please fill out all fields before submitting.",
        "Missing Fields",
        JOptionPane.ERROR_MESSAGE
    );
    return;
}


    if (state == null || state.trim().equals("")) {
      JOptionPane.showMessageDialog(
        this,
        "Please select a state.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    if (!aadhaar.matches("\\d{12}")) {
      JOptionPane.showMessageDialog(
        this,
        "Aadhaar must be 12 digits.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    if (!mobile.matches("\\d{10}")) {
      JOptionPane.showMessageDialog(
        this,
        "Mobile number must be 10 digits.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
      JOptionPane.showMessageDialog(
        this,
        "Invalid email format.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    if (!password.equals(confirmPassword)) {
      JOptionPane.showMessageDialog(
        this,
        "Passwords do not match.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    // Generate Voter ID
    String voterId = generateVoterId(state);

    System.out.println("Register button clicked.");
    System.out.println(
      "Calling DB insert with data: " + name + ", " + voterId + ", " + state
    );
    
    java.sql.Date sqlDob;
try {
    String dobText = dobField.getText().trim();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false); // Ensures strict parsing
    java.util.Date parsedDob = sdf.parse(dobText);
    sqlDob = new java.sql.Date(parsedDob.getTime());
} catch (ParseException ex) {
    JOptionPane.showMessageDialog(this, "Invalid DOB format. Use dd-MM-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
    return;
}


    // Save to DB
    saveToDatabase(name,aadhaar,sqlDob,address,email,mobile,password,voterId,state);

    // Show generated Voter ID
    JOptionPane.showMessageDialog(
      this,
      "Registration successful!\nYour Voter ID: " + voterId,
      "Success",
      JOptionPane.INFORMATION_MESSAGE
    );
    clearForm();
  }

  private String generateVoterId(String state) {
    String prefix = state.replaceAll("[^A-Za-z]", "").toUpperCase();
    prefix =
      prefix.length() >= 3
        ? prefix.substring(0, 3)
        : String.format("%-3s", prefix).replace(' ', 'X');

    String uuidPart = UUID
      .randomUUID()
      .toString()
      .replaceAll("-", "")
      .substring(0, 6)
      .toUpperCase();

    return prefix + uuidPart;
  }

  private void saveToDatabase(String name,String aadhaar,Date dob,String address,String email,String mobile,String password,String voter_id,
    String state)
     {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      // Load SQL Server JDBC driver
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

      // Connect to SQL Server (update hostname, instance, and credentials accordingly)
      String url =
        "jdbc:sqlserver://ADMIN:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
      conn = DriverManager.getConnection(url);

      System.out.println("Connected to DB successfully");

      // SQL INSERT query (without voter_id)
      String sql =
        "INSERT INTO Users (name, aadhaar, dob, address, email, mobile, password,voter_id, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, name);
      pstmt.setString(2, aadhaar);
      pstmt.setDate(3, dob);
      pstmt.setString(4, address);
      pstmt.setString(5, email);
      pstmt.setString(6, mobile);
      pstmt.setString(7, password);
      pstmt.setString(8, voter_id);
      pstmt.setString(9, state);

      int rowsInserted = pstmt.executeUpdate();

      if (rowsInserted > 0) {
        JOptionPane.showMessageDialog(
          this,
          "Registration Successful!",
          "Success",
          JOptionPane.INFORMATION_MESSAGE
        );
        clearForm(); // You should define this method to reset your form fields
      } else {
        JOptionPane.showMessageDialog(
          this,
          "Registration Failed.",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
        this,
        "Error: " + e.getMessage(),
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      e.printStackTrace();
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void clearForm() {
    nameField.setText("");
    voterIdField.setText("");
    aadhaarField.setText("");
    dobField.setText("");
    addressArea.setText("");
    emailField.setText("");
    mobileField.setText("");
    passwordField.setText("");
    confirmPasswordField.setText("");
    ageLabel.setText("Enter DOB to calculate");
  }

  public static void main(String[] args) {
    new RegistrationForm();
  }
}
