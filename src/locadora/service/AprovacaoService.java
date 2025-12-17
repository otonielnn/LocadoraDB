package locadora.service;

import locadora.dao.AprovacaoDAO;
import locadora.model.Aprovacao;

import java.sql.SQLException;
import java.util.List;

public class AprovacaoService {
    private final AprovacaoDAO aprovacaoDAO = new AprovacaoDAO();

    public void salvar(Aprovacao aprovacao) throws SQLException {
        aprovacaoDAO.inserir(aprovacao);
    }

    public void atualizar(Aprovacao aprovacao) throws SQLException {
        aprovacaoDAO.atualizar(aprovacao);
    }

    public void deletar(Integer idLocacao) throws SQLException {
        aprovacaoDAO.deletar(idLocacao);
    }

    public Aprovacao buscar(Integer idLocacao) throws SQLException {
        return aprovacaoDAO.buscarPorIdLocacao(idLocacao);
    }

    public List<Aprovacao> listarTodas() throws SQLException {
        return aprovacaoDAO.listarTodas();
    }

    public List<Aprovacao> listarPorFuncionario(String cpfFuncionario) throws SQLException {
        return aprovacaoDAO.listarPorFuncionario(cpfFuncionario);
    }

    public boolean validarAprovacao(Aprovacao aprovacao) {
        if (aprovacao.getIdLocacao() == null || aprovacao.getIdLocacao() <= 0) {
            return false;
        }
        if (aprovacao.getCpfFuncionario() == null || aprovacao.getCpfFuncionario().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
