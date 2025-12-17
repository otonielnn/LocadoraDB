package locadora.dao;

import locadora.model.Ocorrencia;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaDAO {

    public void inserir(Ocorrencia ocorrencia) throws SQLException {
        String sql = "INSERT INTO ocorrencia (id_locacao, tipo, data_ocorrencia, descricao) " +
                "VALUES (?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, ocorrencia.getIdLocacao());
            stmt.setString(2, ocorrencia.getTipo());
            stmt.setObject(3, ocorrencia.getDataOcorrencia());
            stmt.setString(4, ocorrencia.getDescricao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Ocorrencia ocorrencia) throws SQLException {
        String sql = "UPDATE ocorrencia SET id_locacao = ?, tipo = ?, data_ocorrencia = ?, descricao = ? WHERE id_ocorrencia = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, ocorrencia.getIdLocacao());
            stmt.setString(2, ocorrencia.getTipo());
            stmt.setObject(3, ocorrencia.getDataOcorrencia());
            stmt.setString(4, ocorrencia.getDescricao());
            stmt.setObject(5, ocorrencia.getIdOcorrencia());
            stmt.executeUpdate();
        }
    }

    public void deletar(Integer idOcorrencia) throws SQLException {
        String sql = "DELETE FROM ocorrencia WHERE id_ocorrencia = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idOcorrencia);
            stmt.executeUpdate();
        }
    }

    public Ocorrencia buscarPorId(Integer idOcorrencia) throws SQLException {
        String sql = "SELECT * FROM ocorrencia WHERE id_ocorrencia = ?";
        Ocorrencia ocorrencia = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idOcorrencia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ocorrencia = construirOcorrencia(rs);
                }
            }
        }
        return ocorrencia;
    }

    public List<Ocorrencia> listarTodos() throws SQLException {
        String sql = "SELECT * FROM ocorrencia";
        List<Ocorrencia> ocorrencias = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ocorrencias.add(construirOcorrencia(rs));
            }
        }
        return ocorrencias;
    }

    public List<Ocorrencia> listarPorLocacao(Integer idLocacao) throws SQLException {
        String sql = "SELECT * FROM ocorrencia WHERE id_locacao = ?";
        List<Ocorrencia> ocorrencias = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idLocacao);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ocorrencias.add(construirOcorrencia(rs));
                }
            }
        }
        return ocorrencias;
    }

    public List<Ocorrencia> listarPorTipo(String tipo) throws SQLException {
        String sql = "SELECT * FROM ocorrencia WHERE tipo = ?";
        List<Ocorrencia> ocorrencias = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ocorrencias.add(construirOcorrencia(rs));
                }
            }
        }
        return ocorrencias;
    }

    private Ocorrencia construirOcorrencia(ResultSet rs) throws SQLException {
        return new Ocorrencia(
            rs.getObject("id_ocorrencia", Integer.class),
            rs.getObject("id_locacao", Integer.class),
            rs.getString("tipo"),
            rs.getObject("data_ocorrencia", LocalDateTime.class),
            rs.getString("descricao")
        );
    }
}
