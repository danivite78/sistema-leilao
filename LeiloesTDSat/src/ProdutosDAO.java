import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    // MÉTODO ATUALIZADO: venderProduto()
    public boolean venderProduto(int id) {
        boolean vendidoComSucesso = false;
        
        try {
            conn = new conectaDAO().connectDB();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "❌ Erro: Não foi possível conectar ao banco de dados.");
                return false;
            }
            
            // PRIMEIRO: Verifica se o produto existe e está disponível
            String sqlVerificar = "SELECT id, nome, status FROM produtos WHERE id = ?";
            prep = conn.prepareStatement(sqlVerificar);
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                String nomeProduto = resultset.getString("nome");
                String statusAtual = resultset.getString("status");
                
                // Verifica se já está vendido
                if ("Vendido".equalsIgnoreCase(statusAtual)) {
                    JOptionPane.showMessageDialog(null, 
                        "❌ Produto '" + nomeProduto + "' já foi vendido anteriormente!",
                        "Produto Indisponível",
                        JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                
                // Confirmação da venda
                int confirmacao = JOptionPane.showConfirmDialog(null,
                    "Deseja confirmar a venda do produto?\n\n" +
                    "ID: " + id + "\n" +
                    "Nome: " + nomeProduto + "\n" +
                    "Status atual: " + statusAtual,
                    "Confirmar Venda",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    // ATUALIZA o status para "Vendido"
                    String sqlAtualizar = "UPDATE produtos SET status = ? WHERE id = ?";
                    PreparedStatement prepUpdate = conn.prepareStatement(sqlAtualizar);
                    prepUpdate.setString(1, "Vendido");
                    prepUpdate.setInt(2, id);
                    
                    int linhasAfetadas = prepUpdate.executeUpdate();
                    
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null,
                            "✅ Venda realizada com sucesso!\n\n" +
                            "Produto: " + nomeProduto + "\n" +
                            "ID: " + id + "\n" +
                            "Status atualizado para: VENDIDO",
                            "Venda Concluída",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        vendidoComSucesso = true;
                        
                        // Registra a venda (opcional - para histórico)
                        registrarHistoricoVenda(id, nomeProduto);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "❌ Erro ao atualizar o status do produto.",
                            "Erro na Venda",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                    prepUpdate.close();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                    "❌ Produto com ID " + id + " não encontrado!",
                    "Produto Não Encontrado",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // Fecha recursos
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "❌ Erro ao processar venda: " + e.getMessage(),
                "Erro no Sistema",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return vendidoComSucesso;
    }
    
    // MÉTODO NOVO: registrarHistoricoVenda (opcional para histórico)
    private void registrarHistoricoVenda(int idProduto, String nomeProduto) {
        try {
            // Cria tabela de histórico se não existir
            Connection connHist = new conectaDAO().connectDB();
            String sqlCriarTabela = "CREATE TABLE IF NOT EXISTS historico_vendas (" +
                                   "id_venda INT AUTO_INCREMENT PRIMARY KEY," +
                                   "id_produto INT NOT NULL," +
                                   "nome_produto VARCHAR(100) NOT NULL," +
                                   "data_venda DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                   "FOREIGN KEY (id_produto) REFERENCES produtos(id)" +
                                   ")";
            
            PreparedStatement prepCreate = connHist.prepareStatement(sqlCriarTabela);
            prepCreate.executeUpdate();
            prepCreate.close();
            
            // Insere no histórico
            String sqlInsert = "INSERT INTO historico_vendas (id_produto, nome_produto) VALUES (?, ?)";
            PreparedStatement prepInsert = connHist.prepareStatement(sqlInsert);
            prepInsert.setInt(1, idProduto);
            prepInsert.setString(2, nomeProduto);
            prepInsert.executeUpdate();
            
            prepInsert.close();
            connHist.close();
            
            System.out.println("✅ Histórico de venda registrado para produto ID: " + idProduto);
            
        } catch (Exception e) {
            System.out.println("⚠️ Aviso: Não foi possível registrar histórico: " + e.getMessage());
            // Não interrompe o fluxo principal se o histórico falhar
        }
    }
    
    // MÉTODO AUXILIAR: buscarProdutoPorId
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
    
    // MÉTODO: listarProdutosVendidos (nova funcionalidade)
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            
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
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        }
        
        return listaVendidos;
    }
    
    // MÉTODO: listarProdutosDisponiveis
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
    
    // MÉTODO: cadastrarProduto (mantido da versão anterior)
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
                JOptionPane.showMessageDialog(null, "✅ Produto cadastrado com sucesso!");
            }
            
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    // MÉTODO: listarProdutos (mantido da versão anterior)
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
}