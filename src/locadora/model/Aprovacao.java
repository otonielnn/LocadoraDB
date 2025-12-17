package locadora.model;

public class Aprovacao {
    private Integer idLocacao;
    private String cpfFuncionario;

    public Aprovacao() {}

    public Aprovacao(Integer idLocacao, String cpfFuncionario) {
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
        return "Aprovacao{" +
                "idLocacao=" + idLocacao +
                ", cpfFuncionario='" + cpfFuncionario + '\'' +
                '}';
    }
}
