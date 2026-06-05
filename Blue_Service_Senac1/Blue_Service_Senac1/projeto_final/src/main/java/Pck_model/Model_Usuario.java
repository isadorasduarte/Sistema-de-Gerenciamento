package Pck_model;


public class Model_Usuario {
    private int idUsuario;
    private String email;
    private String senha;

    public Model_Usuario() {}

    public Model_Usuario(int idUsuario, String email, String senha) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario; 
    }
    
    public void setIdUsuario(int idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getSenha() { 
        return senha; 
    }
    
    public void setSenha(String senha) { 
        this.senha = senha;
    }
}
