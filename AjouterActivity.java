import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AjouterActivity extends JFrame implements ActionListener {

    JTextField txtNom, txtType, txtDate;
    JButton btnOk, btnAnnuler;
    String email;

    public AjouterActivity(String email) {
        this.email = email;

        setTitle("Ajouter Activité");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 🔹 Panel avec image de fond
        JPanel panel = new JPanel() {
            Image bg = new ImageIcon("src/img1.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        // Title
        JLabel title = new JLabel("Ajouter Activité", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        title.setBounds(0, 20, 500, 40);
        panel.add(title);

        // Labels
        JLabel l1 = new JLabel("Nom activité:");
        JLabel l2 = new JLabel("Type:");
        JLabel l3 = new JLabel("Date (YYYY-MM-DD):");

        l1.setBounds(50, 100, 150, 25);
        l2.setBounds(50, 150, 150, 25);
        l3.setBounds(50, 200, 180, 25);

        panel.add(l1);
        panel.add(l2);
        panel.add(l3);

        // Text fields
        txtNom = new JTextField();
        txtType = new JTextField();
        txtDate = new JTextField();

        txtNom.setBounds(230, 100, 200, 25);
        txtType.setBounds(230, 150, 200, 25);
        txtDate.setBounds(230, 200, 200, 25);

        panel.add(txtNom);
        panel.add(txtType);
        panel.add(txtDate);

        // Buttons
        btnOk = new JButton("OK");
        btnAnnuler = new JButton("Annuler");

        btnOk.setBounds(120, 270, 100, 30);
        btnAnnuler.setBounds(260, 270, 100, 30);

        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);

        panel.add(btnOk);
        panel.add(btnAnnuler);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnuler) dispose();
        if (e.getSource() == btnOk) insertActivity();
    }

    void insertActivity() {
        try {
            Connection cn = ConnexionBD.getConnection();

            PreparedStatement psUser =
                cn.prepareStatement("SELECT id_user, id_club FROM users WHERE email=?");
            psUser.setString(1, email);
            ResultSet rs = psUser.executeQuery();
            rs.next();

            PreparedStatement ps =
                cn.prepareStatement(
                    "INSERT INTO activitee (nom_activitee, type, date_activitee, id_club, id_user) " +
                    "VALUES (?, ?, ?, ?, ?)");

            ps.setString(1, txtNom.getText());
            ps.setString(2, txtType.getText());
            ps.setString(3, txtDate.getText());
            ps.setInt(4, rs.getInt("id_club"));
            ps.setInt(5, rs.getInt("id_user"));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Activité ajoutée ✔");
            cn.close();
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur BD ❌");
            ex.printStackTrace();
        }
    }
}
