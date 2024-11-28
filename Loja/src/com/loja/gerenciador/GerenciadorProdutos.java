package com.loja.gerenciador;

import com.loja.modelo.Produto;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutos {
    private List<Produto> listaProdutos;
    private int idAtual;

    public GerenciadorProdutos() {
        this.listaProdutos = new ArrayList<>();
        this.idAtual = 1;
    }

    // Adiciona um novo produto à lista
    public void registrarProduto(Produto produto) {
        verificarProduto(produto);
        produto.setId(idAtual++);
        listaProdutos.add(produto);
        System.out.println("Produto registrado com sucesso!");
    }

    // Localiza um produto pelo ID
    public Produto localizarPorId(int id) {
        return listaProdutos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Retorna todos os produtos
    public List<Produto> obterTodosProdutos() {
        return new ArrayList<>(listaProdutos);
    }

    // Atualiza as informações de um produto
    public boolean modificarProduto(Produto produtoModificado) {
        Produto existente = localizarPorId(produtoModificado.getId());
        if (existente != null) {
            // Validação e correção do nome
            String nomeProduto = produtoModificado.getNome();
            while (nomeProduto == null || nomeProduto.length() < 1) {
                System.out.println("O nome do produto deve ter pelo menos 1 caractere.");
                System.out.print("Por favor, refaça a entrada do nome: ");
                nomeProduto = new java.util.Scanner(System.in).nextLine();
            }
            existente.setNome(nomeProduto);

            // Verifica e atualiza o preço
            if (produtoModificado.getPreco() <= 0) {
                throw new IllegalArgumentException("O preço precisa ser maior que zero.");
            }
            existente.setPreco(produtoModificado.getPreco());

            // Verifica e atualiza a quantidade
            if (produtoModificado.getQuantidadeEstoque() < 0) {
                throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
            }
            existente.setQuantidadeEstoque(produtoModificado.getQuantidadeEstoque());

            // Verifica e atualiza a categoria
            if (produtoModificado.getCategoria() == null || produtoModificado.getCategoria().isEmpty()) {
                throw new IllegalArgumentException("A categoria é obrigatória.");
            }
            existente.setCategoria(produtoModificado.getCategoria());

            System.out.println("Produto atualizado com sucesso!");
            return true;
        }
        return false;
    }

    // Remove um produto pelo ID
    public boolean excluirProduto(int id) {
        Produto produto = localizarPorId(id);
        if (produto != null) {
            listaProdutos.remove(produto);
            System.out.println("Produto removido com sucesso!");
            return true;
        }
        return false;
    }

    // Valida os atributos do produto
    private void verificarProduto(Produto produto) {
        // Validação de nome (deve ter pelo menos 1 caractere)
        while (produto.getNome() == null || produto.getNome().length() < 1) {
            System.out.println("O nome do produto deve ter pelo menos 1 caractere.");
            System.out.print("Por favor, refaça a entrada do nome: ");
            produto.setNome(new java.util.Scanner(System.in).nextLine());
        }

        // Verifica se o nome tem pelo menos 2 caracteres
        if (produto.getNome().length() < 2) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 2 caracteres.");
        }

        // Verificação de preço
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço precisa ser maior que zero.");
        }

        // Verificação de quantidade
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }

        // Verificação de categoria
        if (produto.getCategoria() == null || produto.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
    }

    // Busca produtos pelo nome (case insensitive)
    public List<Produto> localizarPorNome(String nome) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : listaProdutos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    // Busca produtos pela categoria (case insensitive)
    public List<Produto> localizarPorCategoria(String categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : listaProdutos) {
            if (produto.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(produto);
            }
        }
        return resultado;
    }
}
