package Pck_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Pck_model.Model_Pedido;

public class Pedido_dao {

 
    public List<Model_Pedido> listar() {
        List<Model_Pedido> lista = new ArrayList<>();
        String sqlPedidos  = "SELECT p.*, c.A01_nome AS nomeCliente " +
                             "FROM PEDIDO_04 p " +
                             "LEFT JOIN CLIENTE_01 c ON p.A01_id_cliente = c.A01_id_cliente " +
                             "ORDER BY p.A04_id_pedido DESC";
        String sqlProdutos = "SELECT pr.A05_nome FROM ITEM_PEDIDO_06 ip " +
                             "INNER JOIN PRODUTO_05 pr ON ip.A05_id_produto = pr.A05_id_produto " +
                             "WHERE ip.A04_id_pedido = ?";
        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPedidos);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Model_Pedido pedido = new Model_Pedido();
                pedido.setIdPedido(rs.getInt("A04_id_pedido"));
                pedido.setDataPedido(rs.getDate("A04_data_pedido"));
                pedido.setOrigem(rs.getString("A04_origem"));
                pedido.setValorTotal(rs.getBigDecimal("A04_valor_total"));
                pedido.setIdCliente(rs.getInt("A01_id_cliente"));
                pedido.setNomeCliente(rs.getString("nomeCliente"));
                List<String> produtos = new ArrayList<>();
                try (PreparedStatement stmtProd = conn.prepareStatement(sqlProdutos)) {
                    stmtProd.setInt(1, pedido.getIdPedido());
                    try (ResultSet rsProd = stmtProd.executeQuery()) {
                        while (rsProd.next()) produtos.add(rsProd.getString("A05_nome"));
                    }
                }
                pedido.setProdutos(produtos);
                lista.add(pedido);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    
    public List<Model_Pedido> listarPorCliente(int idCliente) {
        List<Model_Pedido> lista = new ArrayList<>();
        String sqlPedidos  = "SELECT * FROM PEDIDO_04 WHERE A01_id_cliente = ? ORDER BY A04_id_pedido DESC";
        String sqlProdutos = "SELECT pr.A05_nome FROM ITEM_PEDIDO_06 ip " +
                             "INNER JOIN PRODUTO_05 pr ON ip.A05_id_produto = pr.A05_id_produto " +
                             "WHERE ip.A04_id_pedido = ?";
        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPedidos)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Model_Pedido pedido = new Model_Pedido();
                    pedido.setIdPedido(rs.getInt("A04_id_pedido"));
                    pedido.setDataPedido(rs.getDate("A04_data_pedido"));
                    pedido.setOrigem(rs.getString("A04_origem"));
                    pedido.setValorTotal(rs.getBigDecimal("A04_valor_total"));
                    pedido.setIdCliente(rs.getInt("A01_id_cliente"));
                    List<String> produtos = new ArrayList<>();
                    try (PreparedStatement stmtProd = conn.prepareStatement(sqlProdutos)) {
                        stmtProd.setInt(1, pedido.getIdPedido());
                        try (ResultSet rsProd = stmtProd.executeQuery()) {
                            while (rsProd.next()) produtos.add(rsProd.getString("A05_nome"));
                        }
                    }
                    pedido.setProdutos(produtos);
                    lista.add(pedido);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos do cliente: " + e.getMessage());
        }
        return lista;
    }
}
