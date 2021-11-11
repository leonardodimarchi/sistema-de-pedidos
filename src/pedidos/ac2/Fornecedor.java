package pedidos.ac2;

public class Fornecedor {
    private String cnpj;
    private String nome;

    // Constructors

    public Fornecedor () {}

    public Fornecedor (String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    // Metodos

    public void imprimir() {
        String dados =
                "\nCNPJ: " + this.cnpj +
                "\nNome: " + this.nome;

        System.out.println(dados);
    }

    // Getters e Setters

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeFornecedor) {
        this.nome = nomeFornecedor;
    }
}
