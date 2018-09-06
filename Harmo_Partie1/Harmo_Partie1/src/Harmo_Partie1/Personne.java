package Harmo_Partie1;

import java.io.Serializable;

public class Personne implements Comparable<Personne>, Serializable {
	private String nom;
	private String prenom;
	private String tel;

	public Personne(String nom, String prenom, String tel) {
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
	}

	@Override
	public int compareTo(Personne p) {
		return this.getNom().compareTo(p.getNom());
	}

	@Override
	public String toString() {
		return getNom() + " " + getPrenom() + " " + getTel();
	}

	// Get
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getTel() {
		return tel;
	}

	// Set
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
