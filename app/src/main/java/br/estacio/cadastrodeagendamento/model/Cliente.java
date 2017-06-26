package br.estacio.cadastrodeagendamento.model;

import java.io.Serializable;
import java.util.Calendar;

public class Cliente implements Serializable {

    private long id;
    private String nome;
    private Calendar data;
    private String fone;
    private Servico servicoAtendimento;


    private String caminhoFoto;

    public Cliente() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getFone() {return fone;}

    public void setFone(String fone) {this.fone = fone;}

    @Override
    public String toString() {
        return nome + "  " + servicoAtendimento.toString();
    }


    public Servico getServicoAtendimento() {
        return servicoAtendimento;
    }

    public void setServicoAtendimento(Servico servicoAtendimento) {
        this.servicoAtendimento = servicoAtendimento;
    }
}
