package pedidos.ac2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private List<ItemDePedido> itensDePedido;
    private String identificador;
    private Date data;
    private float valorTotal;
    private Cliente cliente;
    private boolean estaPago;

    // Constructors

    public Pedido() {
        this.itensDePedido = new ArrayList<>();
        this.identificador = "";
        this.data = new Date();
        this.valorTotal = 0;
        this.estaPago = false;
    }

    public Pedido(List<ItemDePedido> itensDePedido,
                  String identificador,
                  Date data,
                  float valorTotal,
                  Cliente cliente,
                  boolean estaPago) {
        this.itensDePedido = itensDePedido;
        this.identificador = identificador;
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.estaPago = estaPago;
    }

    // Getters e Setters

    public List<ItemDePedido> getItensDePedido() {
        return itensDePedido;
    }

    public void setItensDePedido(List<ItemDePedido> itensDePedido) {
        this.itensDePedido = itensDePedido;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean estaPago() {
        return estaPago;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }
}
