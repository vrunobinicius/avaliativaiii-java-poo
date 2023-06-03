package org.example.cliente;

import org.example.contrato.Contrato;
import org.example.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Entity {

    private Long cpf;
    private String nome;
    private List<Contrato> contratos;

    public Cliente() {
        contratos = new ArrayList<>();
    }

    public Cliente(String cpf, String nome) {
        this.cpf = Long.parseLong(cpf);
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.length() > 45) {
            throw new Exception("O nome não pode ter mais que 45 caracteres");
        }

        this.nome = nome;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public String toString() {
        return "Cliente {" +
                "id=" + getId() +
                ", cpf=" + cpf +
                ", nome='" + nome + '\'' +
                ", contratos=" + (contratos != null ? contratos.size() : "N/A") +
                '}';
    }

}
