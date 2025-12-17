package locadora.model;

public class Funcionario {
    private String cpf;
    private String nome;
    private String endereco;
    private Double salario;
    private String cargo;
    private String cnpjFilial;

    public Funcionario() {}

    public Funcionario(String cpf, String nome, String endereco, Double salario, String cargo, String cnpjFilial) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.cargo = cargo;
        this.cnpjFilial = cnpjFilial;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCnpjFilial() {
        return cnpjFilial;
    }

    public void setCnpjFilial(String cnpjFilial) {
        this.cnpjFilial = cnpjFilial;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", cargo='" + cargo + '\'' +
                ", cnpjFilial='" + cnpjFilial + '\'' +
                '}';
    }
}
