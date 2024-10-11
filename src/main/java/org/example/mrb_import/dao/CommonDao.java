package org.example.mrb_import.dao;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CommonDao<T> {
    void save(T t, Connection connection) throws SQLException;
    void saveAll(List<T> t, Connection connection) throws SQLException;
}
