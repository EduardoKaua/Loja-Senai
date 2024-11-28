package com.loja.ui;
import com.loja.gerenciador.GerenciadorProdutos;
import com.loja.modelo.Produto;
import java.util.List;
import java.util.Scanner;

public class MenuProdutos {
    private final GerenciadorProdutos gerenciador;
    private final Scanner entrada;

    public MenuProdutos() {
        this.gerenciador = new GerenciadorProdutos();
        this.entrada = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int escolha;
        do {
            System.out.println("\n=== MENU DE PRODUTOS ===");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Procurar Produto por ID");
            System.out.println("3. Mostrar Todos os Produtos");
            System.out.println("4. Editar Produto");
            System.out.println("5. Remover Produto");
            System.out.println("0. Sair");
            System.out.print("Selecione uma opção: ");
            escolha = entrada.nextInt();
            entrada.nextLine();

            switch (escolha) {
                case 1 -> adicionarProduto();
                case 2 -> buscarProdutoPorId();
                case 3 -> mostrarProdutos();
                case 4 -> editarProduto();
                case 5 -> removerProduto();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (escolha != 0);
    }

    private void adicionarProduto() {
        System.out.print("Nome do Produto: ");
        String nome = entrada.nextLine();
        double preco = solicitarPrecoValido();
        int quantidade = solicitarQuantidadeValida();
        System.out.print("Categoria: ");
        String categoria = entrada.nextLine();

        Produto novoProduto = new Produto(nome, preco, quantidade, categoria);
        gerenciador.registrarProduto(novoProduto);
    }

    private void buscarProdutoPorId() {
        System.out.print("Informe o ID do Produto: ");
        int id = entrada.nextInt();
        Produto produto = gerenciador.localizarPorId(id);
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void mostrarProdutos() {
        List<Produto> produtos = gerenciador.obterTodosProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private void editarProduto() {
        System.out.print("ID do Produto a ser atualizado: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        Produto produto = gerenciador.localizarPorId(id);
        if (produto != null) {
            System.out.print("Novo Nome: ");
            produto.setNome(entrada.nextLine());
            produto.setPreco(solicitarPrecoValido());
            produto.setQuantidadeEstoque(solicitarQuantidadeValida());
            System.out.print("Nova Categoria: ");
            produto.setCategoria(entrada.nextLine());

            gerenciador.modificarProduto(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void removerProduto() {
        System.out.print("ID do Produto a ser removido: ");
        int id = entrada.nextInt();
        if (gerenciador.excluirProduto(id)) {
            System.out.println("Produto removido.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private double solicitarPrecoValido() {
        while (true) {
            try {
                System.out.print("Digite o preço do produto: ");
                double preco = Double.parseDouble(entrada.nextLine());
                if (preco <= 0) {
                    System.out.println("O preço deve ser maior que zero. Tente novamente.");
                } else {
                    return preco;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Insira um valor numérico para o preço.");
            }
        }
    }

    private int solicitarQuantidadeValida() {
        while (true) {
            try {
                System.out.print("Digite a quantidade do produto: ");
                int quantidade = Integer.parseInt(entrada.nextLine());
                if (quantidade < 0) {
                    System.out.println("A quantidade não pode ser negativa. Tente novamente.");
                } else {
                    return quantidade;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Insira um valor numérico inteiro para a quantidade.");
            }
        }
    }
}
