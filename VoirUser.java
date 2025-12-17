import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VoirUser extends JFrame {

    DefaultTableModel model;
    String email;

    public VoirUser(String email) {
        this.email = email;

        setTitle("Mon Profil");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        setContentPane(panel);

        JLabel title = new JLabel("Mes Informations", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new String[]{"Nom","Prénom","Email","Club","Activité","Date"},0);

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        loadUser();
        setVisible(true);
    }

    void loadUser() {
        try {
            Connection cn = ConnexionBD.getConnection();
            String sql =
              "SELECT u.nom,u.prenom,u.email,c.nom_club," +
              "a.nom_activitee,a.date_activitee " +
              "FROM users u " +
              "LEFT JOIN club c ON u.id_club=c.id_club " +
              "LEFT JOIN activitee a ON u.id_user=a.id_user " +
              "WHERE u.email=?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6)
                });
            }
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement ❌");
        }
    }
}
