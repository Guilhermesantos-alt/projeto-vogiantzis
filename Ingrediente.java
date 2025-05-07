public class Ingrediente {
    private String nome;
    private long quantidade;
    private boolean necessarioRepor;

    public Ingrediente(String nome, long quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.necessarioRepor = quantidade < 10;
    }

    public String getNome() { return nome; }
    public long getQuantidade() { return quantidade; }
    public boolean isNecessarioRepor() { return necessarioRepor; }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
        this.necessarioRepor = quantidade < 10;
    }

    public void repor(long qtd) {
        this.quantidade += qtd;
        this.necessarioRepor = this.quantidade < 10;
    }
}