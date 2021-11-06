package pedidos.ac2;

import java.util.ArrayList;
import java.util.List;

public class ClientePessoaFisica extends Cliente {
    private String cpf;
    private int qtdMaxParcelamentoPedido;
    private List<Pedido> pedidos;

    // Constructors

    public ClientePessoaFisica(String cpf, int qtdMaxParcelamentoPedido, List<Pedido> pedidos) {
        this.cpf = cpf;
        this.qtdMaxParcelamentoPedido = qtdMaxParcelamentoPedido;
        this.pedidos = pedidos;
    }

    public ClientePessoaFisica() {
        this.cpf = "";
        this.qtdMaxParcelamentoPedido = 0;
        this.pedidos = new ArrayList<Pedido>();
    }

    // Getters e Setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getQtdMaxParcelamentoPedido() {
        return qtdMaxParcelamentoPedido;
    }

    public void setQtdMaxParcelamentoPedido(int qtdMaxParcelamentoPedido) {
        this.qtdMaxParcelamentoPedido = qtdMaxParcelamentoPedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
