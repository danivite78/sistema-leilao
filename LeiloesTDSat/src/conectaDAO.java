import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conectaDAO {
    
    public Connection connectDB() {
        Connection conn = null;
        
        try {
            // Carrega o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // String de conexão
            String url = "jdbc:mysql://localhost:3306/uc11?" +
                        "useSSL=false&" +
                        "serverTimezone=UTC&" +
                        "allowPublicKeyRetrieval=true&" +
                        "autoReconnect=true";
            
            // Conecta com senha vazia
            conn = DriverManager.getConnection(url, "root", "");
            
            System.out.println("✅ Conexão MySQL estabelecida!");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "❌ Driver MySQL não encontrado!\n" +
                "Adicione mysql-connector-j-9.0.0.jar ao projeto.\n" +
                "Erro: " + e.getMessage(),
                "Erro de Driver",
                JOptionPane.ERROR_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "❌ Erro de conexão com o banco de dados!\n\n" +
                "Detalhes: " + e.getMessage() + "\n\n" +
                "Solução:\n" +
                "1. Certifique-se que o MySQL está rodando\n" +
                "2. Execute: mysqld --console\n" +
                "3. Banco 'uc11' deve existir",
                "Erro de Conexão",
                JOptionPane.ERROR_MESSAGE);
        }
        
        return conn;
    }
}