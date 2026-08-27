import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LoginPage extends JFrame {
    private JTextField voterIdField;
    private JPasswordField passwordField;
    private JTextField otpField;
    private JButton loginButton, sendOtpButton, uploadButton;
    private JLabel fileLabel;

    public LoginPage() {
        setTitle("E-VoteHub - Secure Login");
        setSize(500, 500);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Main panel with gradient background
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 50, 100), getWidth(), getHeight(), new Color(0, 100, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3)); // Golden Border (Java 7/8 Compatible)
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("E-VoteHub - Secure Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        addLabel(panel, "Voter ID:", gbc);
        gbc.gridx = 1;
        voterIdField = addTextField(panel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        addLabel(panel, "Password:", gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        styleTextField(passwordField);
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        addLabel(panel, "OTP:", gbc);
        gbc.gridx = 1;
        otpField = addTextField(panel, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        sendOtpButton = new JButton("Send OTP");
        styleButton(sendOtpButton);
        panel.add(sendOtpButton, gbc);

        gbc.gridy = 5;
        loginButton = new JButton("Login");
        styleButton(loginButton);
        panel.add(loginButton, gbc);

        gbc.gridy = 6;
        uploadButton = new JButton("Upload Voter ID Card");
        styleButton(uploadButton);
        panel.add(uploadButton, gbc);

        gbc.gridy = 7;
        fileLabel = new JLabel("No file selected");
        fileLabel.setForeground(Color.WHITE);
        panel.add(fileLabel, gbc);

        gbc.gridy = 8;
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.RED);
        closeButton.setBackground(Color.BLACK);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(closeButton, gbc);

        // Action Listeners
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

        loginButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Example login check (use proper validation here)
		        String voterId = voterIdField.getText();
		        char[] password = passwordField.getPassword();
		        String otp = otpField.getText();

		        // Use length() method to check if the string is empty
		        if (voterId.length() > 0 && password.length > 0 && otp.length() > 0) {
		            // Assuming login is successful, open the OnlineVotingSystem page
		            new OnlineVotingSystem(voterId);  // Open the OnlineVotingSystem window
		            dispose();  // Close the login page
		        } else {
		            JOptionPane.showMessageDialog(LoginPage.this, "Please enter valid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});


        setVisible(true);
    }

    private void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".pdf")) {
                fileLabel.setText("Selected: " + fileName);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid file format! Please upload JPG, PNG, or PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addLabel(JPanel panel, String text, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);
    }

    private JTextField addTextField(JPanel panel, GridBagConstraints gbc) {
        JTextField textField = new JTextField(15);
        styleTextField(textField);
        panel.add(textField, gbc);
        return textField;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2), // Java 7/8 Compatible
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setBackground(new Color(230, 230, 230));
    }

    private void styleButton(final JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Java 7/8 Compatible

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 82, 164));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 102, 204));
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
