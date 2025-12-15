import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class Welcome extends JFrame {  
    JRadioButton b1 = new JRadioButton("S'inscrire");
    JRadioButton b2 = new JRadioButton("Se connecter");

    

    public Welcome() {
        super("Welcome");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel() {
            Image bg = new ImageIcon("src//img1.jpg").getImage(); // path to your image

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // stretch to fill panel
            }
        };

        JLabel title = new JLabel("Bienvenue");
        title.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 36)); // Font size 30
        title.setForeground(Color.BLACK); // Color of the text
        title.setBounds(200, 20, 300, 50); // x, y, width, height
        p.add(title);

        p.setLayout(null);

        



        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);

        JButton b3 = new JButton("OK");
        JButton b4 = new JButton("Annuler");

      
        b1.setBounds(100, 100, 120, 30);
        b2.setBounds(300, 100, 120, 30);
        b3.setBounds(100, 200, 120, 30);   
        b4.setBounds(300, 200, 120, 30);   

        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);

        setContentPane(p);
    }

    public static void main(String[] args) {
        Welcome a = new Welcome();
        a.setVisible(true); 
    }
}

