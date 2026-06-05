package com.senac.blue_service.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Pck_dao.FazerPedido_dao;
import Pck_dao.ItemPedido_dao;
import Pck_model.Model_ItemPedido;
import Pck_model.Model_Pedido;

@Controller
public class Pedido_Control {

    @PostMapping("/pedido/finalizar")
    public String finalizar(
            @RequestParam int idCliente,
            @RequestParam(name = "idProduto")  List<Integer> idsProduto,
            @RequestParam(name = "quantidade") List<Integer> quantidades,
            @RequestParam(name = "preco")      List<Double>  precos,
            @RequestParam double total) {
        try {
            Model_Pedido pedido = new Model_Pedido();
            pedido.setIdCliente(idCliente);
            pedido.setDataPedido(Date.valueOf(LocalDate.now()));
            pedido.setOrigem("Online");
            pedido.setValorTotal(BigDecimal.valueOf(total));

            FazerPedido_dao fazerDAO = new FazerPedido_dao();
            int idPedido = fazerDAO.salvarPedido(pedido);

            List<Model_ItemPedido> itens = new ArrayList<>();
            for (int i = 0; i < idsProduto.size(); i++) {
                Model_ItemPedido item = new Model_ItemPedido();
                item.setIdProduto(idsProduto.get(i));
                item.setQuantidade(quantidades.get(i));
                item.setPrecoUnitario(BigDecimal.valueOf(precos.get(i)));
                item.setSubtotal(BigDecimal.valueOf(precos.get(i) * quantidades.get(i)));
                itens.add(item);
            }
            new ItemPedido_dao().salvarItens(itens, idPedido);
            System.out.println("Pedido #" + idPedido + " salvo com " + itens.size() + " itens.");
        } catch (Exception e) {
            System.out.println("Erro ao finalizar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/cliente/home?id=" + idCliente;
    }
}
