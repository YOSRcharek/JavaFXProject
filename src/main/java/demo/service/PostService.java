package demo.service;

import demo.DatabaseConnection;
import demo.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService implements IService<Post>{

    private Connection connection;

    public PostService() {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String req = "INSERT INTO post (username_id, title, content, quote, image, rating, video, createdat, visible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, post.getUsername_id());
        os.setString(2, post.getTitle());
        os.setString(3, post.getContent());
        os.setString(4, post.getQuote());
        os.setString(5, post.getImage());
        os.setInt(6, post.getRating());
        os.setString(7, post.getVideo());
        os.setDate(8, new java.sql.Date(post.getCreatedat().getTime()));
    //     os.setInt(9, post.getVisible());
      //  os.setDate(10, new java.sql.Date(post.getDateupdatedat().getTime()));
        os.executeUpdate();
        System.out.println("Post ajoutée avec succès");
    }

    @Override
    public void modifier(Post post) throws SQLException {
        String req = "UPDATE post SET title = ?, content = ?, quote = ?, image = ?, rating = ?, video = ?, " +
                "createdat = ?, visible = ?, updatedat = ? WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, post.getTitle());
        os.setString(2, post.getContent());
        os.setString(3, post.getQuote());
        os.setString(4, post.getImage());
        os.setInt(5, post.getRating());
        os.setString(6, post.getVideo());
        os.setDate(7, new java.sql.Date(post.getCreatedat().getTime()));
        os.setInt(8, post.getVisible());
        os.setDate(9, new java.sql.Date(post.getDateupdatedat().getTime()));
        os.setInt(10, post.getId());
        os.executeUpdate();
        System.out.println("Post modifiée avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM post WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Post supprimée avec succès");
    }

    @Override
    public Post getOneById(int id) throws SQLException {
        String req = "SELECT * FROM post WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            return post;
        }
        return null;
    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<Post> getByIdUser(int id) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post WHERE username_id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            posts.add(post);
        }
        return posts;
    }
    public int getNumberOfLikesOrDislikes(int post_id, String type) throws SQLException {
        String req = "SELECT COUNT(*) FROM comment_like WHERE post_id = ? AND type = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, post_id);
        os.setString(2, type);

        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public  int AddLikesOrDislikes(int post_id, int user_id, String type) throws SQLException {
        // Vérifier si le couple user_id et post_id existe déjà dans la table
        String checkReq = "SELECT COUNT(*) FROM comment_like WHERE post_id = ? AND user_id = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkReq);
        checkStmt.setInt(1, post_id);
        checkStmt.setInt(2, user_id);
        ResultSet checkResult = checkStmt.executeQuery();
        checkResult.next();
        int count = checkResult.getInt(1);

        if (count > 0) {
            // Si le couple existe, mettre à jour le type
            String updateReq = "UPDATE comment_like SET type = ? WHERE post_id = ? AND user_id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateReq);
            updateStmt.setString(1, type);
            updateStmt.setInt(2, post_id);
            updateStmt.setInt(3, user_id);
            updateStmt.executeUpdate();
        } else {
            // Si le couple n'existe pas, l'ajouter
            String insertReq = "INSERT INTO comment_like (post_id, user_id, type) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertReq);
            insertStmt.setInt(1, post_id);
            insertStmt.setInt(2, user_id);
            insertStmt.setString(3, type);
            insertStmt.executeUpdate();
        }

        // Renvoyer le nombre total de likes ou dislikes pour ce post_id après l'opération
        return getNumberOfLikesOrDislikes(post_id, type);
    }
    public String getusermail(int id){

        String req = "SELECT email FROM user WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                return res.getString("email");
            } else {
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**************** 2  ***/
    @Override
    public void updateVisibility(int postId, boolean isVisible) throws SQLException {
        String req = "UPDATE post SET visible = ? WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, isVisible ? 1 : 0); // Convert boolean to integer (1 for true, 0 for false)
        os.setInt(2, postId);
        os.executeUpdate();
        System.out.println("Post visibility updated successfully");
    }
    /*****************************/

}