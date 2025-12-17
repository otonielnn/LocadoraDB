package locadora.model;

import java.time.LocalDate;

public class Locacao {
    private Integer idLocacao;
    private String cpfCliente;
    private String placaVeiculo;
    private String cnpjFilialRetirada;
    private String cnpjFilialRetorno;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Double combustInicial;
    private Double combustFinal;
    private Double kmPercorrido;
    private Double valor;

    public Locacao() {}

    public Locacao(Integer idLocacao, String cpfCliente, String placaVeiculo, 
                   String cnpjFilialRetirada, String cnpjFilialRetorno,
                   LocalDate dataInicial, LocalDate dataFinal,
                   Double combustInicial, Double combustFinal,
                   Double kmPercorrido, Double valor) {
        this.idLocacao = idLocacao;
        this.cpfCliente = cpfCliente;
        this.placaVeiculo = placaVeiculo;
        this.cnpjFilialRetirada = cnpjFilialRetirada;
        this.cnpjFilialRetorno = cnpjFilialRetorno;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.combustInicial = combustInicial;
        this.combustFinal = combustFinal;
        this.kmPercorrido = kmPercorrido;
        this.valor = valor;
    }

    public Integer getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getCnpjFilialRetirada() {
        return cnpjFilialRetirada;
    }

    public void setCnpjFilialRetirada(String cnpjFilialRetirada) {
        this.cnpjFilialRetirada = cnpjFilialRetirada;
    }

    public String getCnpjFilialRetorno() {
        return cnpjFilialRetorno;
    }

    public void setCnpjFilialRetorno(String cnpjFilialRetorno) {
        this.cnpjFilialRetorno = cnpjFilialRetorno;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Double getCombustInicial() {
        return combustInicial;
    }

    public void setCombustInicial(Double combustInicial) {
        this.combustInicial = combustInicial;
    }

    public Double getCombustFinal() {
        return combustFinal;
    }

    public void setCombustFinal(Double combustFinal) {
        this.combustFinal = combustFinal;
    }

    public Double getKmPercorrido() {
        return kmPercorrido;
    }

    public void setKmPercorrido(Double kmPercorrido) {
        this.kmPercorrido = kmPercorrido;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "idLocacao=" + idLocacao +
                ", cpfCliente='" + cpfCliente + '\'' +
                ", placaVeiculo='" + placaVeiculo + '\'' +
                ", cnpjFilialRetirada='" + cnpjFilialRetirada + '\'' +
                ", cnpjFilialRetorno='" + cnpjFilialRetorno + '\'' +
                ", dataInicial=" + dataInicial +
                ", dataFinal=" + dataFinal +
                ", combustInicial=" + combustInicial +
                ", combustFinal=" + combustFinal +
                ", kmPercorrido=" + kmPercorrido +
                ", valor=" + valor +
                '}';
    }
}
