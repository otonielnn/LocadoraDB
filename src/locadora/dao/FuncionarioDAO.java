package locadora.dao;

import locadora.model.Funcionario;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void inserir(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario (cpf, nome, endereco, salario, cargo, cnpj_filial) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setObject(4, funcionario.getSalario());
            stmt.setString(5, funcionario.getCargo());
            stmt.setString(6, funcionario.getCnpjFilial());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionario SET nome = ?, endereco = ?, salario = ?, cargo = ?, cnpj_filial = ? WHERE cpf = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEndereco());
            stmt.setObject(3, funcionario.getSalario());
            stmt.setString(4, funcionario.getCargo());
            stmt.setString(5, funcionario.getCnpjFilial());
            stmt.setString(6, funcionario.getCpf());
            stmt.executeUpdate();
        }
    }

    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE cpf = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE cpf = ?";
        Funcionario funcionario = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = construirFuncionario(rs);
                }
            }
        }
        return funcionario;
    }

    public List<Funcionario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionarios.add(construirFuncionario(rs));
            }
        }
        return funcionarios;
    }

    public List<Funcionario> listarPorFilial(String cnpjFilial) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE cnpj_filial = ?";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpjFilial);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funcionarios.add(construirFuncionario(rs));
                }
            }
        }
        return funcionarios;
    }

    public List<Funcionario> listarPorCargo(String cargo) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE cargo = ?";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cargo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funcionarios.add(construirFuncionario(rs));
                }
            }
        }
        return funcionarios;
    }

    private Funcionario construirFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
            rs.getString("cpf"),
            rs.getString("nome"),
            rs.getString("endereco"),
            rs.getObject("salario", Double.class),
            rs.getString("cargo"),
            rs.getString("cnpj_filial")
        );
    }
}
