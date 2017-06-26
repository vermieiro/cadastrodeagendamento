package br.estacio.cadastrodeagendamento.model;

/**
 * Created by aluno on 29/05/17.
 */

public enum Servico {
    c("Cabelo"),
    b("Barba"),
    a("Bigode"),
    d("Cabelo, Barba e Bigode");


    private String servicoAtendimento;


    Servico(String servicoAtendimento) {
        this.servicoAtendimento = servicoAtendimento;
    }

    @Override
    public String toString() {
        return servicoAtendimento;
    }


}

