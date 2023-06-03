package org.example.contrato;

import org.example.cliente.Cliente;
import org.example.cliente.ClienteDAO;
import org.example.entity.Entity;

import java.time.LocalDate;

public class Contrato extends Entity {

    private String redacao;
    private LocalDate ultimaAtualizacao;
    private Long clienteId;
    private Cliente cliente;

    public Contrato() {
    }

    public Contrato(String redacao, LocalDate ultimaAtualizacao, Long clienteId) {
        this.redacao = redacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.clienteId = clienteId;
    }

    public void setRedacao(String redacao) throws Exception {
        if (redacao.length() > 100000) {
            throw new Exception("O nome não pode ter mais que 45 caracteres");
        }

        this.redacao = redacao;
    }

    public String getRedacao() {
        return redacao;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        if (cliente == null) {
            ClienteDAO clienteDAO = new ClienteDAO();
            cliente = clienteDAO.findById(clienteId);
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @Override
    public String toString() {
        return "Contrato {" +
                "id=" + getId() +
                ", redacao='" + redacao + '\'' +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                ", cliente=" + cliente +
                '}';
    }

}
