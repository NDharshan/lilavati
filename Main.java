import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;


public class Main extends DefaultTableCellRenderer{
    static JFrame frame = new JFrame("Hayavadana");    
    // static JTextArea dataItemText;
    // static JPanel myDisplay = new JPanel();

    public static void addTable(JPanel view) {
        String data[][]={ 
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},   
            {"026","Kamala Srinvas","670000"},    
            {"032","Padma","780000"},    
            {"048","Keshav","700000"}
        };    
        String column[]={"ID","NAME","STATUS"};         
        final JTable jt=new JTable(data,column);   
        jt.setFont(new Font("Segoe UI", Font.BOLD , 18));
        // JScrollPane scrollPane = new JScrollPane(jt);
        jt.setCellSelectionEnabled(true);  
        ListSelectionModel select= jt.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  

        jt.setRowHeight(24);
        
        select.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
            String Data = null;  
            int[] row = jt.getSelectedRows();  
            int[] columns = jt.getSelectedColumns();  
        //                for (int i = 0; i < row.length; i++) {  
        //                  for (int j = 0; j < columns.length; j++) {  
        //                    Data = (String) jt.getValueAt(row[i], columns[j]);  
        //                  } }  

            Data = (String) jt.getValueAt(row[row.length - 1], columns[columns.length - 1]);
            System.out.println("Table element selected is: " + Data);    
            myfun(Data);  
        }

        });  
        JScrollPane sp=new JScrollPane(jt); 
        // TODO: Add table
        view.add(sp);
        view.setVisible(true);
    }

    private static Object String(byte[] bytes) {
        return null;
    }

    public static void myfun(String Data) {
        JTextArea dataItemText = new JTextArea(Data);  
        dataItemText.setBackground(Color.decode("#B7D6DD"));
        // myDisplay.setBackground(Color.decode("#B7D6DD"));
        dataItemText.setBounds(767, 150, 349, 449);
        dataItemText.setFont(new Font("Segoe UI", Font.BOLD , 18));
        // myDisplay.add(dataItemText);
        frame.add(dataItemText);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Srimathe Ramanujaya Nama:\nश्रीमते रामानुजाय नमः");
        byte[] arr = "श्रीमते रामानुजाय नमः".getBytes("UTF-8");

            System.out.print(String(arr));

        sandbox();
    }

    public static void sandbox() {

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
        
        title = new JLabel("Hayavadana");  
        title.setBounds(986,86, 175,50);  
        title.setForeground(Color.decode("#D90166"));
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD , 26));
        frame.add(title);
        
        // tabbed pane
        JTabbedPane tp=new JTabbedPane();  
        tp.setBounds(52,120,592,515);
        tp.setFont(new Font("Segoe", Font.CENTER_BASELINE, 20));
        tp.setBackground(Color.decode("#FFF9EE"));
        // individual panes


        // About
        JPanel about =new JPanel();  
        about.setBackground(Color.decode("#FFF9EE")); 
        String at = "Lilavati (Lilavati) was composed by renowned astronomer and mathematician Bhaskaracarya (Bhaskara II) (b. 1114 AD) in 1150 This is the first volume of his main work Siddhanta Shiromani";
        JTextArea aboutText = new JTextArea(at);  
        aboutText.setBackground(Color.decode("#FFF9EE"));
        aboutText.setBounds(126, 265, 468, 449);
        aboutText.setLineWrap(true);
        aboutText.setFont(new Font("Segoe UI", Font.BOLD , 18));
        about.add(aboutText);




        // view
        JPanel view =new JPanel();  
        addTable(view);

        // info
        JPanel info=new JPanel();  
        info.setBackground(Color.decode("#FFF9EE"));
        // TODO: Add Info
        String it = "Bhāskara II (c. 1114–1185), also known as Bhāskarāchārya ('Bhāskara, the teacher'), and as Bhāskara II to avoid confusion with Bhāskara I, was an Indian mathematician and astronomer. From verses, in his main work, Siddhānta Shiromani (सिद्धांतशिरोमणी), it can be inferred that he was born in 1114 in Vijjadavida (Vijjalavida) and living in the Sahyadri mountain ranges of Western Ghats, believed to be the town of Patan in Chalisgaon, located in present-day Khandesh region of Maharashtra by scholars.[6] He is the only ancient mathematician who has been immortalized on a monument. In a temple in Maharashtra, an inscription supposedly created by his grandson Changadeva, lists Bhaskaracharya's ancestral lineage for several generations before him as well as two generations after him.[7][8] Colebrooke who was the first European to translate (1817) Bhaskaracharya II's mathematical classics refers to the family as Maharashtrian Brahmins residing on the banks of the Godavari.";
        JTextArea infoText = new JTextArea(it);  
        infoText.setBackground(Color.decode("#FFF9EE"));
        infoText.setBounds(126, 265, 468, 449);
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