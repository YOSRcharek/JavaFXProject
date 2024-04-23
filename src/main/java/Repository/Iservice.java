package Repository;

import Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface Iservice<T> {
    void update(Service service, int id);

    <T> void add (T t) throws SQLException;
    <T> void update(T t, int id);

    void delete(int id);
    <T> List<T> getAll();
    <T> T getbyid(int id);
}


