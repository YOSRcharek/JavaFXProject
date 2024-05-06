package com.example.aziz.services;

import com.example.aziz.Util.DataBase;
import com.example.aziz.models.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IService<Comment> {
    private Connection connection;

    public CommentService() {
        connection = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Comment comment) throws SQLException {
        String req = "INSERT INTO comment (idpost_id, username_id, contentcomment, upvotes, createdatcomment) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, comment.getIdpost_id());
        os.setInt(2, comment.getUsername_id());
        os.setString(3, comment.getContentcomment());
        os.setInt(4, comment.getUpvotes());
        os.setDate(5, new java.sql.Date(comment.getCreatedatcomment().getTime()));
        os.executeUpdate();
        System.out.println("Comment ajouté avec succès");
    }

    @Override
    public void modifier(Comment comment) throws SQLException {
        String req = "UPDATE comment SET contentcomment = ?, upvotes = ?, createdatcomment = ? " +
                "WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, comment.getContentcomment());
        os.setInt(2, comment.getUpvotes());
        os.setDate(3, new java.sql.Date(comment.getCreatedatcomment().getTime()));
        os.setInt(4, comment.getId());
        os.executeUpdate();
        System.out.println("Comment modifié avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM comment WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Comment supprimé avec succès");
    }

    @Override
    public Comment getOneById(int id) throws SQLException {
        String req = "SELECT * FROM comment WHERE idpost_id = ? ORDER BY id DESC LIMIT 1";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setIdpost_id(rs.getInt("idpost_id"));
            comment.setUsername_id(rs.getInt("username_id"));
            comment.setContentcomment(rs.getString("contentcomment"));
            comment.setUpvotes(rs.getInt("upvotes"));
            comment.setCreatedatcomment(rs.getDate("createdatcomment"));
            return comment;
        }
        return null;
    }

    @Override
    public List<Comment> getAll() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM comment";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setIdpost_id(rs.getInt("idpost_id"));
            comment.setUsername_id(rs.getInt("username_id"));
            comment.setContentcomment(rs.getString("contentcomment"));
            comment.setUpvotes(rs.getInt("upvotes"));
            comment.setCreatedatcomment(rs.getDate("createdatcomment"));
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public List<Comment> getByIdUser(int id) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM comment WHERE idpost_id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setIdpost_id(rs.getInt("idpost_id"));
            comment.setUsername_id(rs.getInt("username_id"));
            comment.setContentcomment(rs.getString("contentcomment"));
            comment.setUpvotes(rs.getInt("upvotes"));
            comment.setCreatedatcomment(rs.getDate("createdatcomment"));
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public void updateVisibility(int postId, boolean isVisible) throws SQLException {

    }
}
