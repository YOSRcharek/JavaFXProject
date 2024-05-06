package Application.Repository;

import Application.Model.Association;
import Application.Model.Categorie; // Remplacez "Service" par "Categorie"

import java.sql.SQLException;
import java.util.List;

public interface Icategorie<T> {
    void update(Categorie categorie, int id); // Remplacez "Service" par "Categorie"

    Association getassocaitionbyid(int id);

    <T2> List<T2> getAllassociation();

    Association getAssociationById(int id);

    <T> void add(T t) throws SQLException; // Vous pouvez conserver ce comme il est

    <T> void update(T t, int id); // Vous pouvez conserver ce comme il est

    void delete(int id); // Vous pouvez conserver ce comme il est

    <T> List<T> getAll(); // Vous pouvez conserver ce comme il est

    <T> T getbyid(int id); // Vous pouvez conserver ce comme il est
}