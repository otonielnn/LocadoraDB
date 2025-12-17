package locadora.dao;

import locadora.model.Chamada;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChamadaDAO {

    public void inserir(Chamada chamada) throws SQLException {
        String sql = "INSERT INTO chamada (id_locacao, cpf_funcionario) VALUES (?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, chamada.getIdLocacao());
            stmt.setString(2, chamada.getCpfFuncionario());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Chamada chamada) throws SQLException {
        String sql = "UPDATE chamada SET cpf_funcionario = ? WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, chamada.getCpfFuncionario());
            stmt.setObject(2, chamada.getIdLocacao());
            stmt.executeUpdate();
        }
    }

    public void deletar(Integer idLocacao) throws SQLException {
        String sql = "DELETE FROM chamada WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idLocacao);
            stmt.executeUpdate();
        }
    }

    public Chamada buscarPorIdLocacao(Integer idLocacao) throws SQLException {
        String sql = "SELECT * FROM chamada WHERE id_locacao = ?";
        Chamada chamada = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idLocacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    chamada = new Chamada(
                        rs.getObject("id_locacao", Integer.class),
                        rs.getString("cpf_funcionario")
                    );
                }
            }
        }
        return chamada;
    }

    public List<Chamada> listarTodas() throws SQLException {
        String sql = "SELECT * FROM chamada";
        List<Chamada> chamadas = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                chamadas.add(new Chamada(
                    rs.getObject("id_locacao", Integer.class),
                    rs.getString("cpf_funcionario")
                ));
            }
        }
        return chamadas;
    }

    public List<Chamada> listarPorFuncionario(String cpfFuncionario) throws SQLException {
        String sql = "SELECT * FROM chamada WHERE cpf_funcionario = ?";
        List<Chamada> chamadas = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chamadas.add(new Chamada(
                        rs.getObject("id_locacao", Integer.class),
                        rs.getString("cpf_funcionario")
                    ));
                }
            }
        }
        return chamadas;
    }
}
