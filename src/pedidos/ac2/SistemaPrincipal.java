package pedidos.ac2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaPrincipal {
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
            opcaoSelecionada = receberOpcao();

            switch (opcaoSelecionada) {
                case 1:
                    // TODO: Implementar switch cases
                    break;
                case 2:
                    break;
                default:
                    System.out.println("\nSelecione uma opcao valida");
            }

        } while (opcaoSelecionada != opcoesDoMenuPrincipal.length);

        imprimirMenu(opcoesDoMenuPrincipal, "Menu Principal");
    }

    private static void imprimirMenu(String[] menu, String tituloMenu) {
        int numeroOpcao = 1;

        imprimirLinhaComTitulo(tituloMenu);

        for (String opcao : menu) {
            System.out.println((numeroOpcao++) + ". " + opcao);
        }
    }

    private static int receberOpcao() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nDigite a opcao desejada: ");
        return input.nextInt();
    }

    private static void imprimirLinhaComTitulo(String titulo) {
        System.out.println("\n_____________ " + titulo + " _____________");
    }

    private static void imprimirLinha() {
        System.out.println("\n__________________________");
    }
}
