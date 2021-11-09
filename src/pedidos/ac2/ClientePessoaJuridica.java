package pedidos.ac2;

public class ClientePessoaJuridica extends Cliente {
    private String cnpj;
    private int prazoMaxFaturamentoPedido;

    // Constructors

    public ClientePessoaJuridica(String cnpj, int prazoMaxFaturamentoPedido) {
        this.cnpj = cnpj;
        this.prazoMaxFaturamentoPedido = prazoMaxFaturamentoPedido;
    }

    public ClientePessoaJuridica() {
        this.cnpj = "";
        this.prazoMaxFaturamentoPedido = 0;
    }

    // Metodos

    @Override
    protected void imprimir() {
        super.imprimir();
        String dados =
                "\nCPF: " + this.cnpj +
                "\nQuantidade maxima de parcelamento de pedidos: " + this.prazoMaxFaturamentoPedido;

        System.out.println(dados);
    }

    // Getters e Setters

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getPrazoMaxFaturamentoPedido() {
        return prazoMaxFaturamentoPedido;
    }

    public void setPrazoMaxFaturamentoPedido(int prazoMaxFaturamentoPedido) {
        this.prazoMaxFaturamentoPedido = prazoMaxFaturamentoPedido;
    }
}
