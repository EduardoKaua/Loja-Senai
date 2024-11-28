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
            System.out.println("6. Buscar Produto por Nome");
            System.out.println("7. Buscar Produto por Categoria");
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
                case 6 -> buscarProdutoPorNome();
                case 7 -> buscarProdutoPorCategoria();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (escolha != 0);
    }

    private void adicionarProduto() {
        System.out.print("Nome do Produto: ");
        String nome = entrada.nextLine();
        double preco = lerPrecoValido("Preço: ");
        int quantidade = lerQuantidadeValida("Quantidade: ");
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
            produto.setPreco(lerPrecoValido("Novo Preço: "));
            produto.setQuantidadeEstoque(lerQuantidadeValida("Nova Quantidade: "));
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

    private void buscarProdutoPorNome() {
        System.out.print("Informe o Nome do Produto: ");
        String nome = entrada.nextLine();
        List<Produto> produtos = gerenciador.localizarPorNome(nome);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com esse nome.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private void buscarProdutoPorCategoria() {
        System.out.print("Informe a Categoria do Produto: ");
        String categoria = entrada.nextLine();
        List<Produto> produtos = gerenciador.localizarPorCategoria(categoria);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado nessa categoria.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private double lerPrecoValido(String mensagem) {
        double preco;
        while (true) {
            try {
                System.out.print(mensagem);
                preco = Double.parseDouble(entrada.nextLine());
                if (preco <= 0) {
                    System.out.println("O preço deve ser maior que zero.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }
        return preco;
    }

    private int lerQuantidadeValida(String mensagem) {
        int quantidade;
        while (true) {
            try {
                System.out.print(mensagem);
                quantidade = Integer.parseInt(entrada.nextLine());
                if (quantidade < 0) {
                    System.out.println("A quantidade não pode ser negativa.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro válido.");
            }
        }
        return quantidade;
    }
}
