import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inscription extends JFrame implements ActionListener {

    JTextField txtNom,txtPrenom,txtEmail,txtRole;
    JPasswordField txtPassword;
    JComboBox<String> cbClub;
    JButton btnOk,btnAnnuler;

    public Inscription() {
        setTitle("Inscription");
        setSize(450, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // centrer la fenêtre

        // JPanel avec image de fond
        JPanel p=new JPanel() {
            Image bg=new ImageIcon("src/img1.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg,0,0,getWidth(),getHeight(),this);
            }
        };
        p.setLayout(null);

        JLabel title=new JLabel("Inscription au Club", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS",Font.BOLD,24));
        title.setForeground(Color.BLACK);
        title.setBounds(0,10,450,30);
        p.add(title);
        setContentPane(p); 

        //label
        JLabel l1=new JLabel("Nom:");
        JLabel l2= new JLabel("Prénom:");
        JLabel l3=new JLabel("Email:");
        JLabel l4=new JLabel("Password:");
        JLabel l5 =new JLabel("Club:");
        JLabel l6=new JLabel("Role:");

        l1.setBounds(50,50,100,25);
        l2.setBounds(50,90,100,25);
        l3.setBounds(50,130,100,25);
        l4.setBounds(50,170,100,25);
        l5.setBounds(50,210,100,25);
        l6.setBounds(50,250, 100,25);

        //texte
        txtNom=new JTextField();
        txtPrenom=new JTextField();
        txtEmail =new JTextField();
        txtPassword=new JPasswordField();
        txtRole=new JTextField();

        txtNom.setBounds(170,50,180,25);
        txtPrenom.setBounds(170,90,180,25);
        txtEmail.setBounds(170,130,180,25);
        txtPassword.setBounds(170, 170,180,25);
        txtRole.setBounds(170,250,180,25);

        //comboBox
        cbClub=new JComboBox<>();
        cbClub.setBounds(170, 210, 180, 25);
        loadClubs();

        //button
        btnOk=new JButton("OK");
        btnAnnuler=new JButton("Annuler");

        btnOk.setBounds(90,290, 100,30);
        btnAnnuler.setBounds(220,290,100,30);

        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);

        
        p.add(l1);
        p.add(l2);
        p.add(l3);
        p.add(l4); 
        p.add(l5); 
        p.add(l6);
        p.add(txtNom); 
        p.add(txtPrenom); 
        p.add(txtEmail); 
        p.add(txtPassword);
        p.add(cbClub); 
        p.add(txtRole);
        p.add(btnOk); 
        p.add(btnAnnuler);

        setVisible(true);
    }

    //load clubs depuis BD
    void loadClubs() {
        try {
            Connection cn=ConnexionBD.getConnection();
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery("SELECT nom_club FROM club");

            while (rs.next()) {
                cbClub.addItem(rs.getString("nom_club"));
            }
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement clubs");
        }
    }

    //verif  si email existe
    boolean emailExists(String email) {
        try {
            Connection cn=ConnexionBD.getConnection();
            String sql="SELECT id_user FROM users WHERE email = ?";
            PreparedStatement ps=cn.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            boolean exists=rs.next();
            cn.close();
            return exists;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnAnnuler) {
            dispose();
        }
        if (e.getSource()==btnOk) {
            insertUser();
            dispose();
            new CalendrierActivity();
        }
    }

    void insertUser() {
        if (txtNom.getText().isEmpty()||txtPrenom.getText().isEmpty()||txtEmail.getText().isEmpty()||
            txtPassword.getPassword().length==0||txtRole.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Remplissez tous les champs");
            return;
        }

        if (emailExists(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email déjà utilisé");
            return;
        }

        Connection cn=null;

        try {
            cn=ConnexionBD.getConnection();

            String sql =
              "INSERT INTO users (nom, prenom, email, password, id_club, role)"+
              "VALUES (?, ?, ?, ?, (SELECT id_club FROM club WHERE nom_club=?), ?)";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1,txtNom.getText());
            ps.setString(2,txtPrenom.getText());
            ps.setString(3,txtEmail.getText());
            ps.setString(4,new String(txtPassword.getPassword()));
            ps.setString(5,cbClub.getSelectedItem().toString());
            ps.setString(6,txtRole.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Inscription reussie");
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this,"Erreur BD email déjà existant");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Erreur de base de données:"+ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Erreur:"+ex.getMessage());
        } finally {
            try {
                if (cn!=null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }}
