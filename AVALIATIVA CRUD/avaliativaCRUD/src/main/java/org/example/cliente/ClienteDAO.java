package org.example.cliente;

/*
CREATE TABLE cliente (
     ID bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
     cpf bigint NOT NULL UNIQUE,
     nome varchar(45) NOT NULL
);
*/

import org.example.contrato.Contrato;
import org.example.contrato.ContratoDAO;
import org.example.database.Dao;
import org.example.database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends Dao<Cliente> {

    public static final String TABLE = "cliente";

    @Override
    public String getSaveStatement() {
        return "INSERT INTO " + TABLE + " (cpf, nome) values (?, ?)";
    }

    @Override
    public String getUpdateStatement() {
        return "UPDATE " + TABLE + " SET cpf = ?, nome = ? where ID = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Cliente e) {
        try{
            pstmt.setLong(1, e.getCpf());

            pstmt.setString(2, e.getNome());

            if(e.getId() != null){
                pstmt.setLong(3, e.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getFindByIdStatement() {
        return "SELECT ID, cpf, nome"
                + " FROM " + TABLE + " WHERE ID = ?";
    }

    @Override
    public String getFindAllStatement() {
        return "SELECT * FROM " + TABLE;
    }

    public Cliente findClienteByID(Long ID){
        final String SQL = "SELECT * FROM " + TABLE + " WHERE ID = " + ID.toString();

        try(PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(SQL)){
            System.out.println(">> SQL: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return extractObject(resultSet);
            }
        }catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    public List<Contrato> findContratosByClientID(Long id) {
        ContratoDAO contratoDAO = new ContratoDAO();
        return contratoDAO.findContratosByClientID(id);
    }

    @Override
    public Cliente extractObject(ResultSet resultSet) {

        Cliente cliente = null;

        try{
            cliente = new Cliente();
            cliente.setId(resultSet.getLong("ID"));
            cliente.setCpf(resultSet.getLong("cpf"));
            cliente.setNome(resultSet.getString("nome"));

        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }
}
