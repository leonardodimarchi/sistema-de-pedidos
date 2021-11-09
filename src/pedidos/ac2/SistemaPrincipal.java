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
                    // Efetuar pedido
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
        fornecedorAtual.setNome(inputScanner.next());

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
        Fornecedor fornecedorEncontrado;
        Produto produtoAtual = new Produto();

        System.out.print("\nCADASTRO - Nome do produto: ");
        produtoAtual.setNome(inputScanner.next());

        System.out.print("CADASTRO - Descrição do produto: ");
        produtoAtual.setDescricao(inputScanner.next());

        System.out.print("CADASTRO - Valor unitário do produto: ");
        produtoAtual.setValorUnitario(inputScanner.nextFloat());

        System.out.println("Fornecedores disponiveis: ");
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
