package demo.model;

public class Membre {
	private int id;
    private String nomMembre;
    private String prenomMembre;
    private String telephone;
    private String emailMembre;
    private String fonction;
    private int idAssociation;
    
	public Membre(int id, String nomMembre, String prenomMembre, String telephone, String emailMembre,
			String fonciton,int idAssociation) {
		super();
		this.id = id;
		this.nomMembre = nomMembre;
		this.prenomMembre = prenomMembre;
		this.telephone = telephone;
		this.emailMembre = emailMembre;
		this.fonction = fonciton;
		this.idAssociation = idAssociation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomMembre() {
		return nomMembre;
	}
	public void setNomMembre(String nomMembre) {
		this.nomMembre = nomMembre;
	}
	public String getPrenomMembre() {
		return prenomMembre;
	}
	public void setPrenomMembre(String prenomMembre) {
		this.prenomMembre = prenomMembre;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmailMembre() {
		return emailMembre;
	}
	public void setEmailMembre(String emailMembre) {
		this.emailMembre = emailMembre;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonciton) {
		this.fonction = fonciton;
	}
	public int getIdAssociation() {
		return idAssociation;
	}
	public void setIdAssociation(int idAssociation) {
		this.idAssociation = idAssociation;
	}
}
