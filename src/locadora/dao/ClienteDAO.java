package locadora.dao;

import locadora.model.Cliente;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (cpf, nome, data_nascimento, cnh, avaliacao) " +
                "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setObject(3, cliente.getDataNascimento());
            stmt.setString(4, cliente.getCnh());
            stmt.setObject(5, cliente.getAvaliacao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, data_nascimento = ?, cnh = ?, avaliacao = ? WHERE cpf = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setObject(2, cliente.getDataNascimento());
            stmt.setString(3, cliente.getCnh());
            stmt.setObject(4, cliente.getAvaliacao());
            stmt.setString(5, cliente.getCpf());
            stmt.executeUpdate();
        }
    }

    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM cliente WHERE cpf = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        Cliente cliente = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getObject("data_nascimento", LocalDate.class),
                        rs.getString("cnh"),
                        rs.getObject("avaliacao", Double.class)
                    );
                }
            }
        }
        return cliente;
    }

    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getObject("data_nascimento", LocalDate.class),
                    rs.getString("cnh"),
                    rs.getObject("avaliacao", Double.class)
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public List<Cliente> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nome ILIKE ?";
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getObject("data_nascimento", LocalDate.class),
                        rs.getString("cnh"),
                        rs.getObject("avaliacao", Double.class)
                    );
                    clientes.add(cliente);
                }
            }
        }
        return clientes;
    }
}
