package com.example.application;

import com.example.application.model.Events;
import com.example.application.model.TypeEvent;
import com.example.application.repository.EventRepo;
import com.example.application.repository.TypeEventRepo;

import java.time.LocalDate;
import java.util.Date;


import java.sql.Connection;
import java.util.Date;

public class MainEvent {
    public static void main(String[] args) {
      /*  Events event = new Events(
                1, // ID de l'événement
                "Nom de l'événement",
                "Description de l'événement",
                new Date(), // Date de début
                new Date(), // Date de fin
                "Localisation de l'événement",
                100, // Capacité maximale
                0, // Capacité actuelle
                "Image de l'événement",
                0.0f, // Latitude
                0.0f, // Longitude
                null // TypeEvent (remplissez-le avec une instance appropriée si nécessaire)
        );

        //

        // Appel de la méthode ajouter avec l'instance d'événement créée
        eventRepo.ajouter(event);*/

       /* TypeEvent typeEvent = new TypeEvent();
        typeEvent.setNom("mayssa");

        // Création d'une instance de TypeEventRepo
        TypeEventRepo typeEventRepo = new TypeEventRepo();

        // Appel de la méthode ajouter avec l'objet TypeEvent
        typeEventRepo.ajouter(typeEvent);*/
       /* EventRepo eventRepo = new EventRepo();

        // Création d'un objet Events avec des valeurs au hasard
        Events eventToUpdate = new Events(
                56,                           // ID de l'événement à modifier
                "Nouveau nom d'événement",  // Nouveau nom de l'événement
                "Nouvelle description",      // Nouvelle description
                LocalDate.now(),            // Nouvelle date de début (aujourd'hui)
                LocalDate.now().plusDays(7), // Nouvelle date de fin (une semaine à partir d'aujourd'hui)
                "Nouvelle localisation",     // Nouvelle localisation
                100,                         // Nouvelle capacité maximale
                50,                          // Nouvelle capacité actuelle
                "Nouvelle image",            // Nouvelle image
                0.0f,                        // Nouvelle latitude
                0.0f                         // Nouvelle longitude
        );

        // Appel de la méthode modifier avec l'événement à mettre à jour
        eventRepo.modifier(eventToUpdate);*/

    }
}
