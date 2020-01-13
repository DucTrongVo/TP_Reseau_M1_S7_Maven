package Actions;

import ListeAuth.ListeAuth;

import java.util.ArrayList;
import java.util.Arrays;


public class Gerer1Demande extends Thread{
  /**
   * La classe qui traite les demandes du clients
   * @param la : variable représente la listeAuthentification
   * @param haveAdditionalRight : variable de type booléan qui est vrai si c'est un client Manager, false si non
   * @param proto : protocol utilisé par le client
   * @param listeRequetes : array qui contient les requetes standard prédéfinis (des mots clés)
   * @param stringToLog : string qui contient les informations pour logger
   */
  private ListeAuth la;
  private String proto;
  private boolean haveAdditionalRight;
  private static ArrayList<String> listeRequetes = new ArrayList<String>(Arrays.asList("CHK","ADD","DEL","MOD","FIN")); // Liste des mots clés d'actions
  private String stringToLog = "";

  /**
   * constructor de la classe
   * @param la
   * @param proto
   * @param haveAdditionalRight
   */
  public Gerer1Demande (ListeAuth la, String proto, boolean haveAdditionalRight){
    this.la = la;
    this.haveAdditionalRight = haveAdditionalRight;
    this.proto = proto;
  }

  /**
   * Fonction qui retourne un String en résultat de la demande du client
   * @param requete demande du client
   * @return String qui contient le résultat du demande client
   */
  public String travail(String requete) {
    // Initialiser une variable locale qui contiendra la réponse pour le client
    String res = null;
    // Initialiser une variable locale qui contiendra la résultat du requête (true/false)
    boolean r;
    //as.Exemples.ListeAuth.ListeAuth la = new as.Exemples.ListeAuth.ListeAuth();

    // si elle est nulle c'est que le client a fermé la connexion
    if (requete != null) {
      String[] chaineRecu = requete.split(" ");
      // La requête ne commence pas par un des mots clés
      if (!listeRequetes.contains(chaineRecu[0])) {
        res = "Input format not corrected";
        // La requête d'arreter
      } else if (chaineRecu[0].equals(listeRequetes.get(4))){
        res = "FIN";
      } // La requête donc un des 2 champs nécessaires sont vides
      else if (chaineRecu[1].isEmpty() || chaineRecu[2].isEmpty()) {
        res = "LogIn or Password cannot be empty";
      } else {
        // traiter la demande de CHECK les identifiants
        if (chaineRecu[0].equalsIgnoreCase(listeRequetes.get(0))) {
          res = la.tester(chaineRecu[1], chaineRecu[2]) ? "GOOD" : "BAD";
        }
        else{
         // traiter les autres demandes
          if (haveAdditionalRight) { // Quand c'est un client Manager
            // traiter la demande d'AJOUTER un couple ID
            if (chaineRecu[0].equalsIgnoreCase(listeRequetes.get(1))) {
              r = la.creer(chaineRecu[1], chaineRecu[2]);
              res = r ? "DONE" : "ERROR : le login est déjà présent";
            }
            // traiter la demande de VIRER un couple ID
            if (chaineRecu[0].equalsIgnoreCase(listeRequetes.get(2))) {
              r = la.supprimer(chaineRecu[1], chaineRecu[2]);
              res = r ? "DONE" : "ERROR : le login ou le mot de passe ne sont pas corrects";
            }
            // traiter la demande de MODIFIER un mdp
            if (chaineRecu[0].equalsIgnoreCase(listeRequetes.get(3))) {
              r = la.mettreAJour(chaineRecu[1], chaineRecu[2]);
              res = r ? "DONE" : "ERROR : le login n'est pas présent";
            }
          }else{ // Quand c'est un client CHECKER
            String req ;
            switch(chaineRecu[0]){
              case "ADD": req = "ajouter un utilisateur";break;
              case "DEL": req = "supprimer un utilisateur";break;
              case "MOD": req = "modifier un utilisateur";break;
              default: req = "à cette action";
            }
            res = "Vous n'avez le droit à "+req;
          }
        }
      }
      String stringToLog = res.equals("FIN") ? this.proto+"/FIN/null/LOGGED OUT" : this.proto+"/"+chaineRecu[0]+"/"+chaineRecu[1]+"/"+res; // String contient les donnés à logger
      this.setStringToLog(stringToLog);
    }
    return res;
  }
  
  public String getStringToLog(){
    return this.stringToLog;
  }
  
  private void setStringToLog( String stringToLog){
    this.stringToLog = stringToLog;
  }
}
