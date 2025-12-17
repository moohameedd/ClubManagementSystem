import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Inscription extends JFrame implements ActionListener {

    JTextField txtNom, txtPrenom, txtEmail, txtRole;
    JPasswordField txtPassword;
    JComboBox<String> cbClub;
    JButton btnOk, btnAnnuler;

    public Inscription() {
        setTitle("Inscription");
        setSize(450, 400);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Labels
        JLabel l1 = new JLabel("Nom:");
        JLabel l2 = new JLabel("Prénom:");
        JLabel l3 = new JLabel("Email:");
        JLabel l4 = new JLabel("Password:");
        JLabel l5 = new JLabel("Club:");
        JLabel l6 = new JLabel("Role:");

        l1.setBounds(50, 40, 100, 25);
        l2.setBounds(50, 80, 100, 25);
        l3.setBounds(50, 120, 100, 25);
        l4.setBounds(50, 160, 100, 25);
        l5.setBounds(50, 200, 100, 25);
        l6.setBounds(50, 240, 100, 25);

        // Fields
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        txtRole = new JTextField(); // zone de texte pour role

        txtNom.setBounds(170, 40, 180, 25);
        txtPrenom.setBounds(170, 80, 180, 25);
        txtEmail.setBounds(170, 120, 180, 25);
        txtPassword.setBounds(170, 160, 180, 25);
        txtRole.setBounds(170, 240, 180, 25);

        // ComboBox clubs
        cbClub = new JComboBox<>();
        cbClub.setBounds(170, 200, 180, 25);
        loadClubs();

        // Buttons
        btnOk = new JButton("OK");
        btnAnnuler = new JButton("Annuler");

        btnOk.setBounds(90, 290, 100, 30);
        btnAnnuler.setBounds(220, 290, 100, 30);

        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);

        // Add components
        add(l1); add(l2); add(l3); add(l4); add(l5); add(l6);
        add(txtNom); add(txtPrenom); add(txtEmail); add(txtPassword);
        add(cbClub); add(txtRole);
        add(btnOk); add(btnAnnuler);

        setVisible(true);
    }

    // Charger clubs depuis la BD
    void loadClubs() {
        try {
            Connection cn = ConnexionBD.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nom_club FROM club");

            while (rs.next()) {
                cbClub.addItem(rs.getString("nom_club"));
            }
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement clubs ❌");
        }
    }

    // Vérifier si email existe
    boolean emailExists(String email) {
        try {
            Connection cn = ConnexionBD.getConnection();
            String sql = "SELECT id_user FROM users WHERE email = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            cn.close();
            return exists;
        } catch (Exception e) {
            return true; // sécurité si erreur
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnuler) {
            dispose();
        }
        if (e.getSource() == btnOk) {
            insertUser();
        }
    }

    void insertUser() {
        if (txtNom.getText().isEmpty() ||
            txtPrenom.getText().isEmpty() ||
            txtEmail.getText().isEmpty() ||
            txtPassword.getPassword().length == 0 ||
            txtRole.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Remplissez tous les champs ❌");
            return;
        }

        if (emailExists(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email déjà utilisé ❌");
            return;
        }

        Connection cn = null;

        try {
            cn = ConnexionBD.getConnection();

            String sql =
              "INSERT INTO users (nom, prenom, email, password, id_club, role) " +
              "VALUES (?, ?, ?, ?, (SELECT id_club FROM club WHERE nom_club=?), ?)";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, txtNom.getText());
            ps.setString(2, txtPrenom.getText());
            ps.setString(3, txtEmail.getText());
            ps.setString(4, new String(txtPassword.getPassword()));
            ps.setString(5, cbClub.getSelectedItem().toString());
            ps.setString(6, txtRole.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inscription réussie ✔");
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Erreur BD : Email déjà existant ❌");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur de base de données ❌ : " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur inattendue ❌ : " + ex.getMessage());
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
