package pedidos.ac2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaPrincipal {
    private static final Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] opcoesDoMenuPrincipal = new String[] {
                "Cadastros de Clientes",
                "Cadastro de Fornecedores",
                "Cadastro de Produtos",
                "Efetuacao de um pedido",
                "Baixa de pagamento de um pedido",
                "Relatorios",
                "Sair"
        };

        String[] opcoesMenuRelatorio = new String[] {
                "Listagem de todos os Clientes",
                "Listagem de todos os Fornecedores",
                "Listagem de todos os Produtos",
                "Listagem de todos os Pedidos",
                "Listagem de todos os pedidos feitos em um determinado intervalo de datas",
                "Busca de um pedido pelo numero",
                "Listagem de todos os pedidos pagos",
                "Busca de um produto pelo nome",
                "Calculo do total dos pedidos em aberto (nao pagos)",
                "Voltar"
        };

        List<Cliente> clientes = new ArrayList<>();
        List<Fornecedor> fornecedores = new ArrayList<>();
        List<Produto> produtos = new ArrayList<>();
        List<Pedido> pedidos = new ArrayList<>();

        int opcaoSelecionada;
        int opcaoRelatorio;

        do {
            imprimirMenu(opcoesDoMenuPrincipal, "Menu Principal");
            opcaoSelecionada = receberOpcaoInt();

            switch (opcaoSelecionada) {
                case 1:
                    cadastrarListaClientes(clientes);
                    break;
                case 2:
                    cadastrarListaFornecedores(fornecedores);
                    break;
                case 3:
                    try {
                        cadastrarListaProdutos(produtos, fornecedores);
                    } catch (Exception error) {
                        System.out.println("\n" + error.getMessage());
                    }
                    break;
                case 4:
                    try {
                        Pedido novoPedido = efetuarPedidoComIdentificador(pedidos.size() + 1, clientes, produtos);
                        pedidos.add(novoPedido);
                    } catch (Exception error) {
                        System.out.println("\n" + error.getMessage());
                    }
                    break;
                case 5:
                    // Baixa de pagamento de um pedido
                    break;
                case 6:
                    // Relatorios
                    do {
                        imprimirMenu(opcoesMenuRelatorio, "Relatorios");
                        opcaoRelatorio = receberOpcaoInt();

                        switch (opcaoRelatorio) {
                            case 1:
                                imprimirLinhaComTitulo("Lista de clientes");

                                if (clientes.isEmpty())
                                    System.out.println("\nNenhum cliente cadastrado");
                                else
                                    clientes.forEach(Cliente::imprimir);
                                break;
                            case 2:
                                imprimirLinhaComTitulo("Lista de Fornecedores");

                                if (fornecedores.isEmpty())
                                    System.out.println("\nNenhum fornecedor cadastrado");
                                else
                                    fornecedores.forEach(Fornecedor::imprimir);
                                break;
                            case 3:
                                imprimirLinhaComTitulo("Lista de Produtos");

                                if (produtos.isEmpty())
                                    System.out.println("\nNenhum produto cadastrado");
                                else
                                    produtos.forEach(Produto::imprimir);
                                break;
                            case 4:
                                imprimirLinhaComTitulo("Lista de Pedidos");

                                if (pedidos.isEmpty())
                                    System.out.println("\nNenhum pedido cadastrado");
                                else
                                    pedidos.forEach(pedido -> {
                                        pedido.imprimir();
                                        imprimirLinha();
                                    });
                                break;
                            case 5:
                                imprimirLinhaComTitulo("Lista pedidos por data");
                                break;
                            case 6:
                                imprimirLinhaComTitulo("Buscar pedido pelo numero");
                                break;
                            case 7:
                                imprimirLinhaComTitulo("Lista de pedidos pagos");
                                break;
                            case 8:
                                imprimirLinhaComTitulo("Busca de produto pelo nome");
                                break;
                            case 9:
                                imprimirLinhaComTitulo("Calculo do total de pedidos em aberto (não pagos)");
                                break;
                            case 10:
                                System.out.println("\nSaindo do menu de relatorio...");
                                break;
                            default:
                                System.out.println("\nSelecione uma opcao valida de relatorio");
                        }
                    } while (opcaoRelatorio != opcoesMenuRelatorio.length);
                    break;
                case 7:
                    System.out.println("\nFinalizando o programa...");
                    break;
                default:
                    System.out.println("\nSelecione uma opcao valida.");
            }
        } while (opcaoSelecionada != opcoesDoMenuPrincipal.length);
    }

    private static Pedido efetuarPedidoComIdentificador(int identificador, List<Cliente> clientes, List<Produto> produtos) throws Exception {
        Pedido novoPedido = new Pedido(identificador);
        String nomeClienteProcurado;

        boolean continuarCadastrando;
        char opcaoDigitada;
        float valorTotalDoPedido = 0;

        imprimirLinhaComTitulo("Efetuar pedido");

        if (clientes.isEmpty())
            throw new Exception("Por favor, cadastre ao menos 1 cliente para efetuar o pedido");

        if (produtos.isEmpty())
            throw new Exception("Por favor, cadastre ao menos 1 produto para efetuar o pedido");

        System.out.println("Lista de clientes disponiveis");
        clientes.forEach(Cliente::imprimir);

        do {
            System.out.print("\nSelecione o cliente (a partir do CPF/CNPJ) para realizar o pedido: ");
            inputScanner.nextLine();
            nomeClienteProcurado = inputScanner.nextLine();


            for (Cliente cliente : clientes) {
                String cpfOuCnpjDoCliente;

                if (cliente instanceof ClientePessoaFisica)
                    cpfOuCnpjDoCliente = ((ClientePessoaFisica) cliente).getCpf();
                else
                    cpfOuCnpjDoCliente = ((ClientePessoaJuridica) cliente).getCnpj();

                if (cpfOuCnpjDoCliente.equalsIgnoreCase(nomeClienteProcurado)) {
                    novoPedido.setCliente(cliente);
                    break;
                }
            }

            if (novoPedido.getCliente() == null)
                System.out.println("\nDigite um CPF/CNPJ Valido...");
        } while (novoPedido.getCliente() == null);

        System.out.println("\nAdicionando itens ao pedido");
        do {
            List<ItemDePedido> itensAtuais = novoPedido.getItensDePedido();

            itensAtuais.add(cadastrarItemDePedido(produtos));

            System.out.print("\nDeseja continuar adicionando itens ? (S/n): ");
            opcaoDigitada = inputScanner.next().charAt(0);
            continuarCadastrando = opcaoDigitada != 'n' && opcaoDigitada != 'N';
            inputScanner.nextLine();

        } while (continuarCadastrando);

        System.out.println("\nPedido efetuado com sucesso");

        for (ItemDePedido item : novoPedido.getItensDePedido())
            valorTotalDoPedido += item.getValorTotal();

        novoPedido.setValorTotal(valorTotalDoPedido);
        return novoPedido;
    }

    private static ItemDePedido cadastrarItemDePedido(List<Produto> produtos) {
        ItemDePedido novoItemDePedido = new ItemDePedido();
        String nomeProdutoProcurado;

        System.out.println("\nLista de produtos disponiveis");
        produtos.forEach(Produto::imprimir);

        do {
            System.out.print("\nSelecione o produto (a partir do NOME) para realizar o pedido: ");
            nomeProdutoProcurado = inputScanner.nextLine();

            for (Produto produto : produtos) {
                if (produto.getNome().equalsIgnoreCase(nomeProdutoProcurado)) {
                    novoItemDePedido.setProduto(produto);
                    break;
                }
            }

            if (novoItemDePedido.getProduto() == null)
                System.out.println("\nDigite um NOME Valido...");
        } while (novoItemDePedido.getProduto() == null);

        System.out.print("Quantidade: ");
        novoItemDePedido.setQuantidade(inputScanner.nextInt());
        novoItemDePedido.setValorTotal(novoItemDePedido.getProduto().getValorUnitario() * novoItemDePedido.getQuantidade());

        return novoItemDePedido;
    }

    private static void cadastrarListaClientes(List<Cliente> clientes) {
        boolean continuarCadastrando;
        char opcaoDigitada;

        imprimirLinhaComTitulo("Cadastro de Clientes");

        do {
            clientes.add(cadastrarCliente());

            System.out.print("\nDeseja continuar cadastrando ? (S/n): ");
            opcaoDigitada = inputScanner.next().charAt(0);
            continuarCadastrando = opcaoDigitada != 'n' && opcaoDigitada != 'N';
        } while (continuarCadastrando);
    }

    private static Cliente cadastrarCliente() {
        Cliente novoCliente;
        boolean clienteSeraPessoaJuridica;

        System.out.print("\nTipo de pessoa (Fisica/Juridica): ");
        clienteSeraPessoaJuridica = inputScanner.next().equalsIgnoreCase("juridica");
        inputScanner.nextLine();

        if (clienteSeraPessoaJuridica)
            novoCliente = new ClientePessoaJuridica();
        else
            novoCliente = new ClientePessoaFisica();

        System.out.print("\nNOME do cliente: ");
        novoCliente.setNome(inputScanner.nextLine());

        System.out.print("EMAIL do cliente: ");
        novoCliente.setEmail(inputScanner.nextLine());

        if (novoCliente instanceof ClientePessoaFisica) {
            System.out.print("\nCPF do cliente: ");
            ((ClientePessoaFisica) novoCliente).setCpf(inputScanner.next());

            System.out.print("Quantidade maxima de parcelamento dos pedidos: ");
            ((ClientePessoaFisica) novoCliente).setQtdMaxParcelamentoPedido(inputScanner.nextInt());
        } else {
            System.out.print("\nCNPJ do cliente: ");
            ((ClientePessoaJuridica) novoCliente).setCnpj(inputScanner.next());

            System.out.print("Prazo maximo de faturamento dos pedidos: ");
            ((ClientePessoaJuridica) novoCliente).setPrazoMaxFaturamentoPedido(inputScanner.nextInt());
        }

        return novoCliente;
    }

    private static void cadastrarListaFornecedores(List<Fornecedor> fornecedores) {
        boolean continuarCadastrando;
        char opcaoDigitada;

        imprimirLinhaComTitulo("Cadastro de Fornecedores");

        do {
            fornecedores.add(cadastrarFornecedor());

            System.out.print("\nDeseja continuar cadastrando ? (S/n): ");
            opcaoDigitada = inputScanner.next().charAt(0);
            continuarCadastrando = opcaoDigitada != 'n' && opcaoDigitada != 'N';

        } while (continuarCadastrando);
    }

    private static Fornecedor cadastrarFornecedor() {
        Fornecedor fornecedorAtual = new Fornecedor();

        System.out.print("\nNOME do Fornecedor: ");
        inputScanner.nextLine();
        fornecedorAtual.setNome(inputScanner.nextLine());

        System.out.print("CNPJ do Fornecedor: ");
        fornecedorAtual.setCnpj(inputScanner.next());

        return fornecedorAtual;
    }

    private static void cadastrarListaProdutos(List<Produto> produtos, List<Fornecedor> fornecedores) throws Exception {
        boolean continuarCadastrando;
        char opcaoDigitada;

        imprimirLinhaComTitulo("Cadastro de Produtos");

        if (fornecedores.isEmpty())
            throw new Exception("Por favor, cadastre ao menos 1 fornecedor para cadastrar os produtos");

        do {
            produtos.add(cadastrarProduto(fornecedores));

            System.out.print("\nDeseja continuar cadastrando ? (S/n): ");
            opcaoDigitada = inputScanner.next().charAt(0);
            continuarCadastrando = opcaoDigitada != 'n' && opcaoDigitada != 'N';

        } while (continuarCadastrando);
    }

    private static Produto cadastrarProduto(List<Fornecedor> fornecedores) {
        String cnpjProcurado;
        Produto produtoAtual = new Produto();

        System.out.print("\nCADASTRO - Nome do produto: ");
        inputScanner.nextLine();
        produtoAtual.setNome(inputScanner.nextLine());

        System.out.print("CADASTRO - Descrição do produto: ");
        produtoAtual.setDescricao(inputScanner.nextLine());

        System.out.print("CADASTRO - Valor unitário do produto: ");
        produtoAtual.setValorUnitario(inputScanner.nextFloat());

        System.out.println("\nFornecedores disponiveis ");
        fornecedores.forEach(Fornecedor::imprimir);

        do {
            System.out.print("\nCADASTRO - Selecione um fornecedor pelo CNPJ: ");
            cnpjProcurado = inputScanner.next();

            for (Fornecedor fornecedor : fornecedores) {
                if (fornecedor.getCnpj().equalsIgnoreCase(cnpjProcurado)) {
                    produtoAtual.setFornecedor(fornecedor);
                    break;
                }
            }

            if (produtoAtual.getFornecedor() == null)
                System.out.println("\nDigite um CNPJ Valido...");
        } while (produtoAtual.getFornecedor() == null);

        return produtoAtual;
    }

    private static void imprimirMenu(String[] menu, String tituloMenu) {
        int numeroOpcao = 1;

        imprimirLinhaComTitulo(tituloMenu);

        for (String opcao : menu) {
            System.out.println((numeroOpcao++) + ". " + opcao);
        }
    }

    private static int receberOpcaoInt() {
        System.out.print("\nDigite a opcao desejada: ");
        return inputScanner.nextInt();
    }

    private static void imprimirLinhaComTitulo(String titulo) {
        System.out.println("\n_____________ " + titulo + " _____________");
    }

    private static void imprimirLinha() {
        System.out.println("\n__________________________");
    }
}
