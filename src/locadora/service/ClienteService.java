package locadora.service;

import locadora.dao.ClienteDAO;
import locadora.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void salvar(Cliente cliente) throws SQLException {
        if (clienteDAO.buscarPorCpf(cliente.getCpf()) == null) {
            clienteDAO.inserir(cliente);
        } else {
            clienteDAO.atualizar(cliente);
        }
    }

    public void deletar(String cpf) throws SQLException {
        clienteDAO.deletar(cpf);
    }

    public Cliente buscar(String cpf) throws SQLException {
        return clienteDAO.buscarPorCpf(cpf);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }

    public List<Cliente> buscarPorNome(String nome) throws SQLException {
        return clienteDAO.buscarPorNome(nome);
    }

    public boolean validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            return false;
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            return false;
        }
        if (cliente.getCnh() == null || cliente.getCnh().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
