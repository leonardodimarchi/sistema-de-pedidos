package pedidos.ac2;

public class ClientePessoaFisica extends Cliente {
    private String cpf;
    private int qtdMaxParcelamentoPedido;

    // Constructors

    public ClientePessoaFisica(String cpf, int qtdMaxParcelamentoPedido) {
        this.cpf = cpf;
        this.qtdMaxParcelamentoPedido = qtdMaxParcelamentoPedido;
    }

    public ClientePessoaFisica() {
        this.cpf = "";
        this.qtdMaxParcelamentoPedido = 0;
    }

    // Metodos

    @Override
    protected void imprimir() {
        super.imprimir();
        String dados =
                "\nCPF: " + this.cpf +
                "\nQuantidade maxima de parcelamento de pedidos: " + this.qtdMaxParcelamentoPedido;

        System.out.println(dados);
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
}
