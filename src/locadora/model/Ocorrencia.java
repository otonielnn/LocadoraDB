package locadora.model;

import java.time.LocalDateTime;

public class Ocorrencia {
    private Integer idOcorrencia;
    private Integer idLocacao;
    private String tipo;
    private LocalDateTime dataOcorrencia;
    private String descricao;

    public Ocorrencia() {}

    public Ocorrencia(Integer idOcorrencia, Integer idLocacao, String tipo, 
                      LocalDateTime dataOcorrencia, String descricao) {
        this.idOcorrencia = idOcorrencia;
        this.idLocacao = idLocacao;
        this.tipo = tipo;
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
    }

    public Integer getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Integer getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" +
                "idOcorrencia=" + idOcorrencia +
                ", idLocacao=" + idLocacao +
                ", tipo='" + tipo + '\'' +
                ", dataOcorrencia=" + dataOcorrencia +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
