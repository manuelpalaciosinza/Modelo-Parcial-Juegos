package Repositories.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IRepository <T>{

    public ArrayList<T> findAll()throws SQLException;
    public Optional<T> findById (int id) throws SQLException;
    public void deleteById(int id) throws SQLException;
    public void save (T t) throws SQLException;
    public void update (T t) throws  SQLException;
}
