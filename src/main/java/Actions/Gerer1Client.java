package Actions;
import JSON.JsonLogger;
import ListeAuth.ListeAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Gerer1Client extends Thread{
  private Socket sss;
  private ListeAuth la;
  private boolean haveAdditionalRight;
  private String proto;
  private Socket socketLog;
  private String stringToLog;

  /**
   * La classe qui gère l'intéraction entre client et serveur
   * @param socketLog : le socket de connection entre serveur Logger et client AS
   * @param sss : le socket de connection entre serveur AS et client
   * @param la : liste d'authentification
   * @param proto : le protocol
   * @param haveAdditionalRight : boolean qui est vrai si Client Manager, false si non
   */
  public Gerer1Client(Socket socketLog,Socket sss, ListeAuth la, String proto, boolean haveAdditionalRight){
    super();
    this.sss = sss;
    this.la = la;
    this.haveAdditionalRight = haveAdditionalRight;
    this.proto = proto;
    this.socketLog = socketLog;
  }

  public void run(){
    this.travail();
  }
  public void travail() {
    try {
      // Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
      BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));
      
      String chaine = "";
      while(chaine != null) {
        // lecture d'une chaine envoyée à travers la connexion socket
        chaine = entreeSocket.readLine();
        Gerer1Demande g1d = new Gerer1Demande(la,proto,haveAdditionalRight);
        chaine = g1d.travail(chaine);
        
        // Envoyer les données à enregistrer vers le serveur Log
        //  private String[] stringToLog;
        this.stringToLog = g1d.getStringToLog(); // récupérer les donénées pour logger (proto,type,login et result)
        stringToLog = sss.getLocalAddress().toString()+"/"+sss.getLocalPort()+"/"+stringToLog;

        // si la chaine reçu du client est nulle c'est que le client a fermé la connexion
        if (chaine != null) {
          // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
          PrintStream sortieSocket = new PrintStream(sss.getOutputStream());
          sortieSocket.println(chaine); // on envoie la chaine au client

          PrintStream sortieSocketLog = new PrintStream(socketLog.getOutputStream());
          sortieSocketLog.println(stringToLog); // on envoie la chaine au serveur Log
          this.logger(); // Le logger est éffectué ici
        }
      }
      // on ferme nous aussi la connexion
      socketLog.close();
      sss.close();
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public String getStringToLog(){
    return this.stringToLog;
  }

  public void logger(){
    String[] stringToLog = this.getStringToLog().split("/");
    JsonLogger.log(stringToLog[1],Integer.parseInt(stringToLog[2]),stringToLog[3],stringToLog[4],stringToLog[5],stringToLog[6]);
  }
}
