import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conectaDAO {
    
    public Connection connectDB() {
        Connection conn = null;
        
        try {
            // Carrega o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // String de conexão - SENHA VAZIA
            String url = "jdbc:mysql://localhost:3306/uc11?" +
                        "useSSL=false&" +
                        "serverTimezone=UTC&" +
                        "allowPublicKeyRetrieval=true";
            
            // Conecta com senha vazia
            conn = DriverManager.getConnection(url, "root", "");
            
            System.out.println("✅ Conexão MySQL estabelecida!");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "❌ Driver MySQL não encontrado!\n" +
                "Adicione o JAR do MySQL Connector ao projeto.\n" +
                "Erro: " + e.getMessage());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "❌ Erro de conexão: " + e.getMessage() + "\n\n" +
                "Certifique-se que:\n" +
                "1. MySQL está rodando (execute: mysqld --console)\n" +
                "2. Banco 'uc11' existe\n" +
                "3. Usuário: 'root', Senha: [vazia]\n\n" +
                "Para criar o banco, execute no MySQL:\n" +
                "CREATE DATABASE uc11;\n" +
                "USE uc11;\n" +
                "CREATE TABLE produtos...");
        }
        
        return conn;
    }
}