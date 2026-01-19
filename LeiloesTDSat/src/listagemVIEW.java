import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class listagemVIEW extends javax.swing.JFrame {

    public listagemVIEW() {
        initComponents();
        setLocationRelativeTo(null); // Centraliza a janela
        setTitle("Sistema de Leilões - Listagem");
        listarProdutos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaProdutos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        id_produto_venda = new javax.swing.JTextPane();
        btnVender = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnProdutosVendidos = new javax.swing.JButton(); // NOME ALTERADO
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Valor", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(listaProdutos);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel1.setText("Lista de Produtos");

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel2.setText("Vender Produto (ID)");

        jScrollPane2.setViewportView(id_produto_venda);

        btnVender.setText("Vender");
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });

        // BOTÃO ALTERADO: Agora mostra produtos vendidos
        btnProdutosVendidos.setText("Ver Produtos Vendidos");
        btnProdutosVendidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutosVendidosActionPerformed(evt);
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
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVender))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnVoltar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnProdutosVendidos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)) // TAMANHO AJUSTADO
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(193, 193, 193))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVender))
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProdutosVendidos)
                    .addComponent(btnVoltar))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ============================================
    // MÉTODO: btnVenderActionPerformed
    // ============================================
    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {
        String id = id_produto_venda.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o ID do produto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int produtoId = Integer.parseInt(id);
            
            ProdutosDAO produtosdao = new ProdutosDAO();
            boolean vendaRealizada = produtosdao.venderProduto(produtoId);
            
            if (vendaRealizada) {
                // Atualiza a tabela
                listarProdutos();
                // Limpa o campo
                id_produto_venda.setText("");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ============================================
    // MÉTODO: btnProdutosVendidosActionPerformed - NOVO
    // ============================================
    private void btnProdutosVendidosActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Cria objeto DAO
            ProdutosDAO dao = new ProdutosDAO();
            
            // Usa o novo método para buscar produtos vendidos
            ArrayList<ProdutosDTO> produtosVendidos = dao.listarProdutosVendidos();
            
            // Formata números
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            if (produtosVendidos.isEmpty()) {
                // Se não há produtos vendidos
                JOptionPane.showMessageDialog(null,
                    "Nenhum produto vendido ainda!\n\n" +
                    "Venda alguns produtos primeiro.",
                    "Produtos Vendidos",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Cria mensagem com todos os produtos vendidos
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("=== PRODUTOS VENDIDOS ===\n\n");
                mensagem.append("Total: ").append(produtosVendidos.size()).append(" produtos\n\n");
                
                double valorTotal = 0;
                
                // Adiciona cada produto na mensagem
                for (ProdutosDTO produto : produtosVendidos) {
                    mensagem.append("ID: ").append(produto.getId()).append("\n");
                    mensagem.append("Nome: ").append(produto.getNome()).append("\n");
                    mensagem.append("Valor: R$ ").append(df.format(produto.getValor())).append("\n");
                    mensagem.append("Status: ").append(produto.getStatus()).append("\n");
                    mensagem.append("--------------------\n");
                    
                    valorTotal += produto.getValor();
                }
                
                mensagem.append("\nVALOR TOTAL DAS VENDAS: R$ ").append(df.format(valorTotal));
                
                // Mostra em uma caixa de diálogo
                JOptionPane.showMessageDialog(null,
                    mensagem.toString(),
                    "Produtos Vendidos",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Opcional: mostra também no console
                System.out.println("Produtos vendidos encontrados: " + produtosVendidos.size());
                System.out.println("Valor total: R$ " + df.format(valorTotal));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao buscar produtos vendidos:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ============================================
    // MÉTODO: btnVoltarActionPerformed
    // ============================================
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        // Volta para a tela de cadastro
        new cadastroVIEW().setVisible(true);
        this.dispose(); // Fecha a tela atual
    }

    // ============================================
    // MÉTODO MAIN
    // ============================================
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(listagemVIEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(listagemVIEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(listagemVIEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(listagemVIEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new listagemVIEW().setVisible(true);
            }
        });
    }

    // ============================================
    // VARIÁVEIS - ATUALIZADO
    // ============================================
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProdutosVendidos; // NOME ALTERADO
    private javax.swing.JButton btnVender;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JTextPane id_produto_venda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable listaProdutos;
    // End of variables declaration//GEN-END:variables

    // ============================================
    // MÉTODO: listarProdutos
    // ============================================
    private void listarProdutos() {
        try {
            ProdutosDAO produtosdao = new ProdutosDAO();
            
            DefaultTableModel model = (DefaultTableModel) listaProdutos.getModel();
            model.setNumRows(0); // Limpa a tabela
            
            ArrayList<ProdutosDTO> listagem = produtosdao.listarProdutos();
            
            if (listagem.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (int i = 0; i < listagem.size(); i++) {
                    ProdutosDTO produto = listagem.get(i);
                    
                    // Formata o valor como moeda (R$)
                    String valorFormatado = String.format("R$ %.2f", produto.getValor());
                    
                    // Adiciona linha na tabela
                    model.addRow(new Object[]{
                        produto.getId(),
                        produto.getNome(),
                        valorFormatado,
                        produto.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao listar produtos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============================================
    // MÉTODO: atualizarTabela
    // ============================================
    public void atualizarTabela() {
        listarProdutos();
    }
    
    // ============================================
    // MÉTODO ADICIONAL: mostrarEstatisticasVendas
    // ============================================
    public void mostrarEstatisticasVendas() {
        try {
            ProdutosDAO dao = new ProdutosDAO();
            
            // Usa os métodos novos opcionais
            int totalVendidos = dao.getTotalProdutosVendidos();
            double valorTotal = dao.getValorTotalVendas();
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            JOptionPane.showMessageDialog(null,
                "=== ESTATÍSTICAS DE VENDAS ===\n\n" +
                "Total de produtos vendidos: " + totalVendidos + "\n" +
                "Valor total das vendas: R$ " + df.format(valorTotal) + "\n\n" +
                "Para ver detalhes dos produtos vendidos,\n" +
                "clique em 'Ver Produtos Vendidos'.",
                "Estatísticas",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            System.out.println("Erro ao mostrar estatísticas: " + e.getMessage());
        }
    }
}