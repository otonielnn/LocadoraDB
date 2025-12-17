package locadora.service;

import locadora.dao.FilialDAO;
import locadora.model.Filial;

import java.sql.SQLException;
import java.util.List;

public class FilialService {
    private final FilialDAO filialDAO = new FilialDAO();

    public void salvar(Filial filial) throws SQLException {
        if (filialDAO.buscarPorCnpj(filial.getCnpj()) == null) {
            filialDAO.inserir(filial);
        } else {
            filialDAO.atualizar(filial);
        }
    }

    public void deletar(String cnpj) throws SQLException {
        filialDAO.deletar(cnpj);
    }

    public Filial buscar(String cnpj) throws SQLException {
        return filialDAO.buscarPorCnpj(cnpj);
    }

    public List<Filial> listarTodas() throws SQLException {
        return filialDAO.listarTodas();
    }

    public List<Filial> buscarPorNome(String nome) throws SQLException {
        return filialDAO.buscarPorNome(nome);
    }

    public boolean validarFilial(Filial filial) {
        if (filial.getCnpj() == null || filial.getCnpj().trim().isEmpty()) {
            return false;
        }
        if (filial.getNome() == null || filial.getNome().trim().isEmpty()) {
            return false;
        }
        if (filial.getEndereco() == null || filial.getEndereco().trim().isEmpty()) {
            return false;
        }
        if (filial.getTelefone() == null || filial.getTelefone().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
