package com.letscode.cookBook.view;

import com.letscode.cookBook.controller.Catalogo;
import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class CatalogoView {
    private final Receita NONE_FOUND = new Receita("Nenhuma receita encontrada", Categoria.PRATO_UNICO);
    private Receita receita;
    Catalogo controller = new Catalogo();
    private int curIndex = -1;
    boolean run = true;

    boolean debug = true;

    public CatalogoView() {
        if (debug) {

            for (int i = 0; i < new Random().nextInt(10); i++) {
                String nome = "Receita " + (i + 1);
                Categoria categoria = Categoria.values()[new Random().nextInt(12)];
                int tempoPreparo = new Random().nextInt(600);
                Rendimento rendimento = new Rendimento(100, TipoRendimento.values()[new Random().nextInt(2)]);
                List<Ingrediente> ingredientes = new ArrayList<>();
                ingredientes.add(new Ingrediente("Ingrediente 1", Math.floor(new Random().nextDouble() * 60), TipoMedida.values()[new Random().nextInt(10)]));
                ingredientes.add(new Ingrediente("Ingrediente 2", Math.floor(new Random().nextDouble() * 60), TipoMedida.values()[new Random().nextInt(10)]));
                ingredientes.add(new Ingrediente("Ingrediente 3", Math.floor(new Random().nextDouble() * 60), TipoMedida.values()[new Random().nextInt(10)]));
                ingredientes.add(new Ingrediente("Ingrediente 4", Math.floor(new Random().nextDouble() * 60), TipoMedida.values()[new Random().nextInt(10)]));
                List<String> modoPreparo = new ArrayList<>();
                modoPreparo.add("Passos ".repeat(new Random().nextInt(10)));
                modoPreparo.add("Passos ".repeat(new Random().nextInt(10)));
                modoPreparo.add("Passos ".repeat(new Random().nextInt(10)));
                modoPreparo.add("Passos ".repeat(new Random().nextInt(10)));
                Receita receita = new Receita(nome, categoria, tempoPreparo, rendimento, ingredientes, modoPreparo);
                controller.add(receita);

                if (i == 0) {
                    this.receita = receita;
                    curIndex = 0;
                }
            }
        }
    }

    private void showHeader() {
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # ##    ###  #  # #  # ##  ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("", 80, true, '=');
    }

    private void showReceita(Receita receita) {
        System.out.println(receita.toString());
    }

    private void showAnterior() {
        if (curIndex > 0) {
            Receita receitaAnterior = controller.getReceita(curIndex - 1);
            if (receitaAnterior != null) {
                this.receita = receitaAnterior;
                curIndex--;
            }
        }
    }

    private void showSeguinte() {
        Receita receitaSeguinte = controller.getReceita(curIndex + 1);
        if (receitaSeguinte != null) {
            this.receita = receitaSeguinte;
            curIndex++;
        }
    }

    private void add() {
        Receita novaReceita = new NovaReceitaView().nova();
        if (curIndex < 0) {
            controller.add(novaReceita);
            this.receita = novaReceita;
            curIndex++;
        } else {
            controller.add(novaReceita);
        }
    }

    private void del() {
        if (curIndex == 0) {
            if (controller.getReceita(curIndex + 1) != null) {
                controller.del(receita.getNome());
                this.receita = controller.getReceita(curIndex);
            } else {
                controller.del(receita.getNome());
                this.receita = null;
                curIndex = -1;
            }
        } else if (curIndex > 0) {
            if (controller.getReceita(curIndex + 1) != null) {
                controller.del(receita.getNome());
                this.receita = controller.getReceita(curIndex);
            } else if (controller.getReceita(curIndex - 1) != null) {
                controller.del(receita.getNome());
                curIndex--;
                this.receita = controller.getReceita(curIndex);
            }
        } else if (curIndex == -1) {
            System.out.println("Catálogo vazio!");
        }
    }

    private void loadView() {
        showHeader();
        showReceita(receita == null ? NONE_FOUND : receita);
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("P: Receita anterior", 80, true);
        ScreenUtil.printTextLine("N: Receita seguinte", 80, true);
        ScreenUtil.printTextLine("+: Adicionar nova receita", 80, true);
        ScreenUtil.printTextLine("-: Remover receita", 80, true);
        ScreenUtil.printTextLine("S: Pesquisar receita", 80, true);
        ScreenUtil.printTextLine("X: Sair", 80, true);
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#: ", 80);

        if (debug) {
            System.out.println("Current Index: " + curIndex);
        }
    }

    public void search() {
        String nome;
        System.out.print("Digite o nome da receita: ");
        nome = new Scanner(System.in).nextLine();
        Receita receita = controller.getReceita(nome);
        if (receita != null) {
            this.receita = receita;
            curIndex = controller.getIndex(receita);
        } else {
            System.out.println("Receita não encontrada!");
        }

    }

    private void stop() {
        this.run = false;
    }

    public void show() {
        loadView();
        String option;
        do {
            option = new Scanner(System.in).next();
            switch (option.toUpperCase()) {
                case "P":
                    showAnterior();
                    break;
                case "N":
                    showSeguinte();
                    break;
                case "+":
                    add();
                    break;
                case "-":
                    del();
                    break;
                case "S":
                    search();
                    break;
                case "X":
                    stop();
                    break;
                default:
                    ScreenUtil.printTextLine("Opção inválida", 80);
                    ScreenUtil.printTextLine("#: ", 80);
                    break;
            }
            loadView();
        } while (run);
    }
}
