import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Welcome extends JFrame implements ActionListener {

    JRadioButton b1=new JRadioButton("S'inscrire");
    JRadioButton b2=new JRadioButton("Se connecter");
    JButton b3=new JButton("OK");
    JButton b4=new JButton("Annuler");

    public Welcome() {
        super("Welcome");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrer la fenêtre


        JPanel p=new JPanel() {
            Image bg=new ImageIcon("src/img1.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg,0,0, getWidth(),getHeight(),this);
            }
        };

        p.setLayout(null);

        JLabel title = new JLabel("Bienvenue");
        title.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 36));
        title.setForeground(Color.BLACK);
        title.setBounds(200, 20, 300, 50);
        p.add(title);

        ButtonGroup group=new ButtonGroup();
        group.add(b1);
        group.add(b2);

        b1.setBounds(100,100,120,30);
        b2.setBounds(300,100,120,30);
        b3.setBounds(100,200,120,30);
        b4.setBounds(300,200,120,30);

        //listener
        b3.addActionListener(this);
        b4.addActionListener(this);

        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);

        setContentPane(p);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b4) {
            dispose(); 
        } else if (e.getSource()==b3) {
            if (b1.isSelected()) {
                new Inscription();
                dispose(); 

                
            } else if(b2.isSelected()) {
                new login();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Choisissez une option");
            }
        }
    }

    public static void main(String[] args) {
        new Welcome();
    }
}
