package org.example.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
public interface IDao<T> {
    public String getSaveStatement();
    public String getUpdateStatement();
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, T e);
    public Long saveOrUpdate(T e);
    public String getFindByIdStatement();
    public T findById(Long id);
    public String getFindAllStatement();
    public List<T> findAll();
    public T extractObject(ResultSet resultSet);
    public List<T> extractObjects(ResultSet resultSet);
}
