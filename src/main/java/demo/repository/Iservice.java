package demo.repository;

import demo.model.Service;

import java.sql.SQLException;
import java.util.List;

public interface Iservice<T> {
    void update(Service service, int id) throws SQLException;

    <T> void add (T t) throws SQLException;
    <T> void update(T t, int id);

    void delete(int id) throws SQLException;
    <T> List<T> getAll() throws SQLException;
    <T> T getbyid(int id);

    Service getById(int id) throws SQLException;



    void ajouter(Service service) throws SQLException;


    /*  @Override
          public void update(Service service) throws SQLException {

          }

          @Override
          public void delete(Service service) throws SQLException {

          }




      @Override
      public void update(Service service) throws SQLException {
          String request = "UPDATE service SET nom_service=?, description=?, disponibilite=? WHERE id=?";

          PreparedStatement preparedStatement = cnx.prepareStatement(request);
          preparedStatement.setString(1, service.getNom_service());
          preparedStatement.setString(2, service.getDescription());
          preparedStatement.setBoolean(3, service.isDisponibilite());
          preparedStatement.setInt(4, service.getId());
          preparedStatement.executeUpdate();
      }*/
    void delete(Service service) throws SQLException;

    void update(Service service) throws SQLException;
}


