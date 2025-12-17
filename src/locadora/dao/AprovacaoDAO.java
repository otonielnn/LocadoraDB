package locadora.dao;

import locadora.model.Aprovacao;
import locadora.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AprovacaoDAO {

    public void inserir(Aprovacao aprovacao) throws SQLException {
        String sql = "INSERT INTO aprovacao (id_locacao, cpf_funcionario) VALUES (?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, aprovacao.getIdLocacao());
            stmt.setString(2, aprovacao.getCpfFuncionario());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Aprovacao aprovacao) throws SQLException {
        String sql = "UPDATE aprovacao SET cpf_funcionario = ? WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aprovacao.getCpfFuncionario());
            stmt.setObject(2, aprovacao.getIdLocacao());
            stmt.executeUpdate();
        }
    }

    public void deletar(Integer idLocacao) throws SQLException {
        String sql = "DELETE FROM aprovacao WHERE id_locacao = ?";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idLocacao);
            stmt.executeUpdate();
        }
    }

    public Aprovacao buscarPorIdLocacao(Integer idLocacao) throws SQLException {
        String sql = "SELECT * FROM aprovacao WHERE id_locacao = ?";
        Aprovacao aprovacao = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, idLocacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aprovacao = new Aprovacao(
                        rs.getObject("id_locacao", Integer.class),
                        rs.getString("cpf_funcionario")
                    );
                }
            }
        }
        return aprovacao;
    }

    public List<Aprovacao> listarTodas() throws SQLException {
        String sql = "SELECT * FROM aprovacao";
        List<Aprovacao> aprovacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                aprovacoes.add(new Aprovacao(
                    rs.getObject("id_locacao", Integer.class),
                    rs.getString("cpf_funcionario")
                ));
            }
        }
        return aprovacoes;
    }

    public List<Aprovacao> listarPorFuncionario(String cpfFuncionario) throws SQLException {
        String sql = "SELECT * FROM aprovacao WHERE cpf_funcionario = ?";
        List<Aprovacao> aprovacoes = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    aprovacoes.add(new Aprovacao(
                        rs.getObject("id_locacao", Integer.class),
                        rs.getString("cpf_funcionario")
                    ));
                }
            }
        }
        return aprovacoes;
    }
}
