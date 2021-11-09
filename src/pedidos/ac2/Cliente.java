package pedidos.ac2;

public class Cliente {
    private String nome;
    private String email;

    // Constructors

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Cliente() {
        this.nome = "";
        this.email = "";
    }

    // Metodos

    protected void imprimir() {
        String dados =
                "\nNome: " + this.nome +
                "\nEmail: " + this.email;

        System.out.print(dados);
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
}
