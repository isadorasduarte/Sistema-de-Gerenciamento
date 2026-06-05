
package Pck_dao;
import java.sql.*;
import Pck_model.Model_Cliente;
import Pck_model.Model_Usuario;
import java.util.ArrayList;
import java.util.List;

public class Cliente_dao {

    public void inserir(Model_Cliente cliente) throws SQLException {
        String sql = "INSERT INTO CLIENTE_01 (A01_nome, A01_cep, A01_cpf, A01_telefone, A03_id_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = dao_conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCep());
            ps.setString(3, cliente.getCpf());
            ps.setString(4, cliente.getTelefone());
            ps.setInt(5, cliente.getUsuario().getIdUsuario());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt(1));
            }
        }
    }

    public Model_Cliente buscarPorUsuario(Model_Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM CLIENTE_01 WHERE A03_id_usuario = ?";
        try (Connection con = dao_conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdUsuario());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Model_Cliente cliente = new Model_Cliente();
                cliente.setIdCliente(rs.getInt("A01_id_cliente"));
                cliente.setNome(rs.getString("A01_nome"));
                cliente.setCep(rs.getString("A01_cep"));
                cliente.setCpf(rs.getString("A01_cpf"));
                cliente.setTelefone(rs.getString("A01_telefone"));
                cliente.setUsuario(usuario);
                return cliente;
            }
        }
        return null;
    }
   public List<Model_Cliente> listarClientes() throws SQLException {
    List<Model_Cliente> lista = new ArrayList<>();

    String sql = "SELECT A01_id_cliente, A01_nome, A01_telefone, A01_cpf FROM CLIENTE_01 ORDER BY A01_nome ASC";

    try (Connection conn = dao_conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Model_Cliente c = new Model_Cliente();
            c.setIdCliente(rs.getInt("A01_id_cliente"));
            c.setNome(rs.getString("A01_nome"));
            c.setTelefone(rs.getString("A01_telefone"));
            c.setCpf(rs.getString("A01_cpf"));
            lista.add(c);
        }
    }

    return lista;
}

}
