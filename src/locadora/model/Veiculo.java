package locadora.model;

public class Veiculo {
    private String placa;
    private String tipo;
    private String modelo;
    private Integer ano;
    private Double precoDiaria;
    private Double kmAnterior;
    private Boolean alugado;
    private String historico;
    private String cnpjFilial;

    public Veiculo() {}

    public Veiculo(String placa, String tipo, String modelo, Integer ano, Double precoDiaria,
                   Double kmAnterior, Boolean alugado, String historico, String cnpjFilial) {
        this.placa = placa;
        this.tipo = tipo;
        this.modelo = modelo;
        this.ano = ano;
        this.precoDiaria = precoDiaria;
        this.kmAnterior = kmAnterior;
        this.alugado = alugado;
        this.historico = historico;
        this.cnpjFilial = cnpjFilial;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(Double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public Double getKmAnterior() {
        return kmAnterior;
    }

    public void setKmAnterior(Double kmAnterior) {
        this.kmAnterior = kmAnterior;
    }

    public Boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(Boolean alugado) {
        this.alugado = alugado;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getCnpjFilial() {
        return cnpjFilial;
    }

    public void setCnpjFilial(String cnpjFilial) {
        this.cnpjFilial = cnpjFilial;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", tipo='" + tipo + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", precoDiaria=" + precoDiaria +
                ", kmAnterior=" + kmAnterior +
                ", alugado=" + alugado +
                ", cnpjFilial='" + cnpjFilial + '\'' +
                '}';
    }
}
