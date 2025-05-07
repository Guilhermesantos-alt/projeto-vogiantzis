import java.util.List;

public class Receita {
    private String nome;
    private List<Ingrediente> ingredientes;

    public Receita(String nome, List<Ingrediente> ingredientes) {
        this.nome = nome;
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }
}