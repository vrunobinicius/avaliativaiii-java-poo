package org.example.contrato;

/*
CREATE TABLE contrato (
      ID bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
      redacao TEXT,
      ultima_atualizacao DATE NOT NULL,
      cliente_ID bigint,
      FOREIGN KEY (cliente_ID) REFERENCES cliente(ID)
);
*/

import org.example.cliente.ClienteDAO;
import org.example.database.Dao;
import org.example.database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContratoDAO extends Dao<Contrato> {

    public static final String TABLE = "contrato";

    @Override
    public String getSaveStatement() {
        return "INSERT INTO " + TABLE + "(redacao, ultima_atualizacao, cliente_ID)  values (?, ?, ?)";
    }

    @Override
    public String getUpdateStatement() {
        return "UPDATE " + TABLE + " SET redacao = ?, ultima_atualizacao = ?, cliente_ID = ? where id = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Contrato e) {

        try{
            pstmt.setString(1, e.getRedacao());
            pstmt.setObject(2, e.getUltimaAtualizacao(), Types.DATE);
            pstmt.setLong(3, e.getCliente().getId());

            if(e.getId() != null) {
                pstmt.setLong(4, e.getId());
            }
        }catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getFindByIdStatement() {
        return "SELECT ID, redacao, ultima_atualizacao, cliente_ID"
                + " FROM " + TABLE + " WHERE ID = ?";
    }

    public List<Contrato> findContratosByClientID(Long ID){
        final String SQL = "SELECT * FROM " + TABLE + " WHERE cliente_ID = " + ID.toString();
        List<Contrato> contratos = new ArrayList<>();

        try(PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(SQL)){
            System.out.println(">> SQL: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                contratos.add(extractObject(resultSet));
            }
        }catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return contratos;
    }


    @Override
    public String getFindAllStatement() {
        return "SELECT * FROM " + TABLE;
    }

    @Override
    public Contrato extractObject(ResultSet resultSet) {

        Contrato contrato = null;

        try {
            contrato = new Contrato();
            contrato.setId(resultSet.getLong("ID"));
            contrato.setRedacao(resultSet.getString("redacao"));
            contrato.setUltimaAtualizacao(resultSet.getObject("ultima_atualizacao", LocalDate.class));

            // Codigo para inicializar o cliente do contrato
            contrato.setClienteId(resultSet.getLong("cliente_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return contrato;
    }

    public Contrato findContratoByID(Long ID) {
        final String SQL = "SELECT * FROM " + TABLE + " WHERE ID = " + ID.toString();

        try (PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(SQL)) {
            System.out.println(">> SQL: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractObject(resultSet);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

}
