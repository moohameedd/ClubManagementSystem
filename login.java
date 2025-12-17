import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame implements ActionListener {

    JTextField txtEmail;
    JPasswordField txtPassword;
    JButton btnOk, btnAnnuler;

    public login() {
        setTitle("Login");
        setSize(450, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel() {
            Image bg = new ImageIcon("src/img1.jpg").getImage();
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        p.setLayout(null);
        setContentPane(p);

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setBounds(0, 10, 450, 40);
        p.add(title);

        JLabel l1 = new JLabel("Email:");
        JLabel l2 = new JLabel("Password:");
        l1.setBounds(50, 80, 100, 25);
        l2.setBounds(50, 130, 100, 25);
        p.add(l1); p.add(l2);

        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        txtEmail.setBounds(150, 80, 200, 25);
        txtPassword.setBounds(150, 130, 200, 25);
        p.add(txtEmail); p.add(txtPassword);

        btnOk = new JButton("OK");
        btnAnnuler = new JButton("Annuler");
        btnOk.setBounds(100, 200, 100, 30);
        btnAnnuler.setBounds(250, 200, 100, 30);
        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);
        p.add(btnOk); p.add(btnAnnuler);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnuler) dispose();
        if (e.getSource() == btnOk) loginUser();
    }

    void loginUser() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection cn = ConnexionBD.getConnection();
            PreparedStatement ps =
                cn.prepareStatement("SELECT password FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "User inexistant ❌");
            } else if (!rs.getString("password").equals(password)) {
                JOptionPane.showMessageDialog(this, "Password incorrect ❌");
            } else {
                JOptionPane.showMessageDialog(this, "Login réussi ✔");
                new choix(email);
                dispose();
            }
            cn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur BD ❌");
        }
    }
}
