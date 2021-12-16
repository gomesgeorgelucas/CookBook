package com.letscode.cookBook.view;

import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * String nome;
 * Categoria categoria;
 * int tempoPreparo;
 * Rendimento rendimento;
 * List<Ingrediente> ingredientes;
 * List<String> modoPreparo;
 */
public class NovaReceitaView {
    Scanner scanner;
    Receita receita;
    String nome;

    public NovaReceitaView() {
        this.scanner = new Scanner(System.in);
    }

    public Receita nova() {
        this.receita = new Receita(askNome(), askCategoria());
        this.receita.setTempoPreparo(askTempoPreparo());
        this.receita.setRendimento(askRendimento());
        this.receita.getIngredientes().addAll(askIngredientes());
        this.receita.getModoPreparo().addAll(askModoPreparo());
        return receita;
    }

    private String askNome() {
        System.out.println("Qual o nome da receita?");
        nome = scanner.nextLine();
        if (nome.isBlank()) {
            System.out.println("Nome inválido!");
            askNome();
        }

        return nome;
    }

    private Categoria askCategoria() {
        System.out.println("Qual a categoria da receita?");
        for (Categoria cat : Categoria.values()) {
            System.out.printf("%d - %s", cat.ordinal(), cat.name());
        }
        int categoria = scanner.nextInt();
        if (categoria < 0 || categoria >= Categoria.values().length) {
            System.out.println("Categoria inválida!");
            askCategoria();
        }
        return Categoria.values()[categoria];
    }

    private int askTempoPreparo() {
        System.out.println("Pede tempo preparo");
        return 0;
    }

    private Rendimento askRendimento() {
        System.out.println("Pede rendimento");
        Rendimento rendimento = null;
        return rendimento;
    }

    private List<Ingrediente> askIngredientes() {
        System.out.println("Pede ingredientes");
        List<Ingrediente> ingredientes = new ArrayList<>();
        return ingredientes;
    }

    private List<String> askModoPreparo() {
        System.out.println("Pede modo de preparo");
        List<String> modoPreparo = new ArrayList<>();
        return modoPreparo;
    }


}
