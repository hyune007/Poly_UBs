import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Poly_UBs";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
