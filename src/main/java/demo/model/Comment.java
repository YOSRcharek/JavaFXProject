package demo.model;


import java.sql.Date;

public class Comment {
    private int id;
    private int idpost_id;
    private int username_id;
    private String contentcomment;
    private int upvotes;
    private Date createdatcomment;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpost_id() {
        return idpost_id;
    }

    public void setIdpost_id(int idpost_id) {
        this.idpost_id = idpost_id;
    }

    public int getUsername_id() {
        return username_id;
    }

    public void setUsername_id(int username_id) {
        this.username_id = username_id;
    }

    public String getContentcomment() {
        return contentcomment;
    }

    public void setContentcomment(String contentcomment) {
        this.contentcomment = contentcomment;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public Date getCreatedatcomment() {
        return createdatcomment;
    }

    public void setCreatedatcomment(Date createdatcomment) {
        this.createdatcomment = createdatcomment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", idpost_id=" + idpost_id +
                ", username_id=" + username_id +
                ", contentcomment='" + contentcomment + '\'' +
                ", upvotes=" + upvotes +
                ", createdatcomment=" + createdatcomment +
                '}';
    }
}
