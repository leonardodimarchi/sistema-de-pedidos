package pedidos.ac2;

public class ItemDePedido {
    public int quantidade;
    public float valorTotal;
    public Produto produto;

    // Constructors

    public ItemDePedido() {
        this.quantidade = 0;
        this.valorTotal = 0;
    }

    public ItemDePedido(int quantidade, float valorTotal, Produto produto) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.produto = produto;
    }

    // Getters e Setters

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
