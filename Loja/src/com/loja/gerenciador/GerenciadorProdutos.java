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
            existente.setNome(produtoModificado.getNome());
            existente.setPreco(produtoModificado.getPreco());
            existente.setQuantidadeEstoque(produtoModificado.getQuantidadeEstoque());
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
        if (produto.getNome() == null || produto.getNome().length() < 2) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 2 caracteres.");
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço precisa ser maior que zero.");
        }
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
    }
}