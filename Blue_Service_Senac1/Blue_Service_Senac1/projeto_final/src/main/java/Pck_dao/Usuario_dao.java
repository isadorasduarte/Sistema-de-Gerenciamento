/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pck_dao;

import java.sql.*;
import Pck_model.Model_Usuario;

public class Usuario_dao {

    public void inserir(Model_Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIO_03 (A03_email, A03_senha) VALUES (?, ?)";
        try (Connection con = dao_conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt(1));
            }
        }
    }

    public Model_Usuario autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM USUARIO_03 WHERE A03_email = ? AND A03_senha = ?";
        try (Connection con = dao_conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Model_Usuario usuario = new Model_Usuario();
                usuario.setIdUsuario(rs.getInt("A03_id_usuario"));
                usuario.setEmail(rs.getString("A03_email"));
                usuario.setSenha(rs.getString("A03_senha"));
                return usuario;
            }
        }
        return null;
    }
}
