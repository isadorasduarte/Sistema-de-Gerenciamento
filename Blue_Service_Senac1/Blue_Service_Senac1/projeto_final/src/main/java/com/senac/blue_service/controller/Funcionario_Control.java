package com.senac.blue_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Pck_dao.Cliente_dao;
import Pck_dao.Funcionario_dao;
import Pck_dao.Pedido_dao;
import Pck_dao.Produto_dao;
import Pck_model.Model_Funcionario;

@Controller
public class Funcionario_Control {

    Funcionario_dao funcionarioDAO = new Funcionario_dao();

private void carregarAdmin(Model model) {
    try { model.addAttribute("listaFuncionario", funcionarioDAO.listar()); }
    catch (Exception e) { model.addAttribute("listaFuncionario", new ArrayList<>()); }

    try {
        List<Pck_model.Model_Produto> produtos = new Produto_dao().listar();
        model.addAttribute("listaProduto", produtos);

        
        java.math.BigDecimal valorEstoque = produtos.stream()
            .map(p -> java.math.BigDecimal.valueOf(p.getPreco())
                .multiply(java.math.BigDecimal.valueOf(p.getEstoque())))
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("valorEstoque", valorEstoque);

    } catch (Exception e) {
        model.addAttribute("listaProduto", new ArrayList<>());
        model.addAttribute("valorEstoque", java.math.BigDecimal.ZERO);
    }

    try { model.addAttribute("listaCliente", new Cliente_dao().listarClientes()); }
    catch (Exception e) { model.addAttribute("listaCliente", new ArrayList<>()); }

    try {
        List<Pck_model.Model_Pedido> pedidos = new Pedido_dao().listar();
        model.addAttribute("listaPedido", pedidos);

        java.math.BigDecimal faturamento = pedidos.stream()
            .filter(p -> p.getValorTotal() != null)
            .map(Pck_model.Model_Pedido::getValorTotal)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("faturamentoTotal", faturamento);

        
        java.util.Map<String, Long> contagemProdutos = pedidos.stream()
            .filter(p -> p.getProdutos() != null)
            .flatMap(p -> p.getProdutos().stream())
            .collect(java.util.stream.Collectors.groupingBy(
                nome -> nome, java.util.stream.Collectors.counting()));

        List<java.util.Map.Entry<String, Long>> top3 = contagemProdutos.entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .collect(java.util.stream.Collectors.toList());

        model.addAttribute("top3Produtos", top3);

    } catch (Exception e) {
        model.addAttribute("listaPedido", new ArrayList<>());
        model.addAttribute("faturamentoTotal", java.math.BigDecimal.ZERO);
        model.addAttribute("top3Produtos", new ArrayList<>());
    }
}

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        carregarAdmin(model);
        return "admin-dashboard";
    }

    @GetMapping("/funcionario/listar")
    public String listar(Model model) {
        carregarAdmin(model);
        return "admin-dashboard";
    }

    @PostMapping("/funcionario/salvar")
    public String salvar(Model_Funcionario f) {
        try { funcionarioDAO.inserirFuncionario(f); }
        catch (Exception e) { System.out.println("Erro salvar func: " + e.getMessage()); }
        return "redirect:/funcionario/listar";
    }

    @PostMapping("/funcionario/editar")
    public String editar(Model_Funcionario f) {
        try { funcionarioDAO.editarFuncionario(f); }
        catch (Exception e) { System.out.println("Erro editar func: " + e.getMessage()); }
        return "redirect:/funcionario/listar";
    }

    @GetMapping("/funcionario/remover")
    public String remover(@RequestParam int id) {
        try { funcionarioDAO.removerFuncionario(id); }
        catch (Exception e) { System.out.println("Erro remover func: " + e.getMessage()); }
        return "redirect:/funcionario/listar";
    }

    @GetMapping("/funcionario/toggle")
    public String toggle(@RequestParam int id) {
        try {
            List<Model_Funcionario> lista = funcionarioDAO.listar();
            for (Model_Funcionario f : lista) {
                if (f.getId() == id) {
                    funcionarioDAO.alterarStatusFuncionario(id, !f.isAtivo());
                    break;
                }
            }
        } catch (Exception e) { System.out.println("Erro toggle: " + e.getMessage()); }
        return "redirect:/funcionario/listar";
    }
}
