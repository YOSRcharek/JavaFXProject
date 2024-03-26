package application.model;

public class Membre {
	private int id;
    private String nomMembre;
    private String prenomMembre;
    private String telephone;
    private String emailMembre;
    private String fonciton;
	public Membre(int id, String nomMembre, String prenomMembre, String telephone, String emailMembre,
			String fonciton) {
		super();
		this.id = id;
		this.nomMembre = nomMembre;
		this.prenomMembre = prenomMembre;
		this.telephone = telephone;
		this.emailMembre = emailMembre;
		this.fonciton = fonciton;
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
	public String getFonciton() {
		return fonciton;
	}
	public void setFonciton(String fonciton) {
		this.fonciton = fonciton;
	}
}
