package Servers;

import Actions.Gerer1Client;
import ListeAuth.ListeAuth;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends Thread{
  private ListeAuth la;
  private boolean haveAdditionalRight;

  /**
   * Constructeur de la classe représente un serveur TCP
   * @param la : la liste d'authentification
   * @param haveAdditionalRight : booléan qui implique si un client possède d'autre droit que CHECK
   */
  public ServeurTCP(ListeAuth la,boolean haveAdditionalRight){
    this.la = la;
    this.haveAdditionalRight = haveAdditionalRight;
  }
  
  
  private void runServeur() {
    try {
      // Création d'un socket serveur générique sur le port 40000
      ServerSocket ssg = new ServerSocket(40000);

      // Création d'un socket client et connexion avec un serveur logger fonctionnant sur la même machine et sur le port 40000
      Socket sc = new Socket("localhost", 3244);
      
      while(true) {
        // On attend une connexion puis on l'accepte
        Socket sss = ssg.accept();
        System.out.println("Connect to client TCP Checker : " + sss);
        
        Gerer1Client g1c = new Gerer1Client(sc,sss, la, "TCP", haveAdditionalRight);
        g1c.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  };
  
  public void run(){
    this.runServeur();
  }
}
