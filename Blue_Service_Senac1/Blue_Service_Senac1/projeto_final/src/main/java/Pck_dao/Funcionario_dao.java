package Pck_dao;

import Pck_model.Model_Funcionario;
import java.sql.*;
import java.util.*;
public class Funcionario_dao {

    public void inserirFuncionario(Model_Funcionario f) throws SQLException {
    Connection conn = dao_conexao.getConnection();
    CallableStatement stmt = conn.prepareCall("{CALL proc_cadastrar_funcionario(?, ?, ?, ?, ?, ?, ?)}");

    stmt.setString(1, f.getNome());
    stmt.setString(2, f.getTelefone());
    stmt.setString(3, f.getCep());
    stmt.setString(4, f.getPerfil());
    stmt.setString(5, f.getEmail());
    stmt.setString(6, f.getSenha());
    stmt.setBoolean(7, f.isAtivo());

    stmt.execute();
    stmt.close();
    conn.close();
}
public List<Model_Funcionario> listar() throws SQLException {

    List<Model_Funcionario> lista = new ArrayList<>();

    String sql = """
        SELECT
            f.A02_id_funcionario,
            f.A02_nome,
            f.A02_telefone,
            f.A02_cep,
            f.A02_tipo_perfil,
            f.A02_ativo,
            u.A03_email
        FROM FUNCIONARIO_02 f
        INNER JOIN USUARIO_03 u
            ON f.A03_id_usuario = u.A03_id_usuario
    """;

    try (
        Connection conn = dao_conexao.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            Model_Funcionario f = new Model_Funcionario();

            f.setId(rs.getInt("A02_id_funcionario"));
            f.setNome(rs.getString("A02_nome"));
            f.setTelefone(rs.getString("A02_telefone"));
            f.setCep(rs.getString("A02_cep"));
            f.setPerfil(rs.getString("A02_tipo_perfil"));
            f.setAtivo(rs.getBoolean("A02_ativo"));
            f.setEmail(rs.getString("A03_email"));

            lista.add(f);
        }
    }

    return lista;
}
 public void editarFuncionario(Model_Funcionario f) throws SQLException {
    Connection conn = dao_conexao.getConnection();
    CallableStatement stmt = conn.prepareCall("{CALL proc_editar_funcionario(?, ?, ?, ?, ?, ?, ?, ?)}");

    stmt.setInt(1, f.getId());
    stmt.setString(2, f.getNome());
    stmt.setString(3, f.getTelefone());
    stmt.setString(4, f.getCep());
    stmt.setString(5, f.getPerfil());
    stmt.setString(6, f.getEmail());
    stmt.setString(7, f.getSenha());
    stmt.setBoolean(8, f.isAtivo());

    stmt.execute();
    stmt.close();
    conn.close();
}

    public void removerFuncionario(int id) throws SQLException {
        Connection conn = dao_conexao.getConnection();
        CallableStatement stmt = conn.prepareCall("{CALL proc_remover_funcionario(?)}");
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
        conn.close();
    }

    public boolean alterarStatusFuncionario(int id, boolean ativo) throws SQLException {
    String sql = "UPDATE FUNCIONARIO_02 SET A02_ativo = ? WHERE A02_id_funcionario = ?";

    try (Connection conn = dao_conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setBoolean(1, ativo);
        stmt.setInt(2, id);

        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0; 
    }
}
    public Model_Funcionario autenticarFuncionario(String email, String senha) {
        String sql = """
            SELECT f.A02_id_funcionario, f.A02_nome, f.A02_telefone, f.A02_cep,
                   f.A02_tipo_perfil, f.A02_ativo, f.A03_id_usuario, u.A03_email
            FROM FUNCIONARIO_02 f
            JOIN USUARIO_03 u ON f.A03_id_usuario = u.A03_id_usuario
            WHERE u.A03_email = ? AND u.A03_senha = ? AND f.A02_ativo = TRUE
        """;

        try (Connection conn = dao_conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Model_Funcionario f = new Model_Funcionario();
                f.setId(rs.getInt("A02_id_funcionario"));
                f.setNome(rs.getString("A02_nome"));
                f.setTelefone(rs.getString("A02_telefone"));
                f.setCep(rs.getString("A02_cep"));
                f.setPerfil(rs.getString("A02_tipo_perfil"));
                f.setAtivo(rs.getBoolean("A02_ativo"));
                f.setIdUsuario(rs.getInt("A03_id_usuario"));
                f.setEmail(rs.getString("A03_email"));
                return f;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar funcionário: " + e.getMessage());
        }

        return null;
    }
}



