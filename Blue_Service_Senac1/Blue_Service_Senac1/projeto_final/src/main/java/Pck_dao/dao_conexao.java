package Pck_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dao_conexao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/blue_service?useSSL=false&allowPublicKeyRetrieval=true";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}