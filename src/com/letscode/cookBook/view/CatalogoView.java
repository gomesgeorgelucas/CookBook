package com.letscode.cookBook.view;

import com.letscode.cookBook.controller.Catalogo;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.enums.Categoria;
import java.util.Scanner;

public class CatalogoView {
    private final Receita NONE_FOUND = new Receita("Nenhuma receita encontrada", Categoria.PRATO_UNICO);
    private Receita receita;
    Catalogo controller = new Catalogo();
    private int curIndex = -1;

    boolean debug = true;

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
        System.out.println(receita);
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
        if (curIndex >= 0) {
            controller.del(receita.getNome());
            curIndex--;
            if (curIndex < 0) {
                this.receita = null;
            } else {
                this.receita = controller.getReceita(curIndex);
            }
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
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#: ", 80);

        if (debug) {
            System.out.println("Curent Index: " + curIndex);

        }
    }

    public void search() {
        String nome = "";
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
                default:
                    ScreenUtil.printTextLine("Opção inválida", 80);
                    ScreenUtil.printTextLine("#: ", 80);
            }
            loadView();
        } while (true);
    }
}
