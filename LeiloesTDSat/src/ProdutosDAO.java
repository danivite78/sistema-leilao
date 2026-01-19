import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    // Método para SALVAR/CADASTRAR produto
    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());  // Use setDouble
            prep.setString(3, produto.getStatus());
            
            int rowsAffected = prep.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "✅ Produto cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Erro ao cadastrar produto!");
            }
            
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao cadastrar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para LISTAR produtos
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
        
        return listagem;
    }
    
    // Método para VENDER produto
    public void venderProduto(int id) {
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            
            int rowsAffected = prep.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "✅ Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Produto não encontrado!");
            }
            
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        }
    }
}