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
import Pck_dao.Usuario_dao;
import Pck_model.Model_Cliente;
import Pck_model.Model_Funcionario;
import Pck_model.Model_Usuario;

@Controller
public class Usuario_Control {

    Usuario_dao     usuarioDAO     = new Usuario_dao();
    Cliente_dao     clienteDAO     = new Cliente_dao();
    Funcionario_dao funcionarioDAO = new Funcionario_dao();

    @GetMapping("/login")
    public String abrirLogin() { return "login"; }

    @GetMapping("/cadastro")
    public String abrirCadastro() { return "cadastro"; }

    @GetMapping("/")
    public String inicio() { return "redirect:/login"; }

    
    @PostMapping("/usuario/cadastrar")
    public String cadastrar(String email, String senha, String nome,
                            String cep, String cpf, String telefone) {
        try {
            cpf      = cpf.replaceAll("[^0-9]", "");
            cep      = cep.replaceAll("[^0-9]", "");
            telefone = telefone.replaceAll("[^0-9]", "");
            Model_Usuario u = new Model_Usuario(0, email, senha);
            usuarioDAO.inserir(u);
            clienteDAO.inserir(new Model_Cliente(0, nome, cep, cpf, telefone, u));
        } catch (Exception e) {
            System.out.println("Erro cadastro: " + e.getMessage());
        }
        return "redirect:/cadastro?sucesso";
    }

    
@PostMapping("/usuario/login")
public String login(String email, String senha, Model model) {
    try {
        Model_Usuario usuario = usuarioDAO.autenticar(email, senha);
        if (usuario != null) {
            Model_Cliente cliente = clienteDAO.buscarPorUsuario(usuario);

           
            System.out.println("=== LOGIN CLIENTE ===");
            System.out.println("Usuario ID: " + usuario.getIdUsuario());
            System.out.println("Cliente: " + (cliente != null ? cliente.getIdCliente() + " / " + cliente.getNome() : "NULL"));

            model.addAttribute("cliente",  cliente);
            model.addAttribute("idCliente", cliente != null ? cliente.getIdCliente() : 0);

            Produto_dao produtoDAO = new Produto_dao();
            model.addAttribute("listaProduto", produtoDAO.listar());

            if (cliente != null) {
                Pedido_dao pedidoDAO = new Pedido_dao();
                model.addAttribute("listaPedido", pedidoDAO.listarPorCliente(cliente.getIdCliente()));
            } else {
                model.addAttribute("listaPedido", new ArrayList<>());
            }

            return "home-cliente";
        }
    } catch (Exception e) {
        System.out.println("Erro login: " + e.getMessage());
    }
    return "redirect:/login?erro";
}

    
    @GetMapping("/cliente/home")
    public String homeCliente(@RequestParam int id, Model model) {
        try {
            Produto_dao produtoDAO = new Produto_dao();
            Pedido_dao  pedidoDAO  = new Pedido_dao();
            model.addAttribute("listaProduto", produtoDAO.listar());
            model.addAttribute("listaPedido",  pedidoDAO.listarPorCliente(id));
            model.addAttribute("idCliente",    id);
        } catch (Exception e) {
            System.out.println("Erro home cliente: " + e.getMessage());
            model.addAttribute("listaProduto", new ArrayList<>());
            model.addAttribute("listaPedido",  new ArrayList<>());
            model.addAttribute("idCliente",    id);
        }
        return "home-cliente";
    }

    
    @PostMapping("/funcionario/login")
public String loginFuncionario(String email, String senha, Model model) {
    try {
        Model_Funcionario f = funcionarioDAO.autenticarFuncionario(email, senha);
        if (f != null && f.isAtivo()) {
            model.addAttribute("funcionario", f);

            Produto_dao produtoDAO = new Produto_dao();
            Pedido_dao  pedidoDAO  = new Pedido_dao();
            List<Pck_model.Model_Pedido> pedidos = pedidoDAO.listar();

            model.addAttribute("listaProduto", produtoDAO.listar());
            model.addAttribute("listaPedido",  pedidos);

            
            java.math.BigDecimal faturamento = pedidos.stream()
                .filter(p -> p.getValorTotal() != null)
                .map(p -> p.getValorTotal())
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            model.addAttribute("faturamentoTotal", faturamento);

                        
            java.math.BigDecimal valorEstoque = produtoDAO.listar().stream()
                .map(p -> java.math.BigDecimal.valueOf(p.getPreco())
                    .multiply(java.math.BigDecimal.valueOf(p.getEstoque())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            model.addAttribute("valorEstoque", valorEstoque);

            
            java.util.Map<String, Long> contagem = pedidos.stream()
                .filter(p -> p.getProdutos() != null)
                .flatMap(p -> p.getProdutos().stream())
                .collect(java.util.stream.Collectors.groupingBy(nome -> nome, java.util.stream.Collectors.counting()));

            List<java.util.Map.Entry<String, Long>> top3 = contagem.entrySet().stream()
                .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .collect(java.util.stream.Collectors.toList());
            model.addAttribute("top3Produtos", top3);

            try { model.addAttribute("listaFuncionario", funcionarioDAO.listar()); }
            catch (Exception e) { model.addAttribute("listaFuncionario", new java.util.ArrayList<>()); }
            try { model.addAttribute("listaCliente", clienteDAO.listarClientes()); }
            catch (Exception e) { model.addAttribute("listaCliente", new java.util.ArrayList<>()); }

            if ("admin".equalsIgnoreCase(f.getPerfil())) return "admin-dashboard";
            return "funcionario-dashboard";
        }
    } catch (Exception e) {
        System.out.println("Erro login funcionario: " + e.getMessage());
    }
    return "redirect:/login?erro";
}
}
