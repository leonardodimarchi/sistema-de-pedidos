package pedidos.ac2;

public class ItemDePedido {
    private int quantidade;
    private float valorTotal;
    private Produto produto;

    // Constructors

    public ItemDePedido() {
        this.quantidade = 0;
        this.valorTotal = 0;
    }

    public ItemDePedido(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.valorTotal = produto.getValorUnitario() * quantidade;
    }

    public ItemDePedido(int quantidade, float valorTotal, Produto produto) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.produto = produto;
    }

    // Metodos

    public void imprimir() {
        String dados =
                "\nQuantidade: " + this.quantidade +
                "\nValor total: " + this.valorTotal +
                "\nProduto: " + this.produto.getNome();

        System.out.println(dados);
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
