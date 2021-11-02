package pedidos.ac2;

public class Produto {
    public String nome;
    public String descricao;
    public float valorUnitario;
    public Fornecedor fornecedor;

    // Constructors

    public Produto() {
        this.nome = "";
        this.descricao = "";
        this.valorUnitario = 0;
    }

    public Produto(String nome, String descricao, float valorUnitario, Fornecedor fornecedor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
