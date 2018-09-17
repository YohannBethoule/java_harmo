package Harmo_Partie1;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static Scanner sc;

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		// variables
		sc = new Scanner(System.in);
		String nom;
		int choix = 0;
		int suppr;
		Carnet carnet = new Carnet(50);

		do {
			// MENU
			do {
				System.out.println("\n1. Ajouter une fiche \n2. Supprimer une fiche \n3. Rechercher une fiche \n4. Trier le tableau \n5.Afficher carnet\n6. Sauvegarder carnet\n7.Charger le carnet\n8. Quitter");
				try {
					choix = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("Veuillez renseigner un nombre ");
				}
			} while (choix != 1 && choix != 2 && choix!=3 && choix !=4 && choix!=5 && choix != 6 && choix !=7 && choix!= 8);

			switch (choix) {

			case 1:
				// Ajout d'une personne crée methode
				try {
					carnet.ajouterPersonne();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

				break;

			case 2:
				// suppression d'une personne
				System.out.println(carnet);
				try {
					System.out.println("numéro de la personne a supprimer : ");

					try {
						suppr = Integer.parseInt(sc.nextLine()) - 1;
						carnet.supprimerPersonne(suppr);
					} catch (NumberFormatException e) {
						System.err.println("Veuillez renseigner un nombre ");
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

				break;

			case 3:
				// recherche une personne
				System.out.print("Saisir nom : ");
				nom = sc.nextLine();
				System.out.println(carnet.recherchePersonne(nom));

				break;

			case 4:
				// trier le carnet
				carnet.triCarnet();
				break;

			case 5:
				// Affiche le carnet
				System.out.println(carnet);
				break;

			case 6:
				carnet.sauvegarde();
				// sauvegarde du carnet
				break;

			case 7:
				// récupéré le carnet
				carnet.chargement();
				break;

			default:
				System.out.print("A bientôt !");
			}
		} while (choix != 8);
	}
}
