package org.example.banco;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    // ==============================
    // DADOS DE CONEXÃO COM O BANCO
    // ==============================

    // URL de conexão com o banco de dados MySQL:
    // Formato: jdbc:mysql://<host>:<porta>/<nome_do_banco>
    private static final String URL = "jdbc:mysql://localhost:3306/testedb";

    // Nome do usuário do banco de dados (normalmente "root" no MySQL local)
    private static final String USUARIO = "root";

    // Senha do usuário (altere de acordo com sua configuração local do MySQL)
    private static final String SENHA = "123456";

    /**
     * Método responsável por estabelecer a conexão com o banco de dados.
     *
     * @return um objeto do tipo Connection já conectado ao banco
     * @throws Exception caso ocorra algum erro na conexão
     */
    public static Connection conectar() throws Exception {
        // Chama o DriverManager para criar uma conexão com base na URL, usuário e senha
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}