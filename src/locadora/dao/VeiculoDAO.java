package locadora.dao;

import locadora.model.Veiculo;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public void inserir(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo (placa, tipo, modelo, ano, preco_diaria, kmanterior, alugado, historico, cnpj_filial) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getTipo());
            stmt.setString(3, veiculo.getModelo());
            stmt.setObject(4, veiculo.getAno());
            stmt.setObject(5, veiculo.getPrecoDiaria());
            stmt.setObject(6, veiculo.getKmAnterior());
            stmt.setObject(7, veiculo.isAlugado());
            stmt.setString(8, veiculo.getHistorico());
            stmt.setString(9, veiculo.getCnpjFilial());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculo SET tipo = ?, modelo = ?, ano = ?, preco_diaria = ?, " +
                "kmanterior = ?, alugado = ?, historico = ?, cnpj_filial = ? WHERE placa = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getTipo());
            stmt.setString(2, veiculo.getModelo());
            stmt.setObject(3, veiculo.getAno());
            stmt.setObject(4, veiculo.getPrecoDiaria());
            stmt.setObject(5, veiculo.getKmAnterior());
            stmt.setObject(6, veiculo.isAlugado());
            stmt.setString(7, veiculo.getHistorico());
            stmt.setString(8, veiculo.getCnpjFilial());
            stmt.setString(9, veiculo.getPlaca());
            stmt.executeUpdate();
        }
    }

    public void deletar(String placa) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE placa = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.executeUpdate();
        }
    }

    public Veiculo buscarPorPlaca(String placa) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE placa = ?";
        Veiculo veiculo = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    veiculo = construirVeiculo(rs);
                }
            }
        }
        return veiculo;
    }

    public List<Veiculo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM veiculo";
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                veiculos.add(construirVeiculo(rs));
            }
        }
        return veiculos;
    }

    public List<Veiculo> listarDisponiveis() throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE alugado = FALSE";
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                veiculos.add(construirVeiculo(rs));
            }
        }
        return veiculos;
    }

    public List<Veiculo> listarPorFilial(String cnpjFilial) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE cnpj_filial = ?";
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpjFilial);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    veiculos.add(construirVeiculo(rs));
                }
            }
        }
        return veiculos;
    }

    private Veiculo construirVeiculo(ResultSet rs) throws SQLException {
        return new Veiculo(
            rs.getString("placa"),
            rs.getString("tipo"),
            rs.getString("modelo"),
            rs.getObject("ano", Integer.class),
            rs.getObject("preco_diaria", Double.class),
            rs.getObject("kmanterior", Double.class),
            rs.getObject("alugado", Boolean.class),
            rs.getString("historico"),
            rs.getString("cnpj_filial")
        );
    }
}
