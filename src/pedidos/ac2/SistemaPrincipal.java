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
                "Calculo do total dos pedidos em aberto (nao pagos)"
        };

        List<Cliente> clientes = new ArrayList<>();
        List<Fornecedor> fornecedores = new ArrayList<>();
        List<Produto> produtos = new ArrayList<>();

        int opcaoSelecionada;

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

                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:
                    System.out.println("\nSelecione uma opcao valida");
            }
        } while (opcaoSelecionada != opcoesDoMenuPrincipal.length);

        System.out.println("\nFinalizando o programa...");
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
