package org.example.dao;
import org.example.banco.Conexao;
import org.example.model.Usuario;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {


    /**
     * Método para inserir um novo usuário no banco de dados.
     */
    public void inserir(Usuario usuario) throws Exception {
        // Comando SQL com parâmetros (placeholders ?)
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        // Bloco try-with-resources garante que a conexão e o statement sejam fechados automaticamente
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Substitui os parâmetros da query pelos valores do objeto Usuario
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());

            // Executa a inserção no banco
            stmt.executeUpdate();
        }
    }

    /**
     * Método para listar todos os usuários cadastrados.
     */
    public List<Usuario> listar() throws Exception {
        // Cria uma lista que irá armazenar os usuários
        List<Usuario> lista = new ArrayList<>();

        // Comando SQL para buscar todos os registros da tabela
        String sql = "SELECT * FROM usuarios";

        // Abre conexão, cria o statement e executa a consulta
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Percorre cada linha do resultado
            while (rs.next()) {
                // Para cada linha, cria um objeto Usuario com os dados retornados
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                );

                // Adiciona o usuário à lista
                lista.add(u);
            }
        }

        // Retorna a lista de usuários
        return lista;
    }

    /**
     * Método para atualizar os dados de um usuário no banco.
     */
    public void atualizar(Usuario usuario) throws Exception {
        // Comando SQL com parâmetros para atualizar nome e email com base no ID
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";

        // Abre conexão e prepara o statement
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Substitui os parâmetros pelos valores atualizados do objeto
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());

            // Executa a atualização
            stmt.executeUpdate();
        }
    }

    /**
     * Método para excluir um usuário do banco com base no ID.
     */
    public void deletar(int id) throws Exception {
        // Comando SQL para deletar um registro específico
        String sql = "DELETE FROM usuarios WHERE id = ?";

        // Abre conexão e prepara o statement
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro ID
            stmt.setInt(1, id);

            // Executa o comando de exclusão
            stmt.executeUpdate(); // ESSENCIAL para a exclusão ocorrer de fato
        }
    }

    /**
     * Método para consultar um usuário pelo ID.
     * Retorna um objeto Usuario se encontrado, ou null se não existir.
     */
    public Usuario consultar(int id) throws Exception {
        // Comando SQL para buscar um usuário específico
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        // Abre a conexão e prepara o statement
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro ID
            stmt.setInt(1, id);

            // Executa a consulta e obtém o resultado
            try (ResultSet rs = stmt.executeQuery()) {
                // Se houver resultado, cria e retorna um novo objeto Usuario
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email")
                    );
                } else {
                    // Caso contrário, retorna null
                    return null;
                }
            }
        }
    }
}
