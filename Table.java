import javax.swing.*;    
import javax.swing.event.*;  
public class Table {    
	JFrame frame;
    Table(JFrame f) {
        frame = f;
        addTable(f);
        f.setVisible(true);  
    }

    public static JFrame myfun(String Data) {
        JFrame q= new JFrame("Label Example");  
        JLabel l1;
        l1=new JLabel(Data);  
        l1.setBounds(50,50, 100,30);  
        
        q.add(l1); 
        q.setSize(1233,737);
        return q;
    }

    public static void addTable(JFrame f) {
    // JFrame f = new JFrame("Table Example");  
    String data[][]={ 
                {"026","Kamala Srinvas","670000"},    
                {"032","Padma","780000"},    
                {"048","Keshav","700000"}
            };    
    String column[]={"ID","NAME","STATUS"};         
    final JTable jt=new JTable(data,column);   
    // JScrollPane scrollPane = new JScrollPane(jt);
    jt.setCellSelectionEnabled(true);  
    ListSelectionModel select= jt.getSelectionModel();  
    select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
    
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


        JFrame q= myfun(Data);  
        q.setLayout(null);  
        q.setVisible(true);  
        
        
        }       
    });  
    JScrollPane sp=new JScrollPane(jt);    
    f.add(sp);  
    // f.setSize(500, 528);  
    f.setVisible(true);  
    }
}  