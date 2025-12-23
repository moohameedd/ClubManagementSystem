import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class choix extends JFrame implements ActionListener {

    JRadioButton rbVoirUser,rbAjoutActivity;
    JButton btnOk,btnAnnuler;
    String userEmail;

    public choix(String email) {
        this.userEmail=email;
        setTitle("Interface Choix");
        setSize(500,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel=new JPanel() {
            Image bg=new ImageIcon("src/img1.jpg").getImage();
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg,0,0,getWidth(),getHeight(),this);
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        JLabel title=new JLabel("Choisissez une option",SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD,28));
        title.setBounds(0,20,500,40);
        panel.add(title);

        rbVoirUser=new JRadioButton("Voir mon profil");
        rbAjoutActivity=new JRadioButton("Ajouter activité");

        rbVoirUser.setBounds(180,110,250,30);
        rbAjoutActivity.setBounds(180,150,250,30);

        rbVoirUser.setOpaque(false);
        rbAjoutActivity.setOpaque(false);

        ButtonGroup g=new ButtonGroup();
        g.add(rbVoirUser);
        g.add(rbAjoutActivity);

        panel.add(rbVoirUser);
        panel.add(rbAjoutActivity);

        btnOk=new JButton("OK");
        btnAnnuler=new JButton("Annuler");

        btnOk.setBounds(120,230,100,30);
        btnAnnuler.setBounds(280,230,100,30);

        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);

        panel.add(btnOk);
        panel.add(btnAnnuler);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnAnnuler) dispose();

        if (e.getSource()==btnOk) {
            if (rbVoirUser.isSelected()) {
                new VoirUser(userEmail);
                dispose();
            } else if (rbAjoutActivity.isSelected()) {
                new AjouterActivity(userEmail);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Choisir une option");
            }
        }}
}
