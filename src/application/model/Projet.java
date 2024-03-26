package application.model;
import java.sql.Date;

public class Projet {
	private int id;
    private String nomProjet;
    private String description;
    private String status;
    private Date dateDebut;
    private Date dateFin;
    private int idAssociation;
	public Projet(int id, String nomProjet, String description, String status, Date dateDebut, Date dateFin ,int idAssociation) {
		super();
		this.id = id;
		this.nomProjet = nomProjet;
		this.description = description;
		this.setStatus(status);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.setIdAssociation(idAssociation);
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIdAssociation() {
		return idAssociation;
	}
	public void setIdAssociation(int idAssociation) {
		this.idAssociation = idAssociation;
	}
}
