package org.example.database;

import org.example.entity.Entity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public abstract class Dao<T>
        implements IDao<T> {

    public static final String DB = "avaliacao";

    @Override
    public Long saveOrUpdate(T e) {

        Long id = 0L;

        if (((Entity) e).getId() == null
                || ((Entity) e).getId() == 0) {

            try ( PreparedStatement preparedStatement
                    = DbConnection.getConnection().prepareStatement(
                            getSaveStatement(),
                            Statement.RETURN_GENERATED_KEYS)) {

                composeSaveOrUpdateStatement(preparedStatement, e);

                System.out.println("-> SQL: " + preparedStatement);

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                }

            } catch (Exception ex) {
                System.out.println("-> " + ex);
            }

        } else {
            try ( PreparedStatement preparedStatement
                    = DbConnection.getConnection().prepareStatement(
                            getUpdateStatement())) {

                composeSaveOrUpdateStatement(preparedStatement, e);
                System.out.println("-> SQL: " + preparedStatement);
                preparedStatement.executeUpdate();
                id = ((Entity) e).getId();

            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
            }
        }

        return id;
    }
    @Override
    public T findById(Long id) {

        try ( PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindByIdStatement())) {

            preparedStatement.setLong(1, id);

            System.out.println("-> SQL: " + preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractObject(resultSet);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }
    @Override
    public List<T> findAll() {

        try ( PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindAllStatement())) {

            System.out.println("-> SQL: " + preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            return extractObjects(resultSet);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }
    @Override
    public List<T> extractObjects(ResultSet resultSet) {
        List<T> objects = new ArrayList<>();

        try {
            while (resultSet.next()) {
                objects.add(extractObject(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return objects.isEmpty() ? null : objects;
    }
}
