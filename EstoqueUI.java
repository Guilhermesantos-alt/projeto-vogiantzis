package ui;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.util.List;

public class EstoqueUI extends JFrame {
    private EstoqueDAO dao;

    private JTextArea output;
    private JButton atualizarBtn, usarReceitaBtn;

    public EstoqueUI() {
        dao = new EstoqueDAO();
        setTitle("Controle de Estoque");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        atualizarBtn = new JButton("Atualizar Estoque");
        usarReceitaBtn = new JButton("Usar Receita de Teste");

        JPanel topPanel = new JPanel();
        topPanel.add(atualizarBtn);
        topPanel.add(usarReceitaBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        atualizarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarEstoque();
            }
        });

        usarReceitaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usarReceitaTeste();
            }
        });
    }

    private void atualizarEstoque() {
        List<Ingrediente> lista = dao.listarIngredientes();
        output.setText("");
        for (Ingrediente ing : lista) {
            output.append("Ingrediente: " + ing.getNome() + 
                          " | Quantidade: " + ing.getQuantidade() + 
                          " | Repor: " + (ing.isNecessarioRepor() ? "Sim" : "Não") + "\n");
        }
    }

    private void usarReceitaTeste() {
        Ingrediente tomate = new Ingrediente("Tomate", 2);
        Ingrediente cebola = new Ingrediente("Cebola", 1);
        Receita receita = new Receita("Molho", Arrays.asList(tomate, cebola));
        dao.usarReceita(receita);
        atualizarEstoque();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EstoqueUI().setVisible(true);
        });
    }
}