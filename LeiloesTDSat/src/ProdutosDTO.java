public class ProdutosDTO {
    private int id;
    private String nome;
    private double valor;  // Use double para valores decimais
    private String status;
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}