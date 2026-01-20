import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class listagemVIEW extends javax.swing.JFrame {

    public listagemVIEW() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Listagem de Produtos");
        listarProdutos();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaProdutos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtIdVenda = new javax.swing.JTextField(); // Mudei de JTextPane para JTextField
        btnVender = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnConsultarVendas = new javax.swing.JButton(); // Botão renomeado
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Valor", "Status"
            }
        ));
        jScrollPane1.setViewportView(listaProdutos);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 18));
        jLabel1.setText("Lista de Produtos");

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 14));
        jLabel2.setText("Vender Produto (ID):");

        // Mudei para JTextField que é mais fácil de usar
        txtIdVenda.setColumns(10);
        jScrollPane2.setViewportView(txtIdVenda);

        btnVender.setText("Vender");
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });

        // Botão renomeado para "Consultar Vendas"
        btnConsultarVendas.setText("Consultar Vendas");
        btnConsultarVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarVendasActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar para Cadastro");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVender))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(200, 200, 200))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVender))
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(btnConsultarVendas))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    // ========== BOTÃO VENDER (AGORA FUNCIONANDO) ==========
    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {
        String idTexto = txtIdVenda.getText().trim();
        
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Digite o ID do produto que deseja vender!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idTexto);
            
            // Chama o método venderProduto do ProdutosDAO
            ProdutosDAO dao = new ProdutosDAO();
            boolean vendaRealizada = dao.venderProduto(id);
            
            if (vendaRealizada) {
                // Atualiza a tabela para mostrar o novo status
                listarProdutos();
                // Limpa o campo de ID
                txtIdVenda.setText("");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "ID inválido! Digite apenas números.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== BOTÃO CONSULTAR VENDAS (ABRE TELA VendasVIEW) ==========
    private void btnConsultarVendasActionPerformed(java.awt.event.ActionEvent evt) {
        // Abre a tela de vendas (produtos com status "Vendido")
        VendasVIEW telaVendas = new VendasVIEW();
        telaVendas.setVisible(true);
    }

    // ========== BOTÃO VOLTAR ==========
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        new cadastroVIEW().setVisible(true);
        this.dispose();
    }

    // ========== MÉTODO PARA LISTAR PRODUTOS ==========
    private void listarProdutos() {
        try {
            ProdutosDAO dao = new ProdutosDAO();
            ArrayList<ProdutosDTO> lista = dao.listarProdutos();
            
            DefaultTableModel model = (DefaultTableModel) listaProdutos.getModel();
            model.setRowCount(0);
            
            for (ProdutosDTO produto : lista) {
                String valorFormatado = String.format("R$ %.2f", produto.getValor());
                
                model.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    valorFormatado,
                    produto.getStatus()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao carregar produtos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== MAIN ==========
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Usa look and feel padrão
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new listagemVIEW().setVisible(true);
            }
        });
    }

    // ========== VARIÁVEIS ==========
    private javax.swing.JButton btnConsultarVendas;
    private javax.swing.JButton btnVender;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable listaProdutos;
    private javax.swing.JTextField txtIdVenda;
}