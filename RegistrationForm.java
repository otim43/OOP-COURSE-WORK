 import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.sql.*;



public class RegistrationForm extends JFrame {

    private JTextField txtRegID, txtName, txtFaculty, txtTitle, txtContact, txtEmail;
    private JButton btnRegister, btnSearch, btnUpdate, btnDelete, btnClear, btnExit, btnUploadImage;
    private JLabel lblImage;
    private String selectedImagePath = "";

    public RegistrationForm() {
        setTitle("Exhibition Registration Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 2, 5, 5));

        txtRegID = new JTextField();
        txtName = new JTextField();
        txtFaculty = new JTextField();
        txtTitle = new JTextField();
        txtContact = new JTextField();
        txtEmail = new JTextField();
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(150, 150));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnRegister = new JButton("Register");
        btnSearch = new JButton("Find");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");
        btnUploadImage = new JButton("Upload Image");

        add(new JLabel("Registration ID:")); add(txtRegID);
        add(new JLabel("Student Name:")); add(txtName);
        add(new JLabel("Faculty:")); add(txtFaculty);
        add(new JLabel("Project Title:")); add(txtTitle);
        add(new JLabel("Contact Number:")); add(txtContact);
        add(new JLabel("Email Address:")); add(txtEmail);
        add(btnUploadImage); add(lblImage);
        add(btnRegister); add(btnSearch);
        add(btnUpdate); add(btnDelete);
        add(btnClear); add(btnExit);

        // ACTIONS
        btnRegister.addActionListener(e -> registerParticipant());
        btnSearch.addActionListener(e -> searchParticipant());
        btnUpdate.addActionListener(e -> updateParticipant());
        btnDelete.addActionListener(e -> deleteParticipant());
        btnClear.addActionListener(e -> clearFields());
        btnExit.addActionListener(e -> System.exit(0));
        btnUploadImage.addActionListener(e -> chooseImage());

        setVisible(true);
    }

    private Connection connect() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = new File("VUE_Exhibition.accdb").getAbsolutePath();
            return DriverManager.getConnection("jdbc:ucanaccess://" + path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            return null;
        }
    }

    private void registerParticipant() {
        if (!validateFields()) return;

        try (Connection conn = connect()) {
            String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtRegID.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, txtFaculty.getText());
            ps.setString(4, txtTitle.getText());
            ps.setString(5, txtContact.getText());
            ps.setString(6, txtEmail.getText());
            ps.setString(7, selectedImagePath);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Participant registered successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchParticipant() {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM Participants WHERE RegistrationID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtRegID.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("StudentName"));
                txtFaculty.setText(rs.getString("Faculty"));
                txtTitle.setText(rs.getString("ProjectTitle"));
                txtContact.setText(rs.getString("ContactNumber"));
                txtEmail.setText(rs.getString("EmailAddress"));
                selectedImagePath = rs.getString("ImagePath");

                if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    lblImage.setIcon(icon);
                } else {
                    lblImage.setIcon(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Participant not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Search error: " + e.getMessage());
        }
    }

    private void updateParticipant() {
        if (!validateFields()) return;

        try (Connection conn = connect()) {
            String sql = "UPDATE Participants SET StudentName=?, Faculty=?, ProjectTitle=?, ContactNumber=?, EmailAddress=?, ImagePath=? WHERE RegistrationID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtName.getText());
            ps.setString(2, txtFaculty.getText());
            ps.setString(3, txtTitle.getText());
            ps.setString(4, txtContact.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, selectedImagePath);
            ps.setString(7, txtRegID.getText());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Participant updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Participant not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage());
        }
    }

    private void deleteParticipant() {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM Participants WHERE RegistrationID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtRegID.getText());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Participant deleted.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed. Participant not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete error: " + e.getMessage());
        }
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose Project Image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File imgFile = chooser.getSelectedFile();
            selectedImagePath = imgFile.getAbsolutePath();
            ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            lblImage.setIcon(icon);
        }
    }

    private void clearFields() {
        txtRegID.setText("");
        txtName.setText("");
        txtFaculty.setText("");
        txtTitle.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        lblImage.setIcon(null);
        selectedImagePath = "";
    }

    private boolean validateFields() {
        if (txtRegID.getText().isEmpty() || txtName.getText().isEmpty() || txtFaculty.getText().isEmpty() ||
            txtTitle.getText().isEmpty() || txtContact.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return false;
        }

        if (!txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
