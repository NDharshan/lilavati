import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;


public class Algorithms {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1233, 733); 
        frame.setLayout(null);  

        // tabbed pane
        JTabbedPane tp=new JTabbedPane();  
        tp.setBounds(52,120,592,515);
        tp.setFont(new Font("Segoe", Font.CENTER_BASELINE, 20));
        tp.setBackground(Color.decode("#FFF9EE"));

        // squareRoot
        JPanel squareRoot =new JPanel();  
        squareRoot.setBackground(Color.decode("#FFF9EE")); 
        String at = "This is an algorithm to compute the Square Root.";
        JTextArea squareRootText = new JTextArea(at);  
        squareRootText.setBackground(Color.decode("#FFF9EE"));
        squareRootText.setBounds(126, 265, 468, 449);
        squareRootText.setLineWrap(true);
        squareRootText.setFont(new Font("Segoe UI", Font.BOLD , 18));
        squareRoot.add(squareRootText);

        

        tp.add("Square Root", squareRoot);  
        JScrollPane sp = new JScrollPane(tp);
        sp.getVerticalScrollBar();
        
        frame.add(sp);
        frame.setVisible(true);
    }
}
