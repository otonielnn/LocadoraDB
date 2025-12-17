package locadora.dao;

import locadora.model.Filial;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilialDAO {

    public void inserir(Filial filial) throws SQLException {
        String sql = "INSERT INTO filial (cnpj, nome, endereco, telefone) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, filial.getCnpj());
            stmt.setString(2, filial.getNome());
            stmt.setString(3, filial.getEndereco());
            stmt.setString(4, filial.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Filial filial) throws SQLException {
        String sql = "UPDATE filial SET nome = ?, endereco = ?, telefone = ? WHERE cnpj = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, filial.getNome());
            stmt.setString(2, filial.getEndereco());
            stmt.setString(3, filial.getTelefone());
            stmt.setString(4, filial.getCnpj());
            stmt.executeUpdate();
        }
    }

    public void deletar(String cnpj) throws SQLException {
        String sql = "DELETE FROM filial WHERE cnpj = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.executeUpdate();
        }
    }

    public Filial buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT * FROM filial WHERE cnpj = ?";
        Filial filial = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    filial = construirFilial(rs);
                }
            }
        }
        return filial;
    }

    public List<Filial> listarTodas() throws SQLException {
        String sql = "SELECT * FROM filial";
        List<Filial> filiais = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                filiais.add(construirFilial(rs));
            }
        }
        return filiais;
    }

    public List<Filial> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM filial WHERE nome ILIKE ?";
        List<Filial> filiais = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    filiais.add(construirFilial(rs));
                }
            }
        }
        return filiais;
    }

    private Filial construirFilial(ResultSet rs) throws SQLException {
        return new Filial(
            rs.getString("cnpj"),
            rs.getString("nome"),
            rs.getString("endereco"),
            rs.getString("telefone")
        );
    }
}
