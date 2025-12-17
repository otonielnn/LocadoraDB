package locadora.service;

import locadora.dao.LocacaoDAO;
import locadora.dao.VeiculoDAO;
import locadora.model.Locacao;
import locadora.model.Veiculo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LocacaoService {
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void salvar(Locacao locacao) throws SQLException {
        locacaoDAO.inserir(locacao);
    }

    public void atualizar(Locacao locacao) throws SQLException {
        locacaoDAO.atualizar(locacao);
    }

    public void deletar(Integer idLocacao) throws SQLException {
        locacaoDAO.deletar(idLocacao);
    }

    public Locacao buscar(Integer idLocacao) throws SQLException {
        return locacaoDAO.buscarPorId(idLocacao);
    }

    public List<Locacao> listarTodos() throws SQLException {
        return locacaoDAO.listarTodos();
    }

    public List<Locacao> listarPorCliente(String cpfCliente) throws SQLException {
        return locacaoDAO.listarPorCliente(cpfCliente);
    }

    public List<Locacao> listarPorVeiculo(String placaVeiculo) throws SQLException {
        return locacaoDAO.listarPorVeiculo(placaVeiculo);
    }

    public List<Locacao> listarAbertas() throws SQLException {
        return locacaoDAO.listarAbertas();
    }

    public boolean validarLocacao(Locacao locacao) {
        if (locacao.getCpfCliente() == null || locacao.getCpfCliente().trim().isEmpty()) {
            return false;
        }
        if (locacao.getPlacaVeiculo() == null || locacao.getPlacaVeiculo().trim().isEmpty()) {
            return false;
        }
        if (locacao.getCnpjFilialRetirada() == null || locacao.getCnpjFilialRetirada().trim().isEmpty()) {
            return false;
        }
        if (locacao.getCnpjFilialRetorno() == null || locacao.getCnpjFilialRetorno().trim().isEmpty()) {
            return false;
        }
        if (locacao.getDataInicial() == null) {
            return false;
        }
        if (locacao.getDataFinal() == null) {
            return false;
        }
        if (locacao.getDataFinal().isBefore(locacao.getDataInicial())) {
            return false;
        }
        return true;
    }

    public double calcularValorTotal(String placaVeiculo, LocalDate dataInicial, 
                                     LocalDate dataFinal) throws SQLException {
        Veiculo veiculo = veiculoDAO.buscarPorPlaca(placaVeiculo);
        if (veiculo != null) {
            long dias = ChronoUnit.DAYS.between(dataInicial, dataFinal);
            return veiculo.getPrecoDiaria() * dias;
        }
        return 0;
    }

    public void finalizarLocacao(Integer idLocacao, LocalDate dataRetorno, 
                                  Double combustFinal, Double kmPercorrido) throws SQLException {
        Locacao locacao = locacaoDAO.buscarPorId(idLocacao);
        if (locacao != null) {
            locacao.setDataFinal(dataRetorno);
            locacao.setCombustFinal(combustFinal);
            locacao.setKmPercorrido(kmPercorrido);
            locacaoDAO.atualizar(locacao);
        }
    }
}
