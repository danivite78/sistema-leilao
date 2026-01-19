import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    // ============================================
    // MÉTODO 1: venderProduto() - MELHORADO
    // ============================================
    public boolean venderProduto(int id) {
        boolean vendidoComSucesso = false;
        
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, 
                    "Erro: Não foi possível conectar ao banco de dados.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // 2. Verificar se o produto existe
            String sqlVerificar = "SELECT id, nome, valor, status FROM produtos WHERE id = ?";
            prep = conn.prepareStatement(sqlVerificar);
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                String nomeProduto = resultset.getString("nome");
                double valorProduto = resultset.getDouble("valor");
                String statusAtual = resultset.getString("status");
                
                // 3. Verificar se já está vendido
                if ("Vendido".equalsIgnoreCase(statusAtual)) {
                    JOptionPane.showMessageDialog(null, 
                        "Produto '" + nomeProduto + "' já foi vendido!",
                        "Produto Indisponível",
                        JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                
                // 4. Pedir confirmação
                int confirmacao = JOptionPane.showConfirmDialog(null,
                    "CONFIRMAR VENDA\n\n" +
                    "ID: " + id + "\n" +
                    "Nome: " + nomeProduto + "\n" +
                    "Valor: R$ " + valorProduto + "\n" +
                    "Status atual: " + statusAtual + "\n\n" +
                    "Deseja realmente vender este produto?",
                    "Confirmar Venda",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    // 5. Atualizar status para "Vendido"
                    String sqlAtualizar = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
                    PreparedStatement prepUpdate = conn.prepareStatement(sqlAtualizar);
                    prepUpdate.setInt(1, id);
                    
                    int linhasAfetadas = prepUpdate.executeUpdate();
                    
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null,
                            "VENDA REALIZADA COM SUCESSO!\n\n" +
                            "Produto: " + nomeProduto + "\n" +
                            "Valor: R$ " + valorProduto + "\n" +
                            "Status: VENDIDO",
                            "Venda Concluída",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        vendidoComSucesso = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Erro ao atualizar o produto.",
                            "Erro na Venda",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                    prepUpdate.close();
                }
            } else {
                // 6. Produto não encontrado
                JOptionPane.showMessageDialog(null,
                    "Produto com ID " + id + " não encontrado!",
                    "Produto Não Encontrado",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // 7. Fechar tudo
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao processar venda: " + e.getMessage(),
                "Erro no Sistema",
                JOptionPane.ERROR_MESSAGE);
        }
        
        return vendidoComSucesso;
    }
    
    // ============================================
    // MÉTODO 2: listarProdutosVendidos() - NOVO
    // ============================================
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
        
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            if (conn == null) {
                return listaVendidos; // Retorna lista vazia se não conectar
            }
            
            // 2. Buscar produtos com status "Vendido"
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido' ORDER BY id DESC";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // 3. Adicionar cada produto na lista
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listaVendidos.add(produto);
            }
            
            // 4. Fechar conexões
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
        }
        
        return listaVendidos;
    }
    
    // ============================================
    // MÉTODO 3: buscarProdutoPorId() - SIMPLIFICADO
    // ============================================
    public ProdutosDTO buscarProdutoPorId(int id) {
        ProdutosDTO produto = null;
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT * FROM produtos WHERE id = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
        
        return produto;
    }
    
    // ============================================
    // MÉTODO 4: listarProdutosDisponiveis() - MANTIDO
    // ============================================
    public ArrayList<ProdutosDTO> listarProdutosDisponiveis() {
        ArrayList<ProdutosDTO> listaDisponiveis = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT * FROM produtos WHERE status != 'Vendido' OR status IS NULL ORDER BY id";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listaDisponiveis.add(produto);
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos disponíveis: " + e.getMessage());
        }
        
        return listaDisponiveis;
    }
    
    // ============================================
    // MÉTODO 5: cadastrarProduto() - MANTIDO
    // ============================================
    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, "Disponível");
            
            int rowsAffected = prep.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto!");
            }
            
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    // ============================================
    // MÉTODO 6: listarProdutos() - MANTIDO
    // ============================================
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
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
        
        return listagem;
    }
    
    // ============================================
    // MÉTODO 7: getTotalProdutosVendidos() - NOVO (OPCIONAL)
    // ============================================
    public int getTotalProdutosVendidos() {
        int total = 0;
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT COUNT(*) as total FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                total = resultset.getInt("total");
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao contar produtos vendidos: " + e.getMessage());
        }
        
        return total;
    }
    
    // ============================================
    // MÉTODO 8: getValorTotalVendas() - NOVO (OPCIONAL)
    // ============================================
    public double getValorTotalVendas() {
        double total = 0.0;
        
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT SUM(valor) as total FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                total = resultset.getDouble("total");
            }
            
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao calcular valor total: " + e.getMessage());
        }
        
        return total;
    }
}