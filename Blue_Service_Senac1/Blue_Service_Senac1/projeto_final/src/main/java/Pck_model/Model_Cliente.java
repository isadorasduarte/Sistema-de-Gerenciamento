/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_model;

/**
 *
 * @author isado
 */
public class Model_Cliente {
    
private int idCliente;
    private String nome;
    private String cep;
    private String cpf;
    private String telefone;
    private Model_Usuario usuario;

    public Model_Cliente() {}

    public Model_Cliente(int idCliente, String nome, String cep, String cpf, String telefone, Model_Usuario usuario) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cep = cep;
        this.cpf = cpf;
        this.telefone = telefone;
        this.usuario = usuario;
    }

    public int getIdCliente() { 
        return idCliente; 
    }
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente;
    }

    public String getNome() { 
        return nome;
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getCep() { 
        return cep; }
    
    public void setCep(String cep) { 
        this.cep = cep; 
    }

    public String getCpf() { 
        return cpf; 
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf; 
    }

    public String getTelefone() { 
        return telefone; 
    }
    
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }

    public Model_Usuario getUsuario() { 
        return usuario; 
    }
    
    public void setUsuario(Model_Usuario usuario) { 
        this.usuario = usuario;
    }
    @Override
public String toString() {
    return nome;
}

}

