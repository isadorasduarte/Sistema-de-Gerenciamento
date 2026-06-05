    /*
    * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
    */
    package Pck_model;

    public class Model_Funcionario {
        private Integer id;
        private String nome;
        private String telefone;
        private String cep;
        private String perfil;
        private String email;
        private String senha;
        private boolean ativo;     
        private int idUsuario;     

        public Model_Funcionario() {}

    
        public Model_Funcionario(int id, String nome, String telefone, String cep,
                                String perfil, boolean ativo, String email, String senha) {
            this.id = id;
            this.nome = nome;
            this.telefone = telefone;
            this.cep = cep;
            this.perfil = perfil;
            this.ativo = ativo;
            this.email = email;
            this.senha = senha;
        }

    
        public Integer getId() { 
            return id; 
        }

        public void setId(Integer id) { 
            this.id = id; 
        }

        public String getNome() { 
            return nome; 
        }

        public void setNome(String nome) { 
            this.nome = nome; 
        }

        public String getTelefone() { 
            return telefone; 
        }

        public void setTelefone(String telefone) { 
            this.telefone = telefone; 
        }

        public String getCep() { 
            return cep; 
        }

        public void setCep(String cep) { 
            this.cep = cep; 
        }

        public String getPerfil() { 
            return perfil; 
        }

        public void setPerfil(String perfil) { 
            this.perfil = perfil; 
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

        public boolean isAtivo() { 
            return ativo; 
        }

        public void setAtivo(boolean ativo) { 
            this.ativo = ativo; 
        }

        public int getIdUsuario() { 
            return idUsuario; 
        }

        public void setIdUsuario(int idUsuario) { 
            this.idUsuario = idUsuario; 
        }
    }
