import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionBD {

    // Paramètres modifiables facilement
    private static final String HOST = "localhost"; // ou IP serveur
    private static final int PORT = 3306;           // port MySQL
    private static final String DATABASE = "club"; // nom DB
    private static final String USER = "root";        // user XAMPP default
    private static final String PASSWORD = "";        // password XAMPP default

    public static Connection getConnection() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC Driver
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE +
                         "?useSSL=false&serverTimezone=UTC";
            cn = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Connexion réussie ✔");
        } catch (Exception e) {
            System.out.println("Erreur connexion ❌");
            e.printStackTrace();
        }
        return cn;
    }

    // Test rapide
    public static void main(String[] args) {
        Connection test = getConnection();
        if (test != null) {
            System.out.println("Connexion OK !");
        } else {
            System.out.println("Connexion échouée");
        }
    }
}