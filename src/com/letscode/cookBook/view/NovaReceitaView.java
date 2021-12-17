package com.letscode.cookBook.view;

import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * String nome;
 * Categoria categoria;
 * int tempoPreparo;
 * Rendimento rendimento;
 * List<Ingrediente> ingredientes;
 * List<String> modoPreparo;
 */
public class NovaReceitaView {
    Receita receita;
    String nome;
    Categoria categoria;
    int tempoPreparo;
    Rendimento rendimento;
    List<Ingrediente> ingredientes = new ArrayList<>();
    List<String> modoPreparo = new ArrayList<>();

    public Receita nova() {
        this.receita = new Receita(askNome(), askCategoria(), askTempoPreparo(),
                askRendimento(), askIngredientes(), askModoPreparo());
        return receita;
    }

    private String askNome() {
        System.out.println("Qual o nome da receita?");
        nome = new Scanner(System.in).nextLine();
        if (nome.isBlank()) {
            System.out.println("Nome inválido!");
            askNome();
        }

        return nome;
    }

    private Categoria askCategoria() {
        int categoria = 0;
        System.out.println("Qual a categoria da receita?");
        for (Categoria cat : Categoria.values()) {
            System.out.printf("%d - %s%n", cat.ordinal(), cat.name());
        }
        try {
            categoria = new Scanner(System.in).nextInt();

            if (categoria < 0 || categoria >= Categoria.values().length) {
                System.out.println("Categoria inválida!");
                askCategoria();
            }
            this.categoria = Categoria.values()[categoria];
            return this.categoria;
        } catch (InputMismatchException e) {
            System.out.println("Categoria inválida!");
            askCategoria();
        }

        return this.categoria;
    }

    private int askTempoPreparo() {
        String tempo = "";
        System.out.println("Qual o tempo de preparo em minutos?");
        tempo = new Scanner(System.in).nextLine();

        if (tempo.isBlank()) {
            System.out.println("Tempo inválido! Tente novamente!");
            askTempoPreparo();
        }

        try {
            this.tempoPreparo = Integer.parseInt(tempo);
            if (this.tempoPreparo < 0) {
                System.out.println("Tempo inválido! Tente novamente!");
                askTempoPreparo();
            }
        } catch (NumberFormatException e) {
            System.out.println("Tempo inválido! Tente novamente!");
            this.tempoPreparo = 0;
            askTempoPreparo();
        }

        return this.tempoPreparo;
    }

    private Rendimento askRendimento() {
        System.out.println("Qual é o tipo de rendimento da receita?");
        int tipoRendimento = 0;
        int quantidade = 0;
        for (TipoRendimento tipo : TipoRendimento.values()) {
            System.out.printf("%d - %s%n", tipo.ordinal(), tipo.name());
        }
        try {
            tipoRendimento = new Scanner(System.in).nextInt();

            if (tipoRendimento < 0 || tipoRendimento >= Categoria.values().length) {
                System.out.println("Tipo inválido!");
                askRendimento();
            }

            System.out.println("Qual é a quantidade de " + TipoRendimento.values()[tipoRendimento].name() + "?");

            quantidade = new Scanner(System.in).nextInt();

            if (quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                askRendimento();
            }

            this.rendimento = new Rendimento(quantidade, TipoRendimento.values()[tipoRendimento]);
            return  this.rendimento;

        } catch (InputMismatchException e) {
            System.out.println("Rendimento inválido!");
            askRendimento();
        }

        return this.rendimento;
    }

    private List<Ingrediente> askIngredientes() {
        System.out.println("Inserir ingredientes.");

        String nome = "";
        double quantidade = 0;
        int tipoMedida = 0;

        String input = "";
        do {
            input = "";
            do {
                System.out.println("Nome do ingrediente: ");
                nome = new Scanner(System.in).nextLine();
                if (nome.isBlank()) {
                    System.out.println("Nome inválido!");
                }
            } while (nome.isBlank());

            try {
                do {
                    System.out.println("Qual é o tipo de medida do ingrediente da receita?");
                    for (TipoMedida tipo : TipoMedida.values()) {
                        System.out.printf("%d - %s%n", tipo.ordinal(), tipo.name());
                    }
                    tipoMedida = new Scanner(System.in).nextInt();
                    if (tipoMedida < 0 || tipoMedida >= TipoMedida.values().length) {
                        System.out.println("Tipo inválido!");
                    }
                } while (tipoMedida < 0 || tipoMedida >= TipoMedida.values().length);

                do {
                    System.out.println("Qual é a quantidade em/de " + TipoMedida.values()[tipoMedida].name() + "?");
                    quantidade = new Scanner(System.in).nextDouble();
                    if (quantidade <= 0) {
                        System.out.println("Quantidade inválida!");
                    }
                } while(quantidade <= 0);

                this.ingredientes.add(new Ingrediente(nome, quantidade, TipoMedida.values()[tipoMedida]));
            } catch (InputMismatchException e) {
                System.out.println("Ingrediente inválido!");
                continue;
            }

            do {
                System.out.print("Novo ingrediente?(S/N): ");
                input = new Scanner(System.in).next().toUpperCase();
            } while (!input.equals("S") && !input.equals("N"));

        } while(!input.equals("N"));

        return this.ingredientes;
    }

    private List<String> askModoPreparo() {
        System.out.println("Insira os passos do preparo.");
        String modo = "";
        String input = "";
        do {
            input = "";
            do {
                System.out.println("Insira passo: " + (this.modoPreparo.size() + 1));
                modo = new Scanner(System.in).nextLine();
                if (modo.isBlank()) {
                    System.out.println("Passo inválido!");
                }
            } while (modo.isBlank());

            this.modoPreparo.add(modo);

            do {
                System.out.print("Novo passo?(S/N): ");
                input = new Scanner(System.in).next().toUpperCase();
            } while (!input.equals("S") && !input.equals("N"));

        } while(!input.equals("N"));

        return modoPreparo;
    }


}
