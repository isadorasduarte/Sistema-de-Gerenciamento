package com.senac.blue_service.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Pck_dao.Produto_dao;
import Pck_model.Model_Produto;

@Controller
public class Produto_Control {

    Produto_dao dao = new Produto_dao();

    
    @PostMapping("/produto/salvar")
    public String salvar(
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam double preco,
            @RequestParam(defaultValue = "0") int estoque) {
        try {
            Model_Produto p = new Model_Produto();
            p.setNome(nome);
            p.setDescricao(descricao);
            p.setPreco(preco);
            p.setEstoque(estoque);
            dao.inserir(p);
        } catch (Exception e) {
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
        return "redirect:/funcionario/listar";
    }

    
    @PostMapping("/produto/editar")
    public String editar(
            @RequestParam int id,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam double preco,
            @RequestParam(defaultValue = "0") int estoque) {
        try {
            Model_Produto p = new Model_Produto();
            p.setId(id);
            p.setNome(nome);
            p.setDescricao(descricao);
            p.setPreco(preco);
            p.setEstoque(estoque);
            dao.editar(p);
        } catch (Exception e) {
            System.out.println("Erro ao editar produto: " + e.getMessage());
        }
        return "redirect:/funcionario/listar";
    }

    
    @GetMapping("/produto/remover")
    public String remover(@RequestParam int id) {
        try {
            dao.remover(id);
        } catch (Exception e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
        return "redirect:/funcionario/listar";
    }

    
    @GetMapping("/produto/listar")
    public String listar(Model model) {
        try {
            model.addAttribute("listaProduto", dao.listar());
        } catch (Exception e) {
            model.addAttribute("listaProduto", new ArrayList<>());
        }
        return "redirect:/funcionario/listar";
    }
}