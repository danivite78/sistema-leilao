import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    // ==================== MÉTODO 1: venderProduto ====================
    public boolean venderProduto(int id) {
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            // 2. Verificar se o produto existe
            String sqlVerificar = "SELECT nome, valor, status FROM produtos WHERE id = ?";
            prep = conn.prepareStatement(sqlVerificar);
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                String nomeProduto = resultset.getString("nome");
                double valorProduto = resultset.getDouble("valor");
                String statusAtual = resultset.getString("status");
                
                // 3. Verificar se já está vendido
                if ("Vendido".equals(statusAtual)) {
                    JOptionPane.showMessageDialog(null,
                        "Este produto já foi vendido!",
                        "Produto Indisponível",
                        JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                
                // 4. Pedir confirmação
                int resposta = JOptionPane.showConfirmDialog(null,
                    "Vender produto?\n\n" +
                    "ID: " + id + "\n" +
                    "Nome: " + nomeProduto + "\n" +
                    "Valor: R$ " + valorProduto,
                    "Confirmar Venda",
                    JOptionPane.YES_NO_OPTION);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    // 5. Atualizar status para "Vendido"
                    String sqlAtualizar = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
                    PreparedStatement prepUpdate = conn.prepareStatement(sqlAtualizar);
                    prepUpdate.setInt(1, id);
                    int linhasAfetadas = prepUpdate.executeUpdate();
                    
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null,
                            "Produto vendido com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                        prepUpdate.close();
                        return true;
                    }
                    prepUpdate.close();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                    "Produto não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // 6. Fechar conexões
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    // ==================== MÉTODO 2: listarProdutosVendidos ====================
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            
            // Buscar apenas produtos com status "Vendido"
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido' ORDER BY id DESC";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listaVendidos.add(produto);
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
        }
        
        return listaVendidos;
    }
    
    // ==================== MÉTODO 3: cadastrarProduto ====================
    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, "Disponível");
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    // ==================== MÉTODO 4: listarProdutos ====================
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT * FROM produtos ORDER BY id";
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
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + e.getMessage());
        }
        
        return listagem;
    }
}