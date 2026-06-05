/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Pck_model.Model_Pedido;

public class FazerPedido_dao {

    public int salvarPedido(Model_Pedido pedido) throws SQLException {

    System.out.println("Entrou em salvarPedido()");
    System.out.println("Cliente: " + pedido.getIdCliente());
    System.out.println("Valor: " + pedido.getValorTotal());

    Connection conn = dao_conexao.getConnection();

    String sql =
        "INSERT INTO PEDIDO_04 (A04_data_pedido, A04_origem, A04_valor_total, A01_id_cliente) VALUES (?, ?, ?, ?)";

    PreparedStatement pst =
        conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

    pst.setDate(1, pedido.getDataPedido());
    pst.setString(2, pedido.getOrigem());
    pst.setBigDecimal(3, pedido.getValorTotal());
    pst.setInt(4, pedido.getIdCliente());

    int linhas = pst.executeUpdate();

    System.out.println("Linhas inseridas: " + linhas);

    ResultSet rs = pst.getGeneratedKeys();

    int idGerado = 0;

    if (rs.next()) {
        idGerado = rs.getInt(1);
        System.out.println("Pedido gerado: " + idGerado);
    }

    return idGerado;
}
}