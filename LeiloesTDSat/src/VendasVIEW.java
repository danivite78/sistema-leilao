import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class VendasVIEW extends javax.swing.JFrame {

    public VendasVIEW() {
        initComponents();
        carregarProdutosVendidos();
    }
    
    private void initComponents() {
        // Componentes da tela
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaVendidos = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produtos Vendidos");
        
        // Configurar tabela
        tabelaVendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Valor", "Status"
            }
        ));
        jScrollPane1.setViewportView(tabelaVendidos);
        
        // Botão Voltar
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        
        // Título
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18));
        jLabel1.setText("Produtos Vendidos");
        
        // Organizar layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnVoltar)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    private void carregarProdutosVendidos() {
        try {
            // Criar objeto DAO
            ProdutosDAO dao = new ProdutosDAO();
            
            // Buscar produtos vendidos
            ArrayList<ProdutosDTO> lista = dao.listarProdutosVendidos();
            
            // Pegar modelo da tabela
            DefaultTableModel model = (DefaultTableModel) tabelaVendidos.getModel();
            model.setRowCount(0); // Limpar tabela
            
            // Adicionar cada produto na tabela
            for (ProdutosDTO produto : lista) {
                model.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getValor(),
                    produto.getStatus()
                });
            }
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Fechar esta janela
    }
    
    // Variáveis
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaVendidos;
}