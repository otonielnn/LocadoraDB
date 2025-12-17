package locadora.service;

import locadora.dao.FuncionarioDAO;
import locadora.model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void salvar(Funcionario funcionario) throws SQLException {
        if (funcionarioDAO.buscarPorCpf(funcionario.getCpf()) == null) {
            funcionarioDAO.inserir(funcionario);
        } else {
            funcionarioDAO.atualizar(funcionario);
        }
    }

    public void deletar(String cpf) throws SQLException {
        funcionarioDAO.deletar(cpf);
    }

    public Funcionario buscar(String cpf) throws SQLException {
        return funcionarioDAO.buscarPorCpf(cpf);
    }

    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioDAO.listarTodos();
    }

    public List<Funcionario> listarPorFilial(String cnpjFilial) throws SQLException {
        return funcionarioDAO.listarPorFilial(cnpjFilial);
    }

    public List<Funcionario> listarPorCargo(String cargo) throws SQLException {
        return funcionarioDAO.listarPorCargo(cargo);
    }

    public boolean validarFuncionario(Funcionario funcionario) {
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            return false;
        }
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            return false;
        }
        if (funcionario.getSalario() == null || funcionario.getSalario() <= 0) {
            return false;
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            return false;
        }
        if (funcionario.getCnpjFilial() == null || funcionario.getCnpjFilial().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
