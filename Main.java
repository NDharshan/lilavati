
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        System.out.println("Srimathe Ramanujaya Nama:");
        sandbox();
    }

    public static void sandbox() {
        JFrame frame = new JFrame("Hayavadana");
        frame.getContentPane().setBackground( Color.decode("#FFF9EE") );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1233, 733); 
        frame.setLayout(null);  

        JLabel title;  
        title = new JLabel("Lilavati ");  
        title.setBounds(112,66, 195,50);  
        title.setForeground(Color.decode("#075466"));
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD , 40));
        frame.add(title);
        
        JLabel hayavadana ;  
        title = new JLabel("Hayavadana");  
        title.setBounds(986,86, 175,50);  
        title.setForeground(Color.decode("#D90166"));
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD , 26));
        frame.add(title);
        
        // tabbed pane
        JTabbedPane tp=new JTabbedPane();  
        tp.setBounds(52,120,1128,515);
        tp.setFont(new Font("Segoe", Font.CENTER_BASELINE, 20));

        // individual panes
        // About
        JPanel about =new JPanel();   
        String at = "Lilavati (Lilavati) was composed by renowned astronomer and mathematician Bhaskaracarya (Bhaskara II) (b. 1114 AD) in 1150 This is the first volume of his main work Siddhanta Shiromani";
        JTextArea aboutText = new JTextArea(at);  
        aboutText.setBounds(86, 209, 741, 449);
        aboutText.setLineWrap(true);
        aboutText.setFont(new Font("Segoe UI", Font.BOLD , 18));
        about.add(aboutText);

        // view
        JPanel view =new JPanel();  
        // TODO: Add table

        // info
        JPanel info=new JPanel();  
        // TODO: Add Info
        String it = "Bhāskara II (c. 1114–1185), also known as Bhāskarāchārya ('Bhāskara, the teacher'), and as Bhāskara II to avoid confusion with Bhāskara I, was an Indian mathematician and astronomer. From verses, in his main work, Siddhānta Shiromani (सिद्धांतशिरोमणी), it can be inferred that he was born in 1114 in Vijjadavida (Vijjalavida) and living in the Sahyadri mountain ranges of Western Ghats, believed to be the town of Patan in Chalisgaon, located in present-day Khandesh region of Maharashtra by scholars.[6] He is the only ancient mathematician who has been immortalized on a monument. In a temple in Maharashtra, an inscription supposedly created by his grandson Changadeva, lists Bhaskaracharya's ancestral lineage for several generations before him as well as two generations after him.[7][8] Colebrooke who was the first European to translate (1817) Bhaskaracharya II's mathematical classics refers to the family as Maharashtrian Brahmins residing on the banks of the Godavari.";
        JTextArea infoText = new JTextArea(it);  
        infoText.setBounds(86, 209, 741, 449);
        infoText.setLineWrap(true);
        infoText.setFont(new Font("Segoe UI", Font.BOLD , 18));
        info.add(infoText);


        tp.add("About", about);  
        tp.add("View", view);  
        tp.add("Background Info", info);    


        frame.add(tp);
        frame.setVisible(true);  
    }

}
