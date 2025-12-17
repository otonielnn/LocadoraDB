package locadora.service;

import locadora.dao.ChamadaDAO;
import locadora.model.Chamada;

import java.sql.SQLException;
import java.util.List;

public class ChamadaService {
    private final ChamadaDAO chamadaDAO = new ChamadaDAO();

    public void salvar(Chamada chamada) throws SQLException {
        chamadaDAO.inserir(chamada);
    }

    public void atualizar(Chamada chamada) throws SQLException {
        chamadaDAO.atualizar(chamada);
    }

    public void deletar(Integer idLocacao) throws SQLException {
        chamadaDAO.deletar(idLocacao);
    }

    public Chamada buscar(Integer idLocacao) throws SQLException {
        return chamadaDAO.buscarPorIdLocacao(idLocacao);
    }

    public List<Chamada> listarTodas() throws SQLException {
        return chamadaDAO.listarTodas();
    }

    public List<Chamada> listarPorFuncionario(String cpfFuncionario) throws SQLException {
        return chamadaDAO.listarPorFuncionario(cpfFuncionario);
    }

    public boolean validarChamada(Chamada chamada) {
        if (chamada.getIdLocacao() == null || chamada.getIdLocacao() <= 0) {
            return false;
        }
        if (chamada.getCpfFuncionario() == null || chamada.getCpfFuncionario().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
