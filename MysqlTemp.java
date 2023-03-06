// sri
import java.sql.*;

public class MysqlTemp {
    public static void main(String[] args) {
        System.out.println("Connecting to Database");
        Connection con;
        Statement stmt;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/contents";
        String uname = "root";
        String pw = "root";
        try {
            con = DriverManager.getConnection(url, uname, pw);
            stmt = con.createStatement();
            String query = "SELECT * FROM table_name";
            rs = stmt.executeQuery(query);
            while(rs.next())
                System.out.println(rs.getString(0));
            con.close();
        } catch(SQLException error) {
            System.out.println(error);
        }
    }
}