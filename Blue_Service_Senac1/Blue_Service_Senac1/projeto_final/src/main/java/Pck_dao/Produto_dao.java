package Pck_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pck_model.Model_Produto;

public class Produto_dao {

    public void inserir(Model_Produto p) throws SQLException {
        String sql = "INSERT INTO PRODUTO_05 (A05_nome, A05_descricao, A05_preco, A05_qtd_estoque) VALUES (?, ?, ?, ?)";
        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDouble(3, p.getPreco());
            ps.setInt(4, p.getEstoque());
            ps.executeUpdate();
        }
    }

    public List<Model_Produto> listar() throws SQLException {
        List<Model_Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTO_05";
        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Model_Produto p = new Model_Produto();
                p.setId(rs.getInt("A05_id_produto"));
                p.setNome(rs.getString("A05_nome"));
                p.setDescricao(rs.getString("A05_descricao"));
                p.setPreco(rs.getDouble("A05_preco"));
                p.setEstoque(rs.getInt("A05_qtd_estoque"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void editar(Model_Produto p) throws SQLException {
        String sql = "UPDATE PRODUTO_05 SET A05_nome=?, A05_descricao=?, A05_preco=?, A05_qtd_estoque=? WHERE A05_id_produto=?";
        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDouble(3, p.getPreco());
            ps.setInt(4, p.getEstoque());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
   
    String sqlItens = "DELETE FROM ITEM_PEDIDO_06 WHERE A05_id_produto = ?";
    try (Connection conn = dao_conexao.getConnection();
         PreparedStatement ps = conn.prepareStatement(sqlItens)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    }

   
    String sqlProduto = "DELETE FROM PRODUTO_05 WHERE A05_id_produto = ?";
    try (Connection conn = dao_conexao.getConnection();
         PreparedStatement ps = conn.prepareStatement(sqlProduto)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
}