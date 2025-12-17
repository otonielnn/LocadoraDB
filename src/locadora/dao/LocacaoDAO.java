package locadora.dao;

import locadora.model.Locacao;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {

    public void inserir(Locacao locacao) throws SQLException {
        String sql = "INSERT INTO locacao (cpf_cliente, placa_veiculo, cnpj_filial_retirada, cnpj_filial_retorno, " +
                "data_inicial, data_final, combust_inicial, combust_final, km_percorrido, valor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, locacao.getCpfCliente());
            stmt.setString(2, locacao.getPlacaVeiculo());
            stmt.setString(3, locacao.getCnpjFilialRetirada());
            stmt.setString(4, locacao.getCnpjFilialRetorno());
            stmt.setObject(5, locacao.getDataInicial());
            stmt.setObject(6, locacao.getDataFinal());
            stmt.setObject(7, locacao.getCombustInicial());
            stmt.setObject(8, locacao.getCombustFinal());
            stmt.setObject(9, locacao.getKmPercorrido());
            stmt.setObject(10, locacao.getValor());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Locacao locacao) throws SQLException {
        String sql = "UPDATE locacao SET cpf_cliente = ?, placa_veiculo = ?, cnpj_filial_retirada = ?, " +
                "cnpj_filial_retorno = ?, data_inicial = ?, data_final = ?, combust_inicial = ?, " +
                "combust_final = ?, km_percorrido = ?, valor = ? WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, locacao.getCpfCliente());
            stmt.setString(2, locacao.getPlacaVeiculo());
            stmt.setString(3, locacao.getCnpjFilialRetirada());
            stmt.setString(4, locacao.getCnpjFilialRetorno());
            stmt.setObject(5, locacao.getDataInicial());
            stmt.setObject(6, locacao.getDataFinal());
            stmt.setObject(7, locacao.getCombustInicial());
            stmt.setObject(8, locacao.getCombustFinal());
            stmt.setObject(9, locacao.getKmPercorrido());
            stmt.setObject(10, locacao.getValor());
            stmt.setInt(11, locacao.getIdLocacao());
            stmt.executeUpdate();
        }
    }

    public void deletar(Integer idLocacao) throws SQLException {
        String sql = "DELETE FROM locacao WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLocacao);
            stmt.executeUpdate();
        }
    }

    public Locacao buscarPorId(Integer idLocacao) throws SQLException {
        String sql = "SELECT * FROM locacao WHERE id_locacao = ?";
        Locacao locacao = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLocacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    locacao = construirLocacao(rs);
                }
            }
        }
        return locacao;
    }

    public List<Locacao> listarTodos() throws SQLException {
        String sql = "SELECT * FROM locacao";
        List<Locacao> locacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                locacoes.add(construirLocacao(rs));
            }
        }
        return locacoes;
    }

    public List<Locacao> listarPorCliente(String cpfCliente) throws SQLException {
        String sql = "SELECT * FROM locacao WHERE cpf_cliente = ?";
        List<Locacao> locacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    locacoes.add(construirLocacao(rs));
                }
            }
        }
        return locacoes;
    }

    public List<Locacao> listarPorVeiculo(String placaVeiculo) throws SQLException {
        String sql = "SELECT * FROM locacao WHERE placa_veiculo = ?";
        List<Locacao> locacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placaVeiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    locacoes.add(construirLocacao(rs));
                }
            }
        }
        return locacoes;
    }

    public List<Locacao> listarAbertas() throws SQLException {
        String sql = "SELECT * FROM locacao WHERE data_final IS NULL";
        List<Locacao> locacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                locacoes.add(construirLocacao(rs));
            }
        }
        return locacoes;
    }

    private Locacao construirLocacao(ResultSet rs) throws SQLException {
        return new Locacao(
            rs.getObject("id_locacao", Integer.class),
            rs.getString("cpf_cliente"),
            rs.getString("placa_veiculo"),
            rs.getString("cnpj_filial_retirada"),
            rs.getString("cnpj_filial_retorno"),
            rs.getObject("data_inicial", LocalDate.class),
            rs.getObject("data_final", LocalDate.class),
            rs.getObject("combust_inicial", Double.class),
            rs.getObject("combust_final", Double.class),
            rs.getObject("km_percorrido", Double.class),
            rs.getObject("valor", Double.class)
        );
    }
}
