package Dons.services;

import java.util.List;


public interface ITypeDonsCrud <T>{



        public void ajouterType(T p) ;
        public List<T> afficherType() ;
        public void modifierType(T p) ;
        public  void supprimerType(T p) ;

}
