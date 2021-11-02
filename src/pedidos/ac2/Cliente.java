package pedidos.ac2;

public class Cliente {
    public String nome;
    public String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Cliente() {
        this.nome = "";
        this.email = "";
    }


    // codes after here


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
