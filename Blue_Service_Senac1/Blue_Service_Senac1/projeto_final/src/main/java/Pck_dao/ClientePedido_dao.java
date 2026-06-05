/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Pck_model.Model_ClientePedido;
import Pck_model.Model_Pedido;


public class ClientePedido_dao {

    public List<Model_ClientePedido> listarClientesPedidos() throws SQLException {
        List<Model_ClientePedido> lista = new ArrayList<>();

        String sql = """
            SELECT c.A01_id_cliente, c.A01_nome, c.A01_cpf, c.A01_telefone,
                   p.A04_id_pedido, p.A04_data_pedido, p.A04_valor_total
            FROM CLIENTE_01 c
            LEFT JOIN PEDIDO_04 p ON c.A01_id_cliente = p.A01_id_cliente
            ORDER BY c.A01_nome, p.A04_data_pedido;
        """;

        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Model_ClientePedido obj = new Model_ClientePedido();
                obj.setIdCliente(rs.getInt("A01_id_cliente"));
                obj.setNomeCliente(rs.getString("A01_nome"));
                obj.setCpf(rs.getString("A01_cpf"));
                obj.setTelefone(rs.getString("A01_telefone"));
                obj.setIdPedido(rs.getInt("A04_id_pedido"));
                obj.setDataPedido(rs.getDate("A04_data_pedido"));
                obj.setValorTotal(rs.getBigDecimal("A04_valor_total"));
                lista.add(obj);
            }
        }
        return lista;
    }
    
    public List<Model_Pedido> listarPedidosPorCliente(int idCliente) throws SQLException {
        List<Model_Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDO_04 WHERE A01_id_cliente = ? ORDER BY A04_data_pedido DESC";

        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Model_Pedido p = new Model_Pedido();
                p.setIdPedido(rs.getInt("A04_id_pedido"));
                p.setDataPedido(rs.getDate("A04_data_pedido"));
                p.setValorTotal(rs.getBigDecimal("A04_valor_total"));
                p.setIdCliente(rs.getInt("A01_id_cliente"));
                lista.add(p);
            }
        }
        return lista;
    }
}

