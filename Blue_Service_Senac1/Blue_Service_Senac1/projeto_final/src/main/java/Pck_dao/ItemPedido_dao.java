/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_dao;

import Pck_model.Model_ItemPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemPedido_dao {

    public void salvarItens(List<Model_ItemPedido> itens, int idPedido) throws SQLException {
        Connection conn = dao_conexao.getConnection();
        String sql = "INSERT INTO ITEM_PEDIDO_06 (A06_quantidade, A06_preco_unitario, A06_subtotal, A04_id_pedido, A05_id_produto) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);

        for (Model_ItemPedido item : itens) {
            pst.setInt(1, item.getQuantidade());
            pst.setBigDecimal(2, item.getPrecoUnitario());
            pst.setBigDecimal(3, item.getSubtotal());
            pst.setInt(4, idPedido);
            pst.setInt(5, item.getIdProduto());
            pst.addBatch();
        }

        pst.executeBatch();
        pst.close();
        conn.close();
    }
}
