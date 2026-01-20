import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class VendasVIEW extends javax.swing.JFrame {

    public VendasVIEW() {
        initComponents();
        setLocationRelativeTo(null); // Centraliza
        setTitle("Produtos Vendidos");
        carregarProdutosVendidos(); // Carrega produtos com status "Vendido"
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaVendidos = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblTotalVendidos = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 18));
        jLabel1.setText("Produtos Vendidos");

        // Configura tabela para produtos vendidos
        tabelaVendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Valor", "Status"
            }
        ));
        jScrollPane1.setViewportView(tabelaVendidos);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 14));
        jLabel2.setText("Resumo:");

        lblTotalVendidos.setText("Total de produtos vendidos: 0");

        lblValorTotal.setText("Valor total: R$ 0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalVendidos)
                    .addComponent(lblValorTotal))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(lblTotalVendidos)
                .addGap(5, 5, 5)
                .addComponent(lblValorTotal)
                .addGap(30, 30, 30)
                .addComponent(btnVoltar)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    // ========== MÉTODO PARA CARREGAR PRODUTOS VENDIDOS ==========
    private void carregarProdutosVendidos() {
        try {
            ProdutosDAO dao = new ProdutosDAO();
            
            // Usa o método que busca apenas produtos com status "Vendido"
            ArrayList<ProdutosDTO> listaVendidos = dao.listarProdutosVendidos();
            
            DefaultTableModel model = (DefaultTableModel) tabelaVendidos.getModel();
            model.setRowCount(0); // Limpa tabela
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            double valorTotal = 0;
            
            // Adiciona cada produto vendido na tabela
            for (ProdutosDTO produto : listaVendidos) {
                String valorFormatado = "R$ " + df.format(produto.getValor());
                
                model.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    valorFormatado,
                    produto.getStatus()
                });
                
                valorTotal += produto.getValor();
            }
            
            // Atualiza os labels com as informações
            lblTotalVendidos.setText("Total de produtos vendidos: " + listaVendidos.size());
            lblValorTotal.setText("Valor total: R$ " + df.format(valorTotal));
            
            // Mensagem se não houver produtos vendidos
            if (listaVendidos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nenhum produto vendido ainda!",
                    "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar produtos vendidos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== BOTÃO VOLTAR ==========
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Fecha esta janela
    }

    // ========== VARIÁVEIS ==========
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalVendidos;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JTable tabelaVendidos;
}