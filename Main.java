import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EstoqueDAO dao = new EstoqueDAO();

        // Criar e usar uma receita
        Ingrediente tomate = new Ingrediente("Tomate", 2);
        Ingrediente cebola = new Ingrediente("Cebola", 1);
        Receita molho = new Receita("Molho", Arrays.asList(tomate, cebola));

        dao.usarReceita(molho);

        // Listar ingredientes após uso
        List<Ingrediente> ingredientes = dao.listarIngredientes();
        for (Ingrediente ing : ingredientes) {
            System.out.println("Nome: " + ing.getNome() +
                               " | Quantidade: " + ing.getQuantidade() +
                               " | Repor? " + (ing.isNecessarioRepor() ? "Sim" : "Não"));
        }
    }
}