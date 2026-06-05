/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_persistencia;

import Pck_dao.Cliente_dao;
import Pck_dao.Funcionario_dao;
import Pck_dao.Usuario_dao;
import Pck_model.Model_Cliente;
import Pck_model.Model_Funcionario;
import Pck_model.Model_Usuario;

public class Persistencia_Usuario {

    private Usuario_dao usuarioDAO = new Usuario_dao();
    private Cliente_dao clienteDAO = new Cliente_dao();
    private Funcionario_dao funcionarioDAO = new Funcionario_dao();

    
    public boolean cadastrar(String email, String senha,
                             String nome, String cep,
                             String cpf, String telefone) {
        try {
            Model_Usuario usuario = new Model_Usuario(0, email, senha);
            usuarioDAO.inserir(usuario);

            Model_Cliente cliente =
                new Model_Cliente(0, nome, cep, cpf, telefone, usuario);

            clienteDAO.inserir(cliente);

            return true;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

   
    public Model_Cliente autenticar(String email, String senha) {
        try {
            Model_Usuario usuario = usuarioDAO.autenticar(email, senha);
            if (usuario != null) {
                return clienteDAO.buscarPorUsuario(usuario);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }

    
    public Model_Funcionario autenticarFuncionario(String email, String senha) {
        try {
            Model_Funcionario f =
                funcionarioDAO.autenticarFuncionario(email, senha);

            if (f != null && f.isAtivo()) {
                return f;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}
