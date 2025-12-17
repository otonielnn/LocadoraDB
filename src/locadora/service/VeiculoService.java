package locadora.service;

import locadora.dao.VeiculoDAO;
import locadora.model.Veiculo;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void salvar(Veiculo veiculo) throws SQLException {
        if (veiculoDAO.buscarPorPlaca(veiculo.getPlaca()) == null) {
            veiculoDAO.inserir(veiculo);
        } else {
            veiculoDAO.atualizar(veiculo);
        }
    }

    public void deletar(String placa) throws SQLException {
        veiculoDAO.deletar(placa);
    }

    public Veiculo buscar(String placa) throws SQLException {
        return veiculoDAO.buscarPorPlaca(placa);
    }

    public List<Veiculo> listarTodos() throws SQLException {
        return veiculoDAO.listarTodos();
    }

    public List<Veiculo> listarDisponiveis() throws SQLException {
        return veiculoDAO.listarDisponiveis();
    }

    public List<Veiculo> listarPorFilial(String cnpj) throws SQLException {
        return veiculoDAO.listarPorFilial(cnpj);
    }

    public boolean validarVeiculo(Veiculo veiculo) {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
            return false;
        }
        if (veiculo.getTipo() == null || veiculo.getTipo().trim().isEmpty()) {
            return false;
        }
        if (veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty()) {
            return false;
        }
        if (veiculo.getAno() == null || veiculo.getAno() <= 0) {
            return false;
        }
        if (veiculo.getPrecoDiaria() == null || veiculo.getPrecoDiaria() <= 0) {
            return false;
        }
        if (veiculo.getCnpjFilial() == null || veiculo.getCnpjFilial().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
