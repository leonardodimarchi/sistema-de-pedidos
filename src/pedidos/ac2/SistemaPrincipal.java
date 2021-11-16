package pedidos.ac2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
                    try {
                        baixaDePagamento(pedidos);
                    } catch (Exception error) {
                        System.out.println("\n" + error.getMessage());
                    }
                    break;
                case 6:
                    do {
                        imprimirMenu(opcoesMenuRelatorio, "Relatorios");
                        opcaoRelatorio = receberOpcaoInt();

                        selecionarOpcaoDeRelatorio(opcaoRelatorio, clientes, fornecedores, produtos, pedidos);
                    } while (opcaoRelatorio != opcoesMenuRelatorio.length);
                    break;
                case 7:
                    System.out.println("\n\nSistema encerrado!");
                    break;
                default:
                    System.out.println("\nSelecione uma opcao valida.");
            }
        } while (opcaoSelecionada != opcoesDoMenuPrincipal.length);
    }

    private static void selecionarOpcaoDeRelatorio(int opcaoRelatorio, List<Cliente> clientes, List<Fornecedor> fornecedores, List<Produto> produtos, List<Pedido> pedidos) {
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
                if (pedidos.isEmpty()) {
                    System.out.println("\nNenhum pedido cadastrado");
                    break;
                }
                try {
                    System.out.print("\nDigite a data inicial (dd/MM/yyyy): ");
                    String dataInicialDigitada = inputScanner.next();

                    System.out.print("\nDigite a data final (dd/MM/yyyy): ");
                    String dataFinalDigitada = inputScanner.next();

                    Date dataInicial = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicialDigitada);
                    Date dataFinal = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinalDigitada);

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

                    List<Pedido> listaFiltrada = pedidos.stream()
                            .filter(pedido ->  pedido.getData().after(dataInicial) && pedido.getData().before(dataFinal))
                            .collect(Collectors.toList());

                    if (listaFiltrada.isEmpty()) {
                        System.out.println("\nNenhum pedido encontrado para o intervalo de datas: " +
                                dateFormat.format(dataInicial) + " e " + dateFormat.format(dataFinal));
                    	System.out.println("\nOBS: as datas iniciam a contagem de 00:00 pm");
                    }
                    else
                    	listaFiltrada.forEach(Pedido::imprimir);

                } catch (ParseException errorParsing) {
                    System.out.println("\nPor favor, utilizar o formato especificado...");
                } catch (Exception error) {
                    System.out.println("\nOcorreu um erro inesperado... Tente novamente.");
                }

                break;
            case 6:
                imprimirLinhaComTitulo("Buscar pedido pelo numero");

                if (pedidos.isEmpty()) {
                    System.out.println("\nNenhum pedido cadastrado");
                    break;
                }

                System.out.print("\nDigite o identificador desejado: ");
                int identificadorBuscado = inputScanner.nextInt();

                try {
                    Pedido pedidoSelecionado = encontrarPedidoPorIndentificador(pedidos, identificadorBuscado);
                    pedidoSelecionado.imprimir();
                } catch (Exception error) {
                    System.out.println("\n" + error.getMessage());
                }

                break;
            case 7:
                imprimirLinhaComTitulo("Lista de pedidos pagos");

                if (pedidos.isEmpty()) {
                    System.out.println("\nNenhum pedido cadastrado");
                    break;
                }

                List<Pedido> pedidosPagos = pedidos.stream()
                        .filter(Pedido::estaPago)
                        .collect(Collectors.toList());

                if (pedidosPagos.isEmpty())
                    System.out.println("\nNenhum pedido esta pago no momento");
                else
                    pedidosPagos.forEach(Pedido::imprimir);

                break;
            case 8:
                imprimirLinhaComTitulo("Busca de produto pelo nome");

                if (produtos.isEmpty()) {
                    System.out.println("\nNenhum produto cadastrado");
                    break;
                }

                inputScanner.nextLine();
                System.out.print("\nDigite o nome do produto: ");
                String nomeProcurado = inputScanner.nextLine();

                List<Produto> produtosEncontrados = produtos.stream()
                        .filter(produto -> produto.getNome().toLowerCase().contains(nomeProcurado.toLowerCase()))
                        .collect(Collectors.toList());

                if (produtosEncontrados.isEmpty())
                    System.out.println("\nNao foram encontrados produtos contendo este nome");
                else
                    produtosEncontrados.forEach(Produto::imprimir);

                break;
            case 9:
                imprimirLinhaComTitulo("Calculo do total de pedidos em aberto (nao pagos)");

                if (pedidos.isEmpty()) {
                    System.out.println("\nNenhum pedido cadastrado");
                    break;
                }

                float valorTotalEmAberto = 0;

                List<Pedido> pedidosEmAberto = pedidos.stream()
                        .filter(pedido -> !pedido.estaPago())
                        .collect(Collectors.toList());

                if (pedidosEmAberto.isEmpty()) {
                    System.out.println("\nNenhum pedido em aberto");
                    break;
                }

                for (Pedido pedido : pedidosEmAberto)
                    valorTotalEmAberto += pedido.getValorTotal();

                System.out.println("\nValor total de pedidos em aberto: " + valorTotalEmAberto);

                break;
            case 10:
                System.out.println("\nSaindo do menu de relatorio...");
                break;
            default:
                System.out.println("\nSelecione uma opcao valida de relatorio");
        }
    }

    private static Pedido encontrarPedidoPorIndentificador(List<Pedido> pedidos, int identificadorBuscado) throws Exception {
        Pedido pedidoSelecionado = null;

        for (Pedido pedido : pedidos) {
            if (pedido.getIdentificador() == identificadorBuscado)
                pedidoSelecionado = pedido;
        }

        if (pedidoSelecionado == null)
            throw new Exception("Nenhum pedido encontrado com esse identificador");
        else
            return pedidoSelecionado;
    }

    private static void baixaDePagamento(List<Pedido> pedidos) throws Exception {
        imprimirLinhaComTitulo("Efetuar baixa de pagamento de pedido");

        if (pedidos.isEmpty())
            throw new Exception("Por favor, cadastre ao menos 1 pedido para efetuar a baixa de pagamento");

        int identificadorDesejado;
        List<Pedido> pedidosEmAberto = pedidos.stream()
                .filter(pedido -> !pedido.estaPago())
                .collect(Collectors.toList());

        if (pedidosEmAberto.isEmpty())
        	throw new Exception("Nenhum pedido esta em aberto");
        	
        pedidosEmAberto.forEach(pedido -> {
            pedido.imprimir();
            imprimirLinha();
        });

        System.out.print("\nDigite o identificador desejado: ");
        identificadorDesejado = inputScanner.nextInt();

        try {
            Pedido pedidoEncontrado = encontrarPedidoPorIndentificador(pedidosEmAberto, identificadorDesejado);
            pedidoEncontrado.setEstaPago(true);
            pedidoEncontrado.imprimir();
            System.out.println("\nBaixa de pagamento feito com sucesso");
        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }
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

        inputScanner.nextLine();
        do {
            System.out.print("\nSelecione o cliente (a partir do CPF/CNPJ) para realizar o pedido: ");
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
            System.out.print("\nSelecione o produto (a partir do nome) para realizar o pedido: ");
            nomeProdutoProcurado = inputScanner.nextLine();

            for (Produto produto : produtos) {
                if (produto.getNome().equalsIgnoreCase(nomeProdutoProcurado)) {
                    novoItemDePedido.setProduto(produto);
                    break;
                }
            }

            if (novoItemDePedido.getProduto() == null)
                System.out.println("\nDigite um nome Valido...");
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

        System.out.print("CADASTRO - Descricao do produto: ");
        produtoAtual.setDescricao(inputScanner.nextLine());

        System.out.print("CADASTRO - Valor unitario do produto: ");
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
