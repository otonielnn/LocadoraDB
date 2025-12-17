package locadora.model;

public class Chamada {
    private Integer idLocacao;
    private String cpfFuncionario;

    public Chamada() {}

    public Chamada(Integer idLocacao, String cpfFuncionario) {
        this.idLocacao = idLocacao;
        this.cpfFuncionario = cpfFuncionario;
    }

    public Integer getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    @Override
    public String toString() {
        return "Chamada{" +
                "idLocacao=" + idLocacao +
                ", cpfFuncionario='" + cpfFuncionario + '\'' +
                '}';
    }
}
