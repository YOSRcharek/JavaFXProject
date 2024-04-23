package test;

import Model.Service;
import Repository.Servicerepo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Main2  extends Application {

   /* private static final Servicerepo serviceService;

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
        System.out.println("categorie_id");
        int categorieId =scanner.nextInt();
        scanner.nextLine();
        System.out.println("commentaire_id");
        int commentaire_id =scanner.nextInt();
        scanner.nextLine();
        System.out.println("association_id_id");
        int association_id =scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nom du service : ");
        String nomService = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Disponibilité (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine();  // pour consommer le saut de ligne



        Service service = new Service(categorieId,commentaire_id,association_id,nomService, description, disponibilite);

        serviceService.ajouterService(service);

        System.out.println("Service ajouté avec succès !");
    }

    private static void modifierService() throws SQLException {
        System.out.print("ID du service à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();  // pour consommer le saut de ligne

        System.out.print("Nom du service : ");
        String nomService = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Disponibilité (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine();  // pour consommer le saut de ligne

        Service service = new Service(id, nomService, description, disponibilite, 0, 2, 0);  // 0 pour les autres ID
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



*/



        @Override
   public void start(Stage primaryStage) throws Exception {
       // Charger le fichier FXML
       Parent root = FXMLLoader.load(getClass().getResource("/categorieview.fxml"));

       // Créer une nouvelle scène
       Scene scene = new Scene(root);

       // Définir la scène et afficher la fenêtre principale
       primaryStage.setScene(scene);
       primaryStage.setTitle("Gestion des catégories");
       primaryStage.show();
   }

    public static void main(String[] args) {
        launch(args);
    }
}


