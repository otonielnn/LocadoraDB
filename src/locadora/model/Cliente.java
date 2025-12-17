package locadora.model;

import java.time.LocalDate;

public class Cliente {
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String cnh;
    private Double avaliacao;

    public Cliente() {}

    public Cliente(String cpf, String nome, LocalDate dataNascimento, String cnh, Double avaliacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cnh = cnh;
        this.avaliacao = avaliacao;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", cnh='" + cnh + '\'' +
                ", avaliacao=" + avaliacao +
                '}';
    }
}
