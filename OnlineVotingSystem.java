import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;

public class OnlineVotingSystem extends JFrame {
    private JPanel candidatePanel;
    private JLabel messageLabel;
    private boolean hasVoted = false;

    private String userName;
    private String voterId;
    private String userState;

    public OnlineVotingSystem(String userEmail) {
        fetchUserDetails(userEmail);

        setTitle("Indian Voting Election");
        setSize(900, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(0, 0, 0, 200));
        navbar.setPreferredSize(new Dimension(100, 60));

        JLabel titleLabel = new JLabel("Indian Voting Election");
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navLinks.setOpaque(false);
        JButton totalVotesButton = new JButton("Total Votes");
        totalVotesButton.setBackground(new Color(76, 175, 80));
        totalVotesButton.setForeground(Color.WHITE);
        totalVotesButton.setFont(new Font("Arial", Font.BOLD, 14));
        navLinks.add(totalVotesButton);

        totalVotesButton.addActionListener(e -> showTotalVotes());

        navbar.add(titleLabel, BorderLayout.WEST);
        navbar.add(navLinks, BorderLayout.EAST);

        JPanel userInfoPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Voter Information"));
        userInfoPanel.setBackground(new Color(224, 242, 241));

        JLabel nameLabel = new JLabel("Name: " + userName);
        JLabel voterIdLabel = new JLabel("Voter ID: " + voterId);
        JLabel stateLabel = new JLabel("State: " + userState);

        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        voterIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        stateLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(voterIdLabel);
        userInfoPanel.add(stateLabel);

        candidatePanel = new JPanel(new GridLayout(1, 3, 20, 20));
        candidatePanel.setBorder(BorderFactory.createTitledBorder("Vote for Your Leader"));
        candidatePanel.setBackground(new Color(30, 60, 114));

        addCandidate(1, "PM Modi", "BJP");
        addCandidate(2, "Uddhav Thackeray", "Shivsena");
        addCandidate(3, "Rahul Gandhi", "Congress");

        messageLabel = new JLabel(" ", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(userInfoPanel, BorderLayout.NORTH);
        centerPanel.add(candidatePanel, BorderLayout.CENTER);
        centerPanel.add(messageLabel, BorderLayout.SOUTH);

        add(navbar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addCandidate(int candidateId, String name, String party) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(0, 0, 0, 150));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel imageLabel = new JLabel(new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel(name + " - " + party, SwingConstants.CENTER);
        nameLabel.setForeground(Color.YELLOW);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton voteButton = new JButton("Vote");
        voteButton.setBackground(new Color(255, 87, 34));
        voteButton.setForeground(Color.WHITE);
        voteButton.setFocusPainted(false);
        voteButton.setFont(new Font("Arial", Font.BOLD, 14));

        voteButton.addActionListener(e -> {
            if (!hasVoted) {
                if (isVotingOpen(candidateId)) {
                    castVote(candidateId);
                    messageLabel.setText("Thank you! Your vote for " + name + " has been recorded.");
                    messageLabel.setForeground(new Color(76, 175, 80));
                    hasVoted = true;
                } else {
                    messageLabel.setText("Voting is closed for this candidate.");
                    messageLabel.setForeground(Color.RED);
                }
            } else {
                messageLabel.setText("Error: You can only vote once!");
                messageLabel.setForeground(Color.RED);
            }
        });

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(nameLabel, BorderLayout.CENTER);
        card.add(voteButton, BorderLayout.SOUTH);

        candidatePanel.add(card);
    }

    private boolean isVotingOpen(int candidateId) {
        try {
                     
            Connection con = DriverManager.getConnection("jdbc:sqlserver://ADMIN:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true");
            String query = "SELECT VotingStatus FROM CandidateVotes WHERE CandidateID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, candidateId);
            ResultSet rs = pst.executeQuery();
            boolean status = rs.next() && rs.getBoolean("VotingStatus");
            rs.close();
            pst.close();
            con.close();
            return status;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void castVote(int candidateId) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://ADMIN:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true");
            String query = "UPDATE CandidateVotes SET VoteCount = VoteCount + 1 WHERE CandidateID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, candidateId);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showTotalVotes() {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://ADMIN:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true");
            String query = "SELECT CandidateName, PartyName, VoteCount FROM CandidateVotes";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append(rs.getString("CandidateName"))
                        .append(" (").append(rs.getString("PartyName")).append("): ")
                        .append(rs.getInt("VoteCount")).append(" votes\n");
            }

            rs.close();
            pst.close();
            con.close();

            JOptionPane.showMessageDialog(this, result.toString(), "Total Votes", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving vote counts.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchUserDetails(String email) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://ADMIN:1433;databaseName=VoterDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true");
            String query = "SELECT name, voter_id, state FROM Users WHERE voter_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userName = rs.getString("name");
                voterId = rs.getString("voter_id");
                userState = rs.getString("state");
            } else {
                JOptionPane.showMessageDialog(this, "User not found in database.");
                System.exit(0);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching user details.");
        }
    }

    public static void main(String[] args) {
        new OnlineVotingSystem("123456"); // Example voter_id
    }
}
