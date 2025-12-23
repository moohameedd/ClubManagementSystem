import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VoirUser extends JFrame {

    JTable table;
    DefaultTableModel model;
    String email;

    public VoirUser(String email) {
        this.email = email;
        setTitle("Mon Profil");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //panel avec background
        JPanel panel=new JPanel() {
            Image bg=new ImageIcon("src/img1.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg,0,0,getWidth(),getHeight(),this);
            }
        };
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        //title
        JLabel title=new JLabel("Mon Profil",SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS",Font.BOLD,28));
        title.setBorder(BorderFactory.createEmptyBorder(15,0, 15, 0));
        panel.add(title, BorderLayout.NORTH);

        //table
        model=new DefaultTableModel();
        model.addColumn("Champ");
        model.addColumn("Valeur");

        table=new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial",Font.BOLD,15));
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,16));

        JScrollPane scroll=new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        panel.add(scroll,BorderLayout.CENTER);

        loadUser();
        setVisible(true);
    }

    void loadUser() {
        try {
            Connection cn = ConnexionBD.getConnection();
            //select using join
            String sql =
                "SELECT u.nom, u.prenom, u.email, c.nom_club, " +
                "a.nom_activitee, a.date_activitee " +
                "FROM users u " +
                "LEFT JOIN club c ON u.id_club = c.id_club " +
                "LEFT JOIN activitee a ON u.id_user = a.id_user " +
                "WHERE u.email = ?";

            PreparedStatement ps=cn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs =ps.executeQuery();

            model.setRowCount(0);

            if (rs.next()) {
                model.addRow(new Object[]{"Nom",rs.getString("nom")});
                model.addRow(new Object[]{"Prénom",rs.getString("prenom")});
                model.addRow(new Object[]{"Email",rs.getString("email")});
                model.addRow(new Object[]{"Club",rs.getString("nom_club")});
                model.addRow(new Object[]{"Activité",rs.getString("nom_activitee")});
                model.addRow(new Object[]{"Date activité",rs.getString("date_activitee")});
            }

            cn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Erreur chargement profil");
            e.printStackTrace();
        }
    }
}
