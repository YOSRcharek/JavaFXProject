package test;

import Model.Service;
import Repository.Servicerepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Servicerepo serviceService;

    static {
        try {
            serviceService = new Servicerepo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean quitter = false;

        while (!quitter) {
            System.out.println("\nMenu :");
            System.out.println("1. Ajouter un service");
            System.out.println("2. Modifier un service");
            System.out.println("3. Supprimer un service");
            System.out.println("4. Afficher tous les services");
            System.out.println("5. Filtrer par catégorie");
            System.out.println("6. Quitter");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();  // pour consommer le saut de ligne

            try {
                switch (choix) {
                    case 1:
                        ajouterService();
                        break;
                    case 2:
                        modifierService();
                        break;
                    case 3:
                        supprimerService();
                        break;
                    case 4:
                        afficherServices();
                        break;
                    case 5:
                        // Ajouter le filtrage par catégorie ici si nécessaire
                        System.out.println("Filtrage par catégorie :");
                        break;
                    case 6:
                        quitter = true;
                        break;
                    default:
                        System.out.println("Choix invalide !");
                        break;
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Au revoir !");
    }

    private static void ajouterService() throws SQLException {
        System.out.print("ID de la catégorie : ");
        int categorie_Id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ID du commentaire : ");
        int commentaire_Id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ID d' association : ");
        int association_Id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nom du service : ");
        String nom_service = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Disponibilité (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine();  // pour consommer le saut de ligne



        Service service = new Service(categorie_Id,commentaire_Id,association_Id,nom_service,description, disponibilite );
        serviceService.ajouterService(service);

        System.out.println("Service ajouté avec succès !");
    }


    private static void modifierService() throws SQLException {
        // pour consommer le saut de ligne

        System.out.print("Nom du service : ");
        String nomService = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("ID du service à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Disponibilité (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine();  // pour consommer le saut de ligne

        System.out.print("ID de la catégorie : ");
        int categorieId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID du commentaire : ");
        int commentaireId = scanner.nextInt();
        scanner.nextLine();

        Service service = new Service(id, nomService, description, disponibilite, categorieId, commentaireId);
        serviceService.modifierService(service);

        System.out.println("Service modifié avec succès !");
    }

    private static void supprimerService() throws SQLException {
        System.out.print("ID du service à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();  // pour consommer le saut de ligne

        serviceService.supprimerService(id);
        System.out.println("Service supprimé avec succès !");
    }

    private static void afficherServices() throws SQLException {
        List<Service> services = serviceService.recupererServices();
        System.out.println("Liste des services :");
        for (Service service : services) {
            System.out.println(service);
        }
    }
}
