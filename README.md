Relatório de Versionamento - Sistema de Leilões

Repositório GitHub
- URL: https://github.com/danivite78/sistema-leilao.git
- Branch Principal: main
- Total de Commits: 6

Histórico de Commits

Commit 1: Configuração da tela principal
Descrição: Define cadastroVIEW como tela principal do sistema
Arquivos modificados:
- cadastroVIEW.java

Commit 2: Implementação do botão Salvar
Descrição: Implementa funcionalidade completa de salvar produto
Arquivos modificados:
- cadastroVIEW.java (método btnCadastrarActionPerformed)
- ProdutosDAO.java (método cadastrarProduto)
- ProdutosDTO.java (tipo double para valor)
- conectaDAO.java (conexão MySQL)

Commit 3: Sistema de mensagens de feedback
Descrição: Adiciona mensagens informativas ao usuário
Arquivos modificados:
- cadastroVIEW.java (validações e JOptionPane)
- ProdutosDAO.java (mensagens de sucesso/erro)

Commit 4: Funcionalidade de listagem de produtos
Descrição: Implementa tela de listagem com tabela
Arquivos modificados:
- listagemVIEW.java (tabela e métodos)
- ProdutosDAO.java (método listarProdutos)

Commit 5: Correção de bug na conexão MySQL
Descrição: Corrige erro 2003 de conexão com MySQL
Arquivos modificados:
- conectaDAO.java (configuração de conexão)
- (Scripts de correção do MySQL)

Commit 6: Melhorias na interface e validações
Descrição: Melhora UX e adiciona validações
Arquivos modificados:
- cadastroVIEW.java (validações extras)
- listagemVIEW.java (formatação de valores)
