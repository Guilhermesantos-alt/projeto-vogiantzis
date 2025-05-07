import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public void adicionarIngrediente(Ingrediente ingrediente) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO Estoque (ingrediente, quantidade, necessarioRepor) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ingrediente.getNome());
            stmt.setLong(2, ingrediente.getQuantidade());
            stmt.setBoolean(3, ingrediente.isNecessarioRepor());

            stmt.executeUpdate();
            System.out.println("Ingrediente inserido com sucesso!");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente: " + e.getMessage());
        }
    }

    public List<Ingrediente> listarIngredientes() {
        List<Ingrediente> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT ingrediente, quantidade, necessarioRepor FROM Estoque";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("ingrediente");
                long quantidade = rs.getLong("quantidade");

                Ingrediente ing = new Ingrediente(nome, quantidade);
                ing.setQuantidade(quantidade);
                lista.add(ing);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar ingredientes: " + e.getMessage());
        }

        return lista;
    }

    public void usarIngrediente(String nomeIngrediente, long quantidadeUsada) {
        Connection conn = Conexao.conectar();

        try {
            String selectSQL = "SELECT quantidade FROM Estoque WHERE ingrediente = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
            selectStmt.setString(1, nomeIngrediente);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                long quantidadeAtual = rs.getLong("quantidade");
                long novaQuantidade = quantidadeAtual - quantidadeUsada;

                String updateSQL = "UPDATE Estoque SET quantidade = ?, necessarioRepor = ? WHERE ingrediente = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setLong(1, novaQuantidade);
                updateStmt.setBoolean(2, novaQuantidade < 10);
                updateStmt.setString(3, nomeIngrediente);
                updateStmt.executeUpdate();

                System.out.println("✅ Uso registrado: " + quantidadeUsada + " de " + nomeIngrediente);
                updateStmt.close();
            } else {
                System.out.println("❌ Ingrediente não encontrado: " + nomeIngrediente);
            }

            rs.close();
            selectStmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro ao usar ingrediente: " + e.getMessage());
        }
    }

    public void usarReceita(Receita receita) {
        System.out.println("🍳 Tentando preparar receita: " + receita.getNome());

        Connection conn = Conexao.conectar();
        boolean podeExecutar = true;

        try {
            for (Ingrediente ing : receita.getIngredientes()) {
                String sql = "SELECT quantidade FROM Estoque WHERE ingrediente = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, ing.getNome());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    long quantidadeAtual = rs.getLong("quantidade");
                    if (quantidadeAtual < ing.getQuantidade()) {
                        System.out.println("❌ Ingrediente insuficiente: " + ing.getNome() +
                                           " (Precisa: " + ing.getQuantidade() +
                                           ", Disponível: " + quantidadeAtual + ")");
                        podeExecutar = false;
                    }
                } else {
                    System.out.println("❌ Ingrediente não encontrado: " + ing.getNome());
                    podeExecutar = false;
                }

                rs.close();
                stmt.close();
            }

            if (!podeExecutar) {
                System.out.println("❌ Receita '" + receita.getNome() + "' cancelada por falta de ingredientes.");
                conn.close();
                return;
            }

            for (Ingrediente ing : receita.getIngredientes()) {
                usarIngrediente(ing.getNome(), ing.getQuantidade());
            }

            System.out.println("✅ Receita '" + receita.getNome() + "' preparada com sucesso!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro ao preparar receita: " + e.getMessage());
        }
    }
}