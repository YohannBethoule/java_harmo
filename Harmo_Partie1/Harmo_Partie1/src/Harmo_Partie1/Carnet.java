package Harmo_Partie1;

import java.io.*;
import java.util.Scanner;

public class Carnet implements Serializable {
	private Personne personnes[];
	private int count;

	public Carnet(int taille) {
		this.personnes = new Personne[taille];
		this.count = 0;
	}

	public void ajouterPersonne() throws Exception {
		if (personnes.length == count) {
			throw new Exception("Le carnet est deja plein");
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Saisir nom : ");
		String nom = sc.nextLine();
		System.out.print("Saisir prenom : ");
		String prenom = sc.nextLine();
		System.out.print("Saisir tel : ");
		String tel = sc.nextLine();
		Personne personne = new Personne(nom, prenom, tel);
		personnes[count] = personne;
		count++;
	}

	public void supprimerPersonne(int index) throws Exception {
		if (count == 0 || index > count) {
			throw new Exception("Le carnet est vide");
		}
		personnes[index] = personnes[count - 1];
		count--;
	}

	public String recherchePersonne(String nom) {
		String s = "";

		for (int i = 0; i < count; i++) {
			if (personnes[i].getNom().equals(nom)) {
				s += personnes[i] + "\n";
			}
		}
		if (s.equals("")) {
			System.out.println(nom + " n'existe pas dans le carnet");
		}
		return s;
	}

	public void triCarnet() {
		for (int i = 0; i < count; i++) {
			for (int j = 1; j < count - i; j++) {
				if (personnes[j].compareTo(personnes[j - 1]) < 0) { // j<j-1
					Personne tmp = personnes[j];
					personnes[j] = personnes[j - 1];
					personnes[j - 1] = tmp;
				}
			}
		}
	}

	@Override
	public String toString() {
		String s = "Mon carnet d'adresse (contient " + count + " personnes): \n";
		for (int i = 0; i < count; i++) {
			s += "personne n°" + (i + 1) + " : " + personnes[i] + "\n";
		}
		return s;
	}

	public Personne getPersonne(int index) {
		return personnes[index];
	}

	public int getCount() {
		return count;
	}

	public Personne[] getPersonnes() {
		return personnes;
	}

	public void setPersonnes(Personne[] tab) {
		personnes = tab;
	}

	public void sauvegarde() {
		try {
			FileOutputStream file = new FileOutputStream("save.txt");
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(this);

			out.close();
			file.close();

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		}
	}

	public void chargement() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("save.txt");
		ObjectInputStream in = new ObjectInputStream(file);
		Carnet c = (Carnet) in.readObject();
		this.personnes = c.getPersonnes();
		this.count = c.getCount();
		System.out.println("Object has been deserialized ");

		in.close();
		file.close();
	}

}
