import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CalendrierActivity extends JFrame {

    JTable table;
    DefaultTableModel model;

    public CalendrierActivity() {
        setTitle("Calendrier d'Activités");
        setSize(700, 500); // taille plus grande
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // JPanel avec image de fond
        JPanel panel = new JPanel() {
            Image bg = new ImageIcon("src/img1.jpg").getImage(); // même image que Inscription

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        // Titre
        JLabel title = new JLabel("Calendrier d'Activités", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Table model avec deux colonnes
        model = new DefaultTableModel();
        model.addColumn("Event");
        model.addColumn("Date");

        // JTable
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30); // hauteur des lignes
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(200, 200, 200));

        // JScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scroll, BorderLayout.CENTER);

        loadActivities();

        setVisible(true);
    }

    // Charger les activités depuis la BD
    void loadActivities() {
        try {
            Connection cn = ConnexionBD.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nom_activitee, date_activitee FROM activitee");

            while (rs.next()) {
                String event = rs.getString("nom_activitee");
                String date = rs.getString("date_activitee");
                model.addRow(new Object[]{event, date});
            }

            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement activités ❌");
            e.printStackTrace();
        }
    }


   
}
