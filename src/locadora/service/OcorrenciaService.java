package locadora.service;

import locadora.dao.OcorrenciaDAO;
import locadora.model.Ocorrencia;

import java.sql.SQLException;
import java.util.List;

public class OcorrenciaService {
    private final OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

    public void salvar(Ocorrencia ocorrencia) throws SQLException {
        ocorrenciaDAO.inserir(ocorrencia);
    }

    public void atualizar(Ocorrencia ocorrencia) throws SQLException {
        ocorrenciaDAO.atualizar(ocorrencia);
    }

    public void deletar(Integer idOcorrencia) throws SQLException {
        ocorrenciaDAO.deletar(idOcorrencia);
    }

    public Ocorrencia buscar(Integer idOcorrencia) throws SQLException {
        return ocorrenciaDAO.buscarPorId(idOcorrencia);
    }

    public List<Ocorrencia> listarTodas() throws SQLException {
        return ocorrenciaDAO.listarTodos();
    }

    public List<Ocorrencia> listarPorLocacao(Integer idLocacao) throws SQLException {
        return ocorrenciaDAO.listarPorLocacao(idLocacao);
    }

    public List<Ocorrencia> listarPorTipo(String tipo) throws SQLException {
        return ocorrenciaDAO.listarPorTipo(tipo);
    }

    public boolean validarOcorrencia(Ocorrencia ocorrencia) {
        if (ocorrencia.getIdLocacao() == null || ocorrencia.getIdLocacao() <= 0) {
            return false;
        }
        if (ocorrencia.getTipo() == null || ocorrencia.getTipo().trim().isEmpty()) {
            return false;
        }
        if (ocorrencia.getDataOcorrencia() == null) {
            return false;
        }
        if (ocorrencia.getDescricao() == null || ocorrencia.getDescricao().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
