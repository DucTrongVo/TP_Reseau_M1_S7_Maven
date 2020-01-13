package Servers;

import Actions.Gerer1Client;
import JSON.JsonLogger;
import ListeAuth.ListeAuth;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe représente le serveur TCP qui prend en compte un numéro de port dans paramètre
 */
public class ServeurTCPM extends Thread{
  /**
   * Constructor
   * @param la : liste authentication
   * @param portNum : numéro de port
   */
  private ListeAuth la;
  private int numPort;
  private boolean haveAdditionalRight;
  private JsonLogger logger;
  public ServeurTCPM(ListeAuth la, int numPort, boolean haveAdditionalRight){
    this.la = la;
    this.numPort = numPort;
    this.haveAdditionalRight = haveAdditionalRight;
  }
  public void run(){
    try {
      // Création d'un socket serveur générique sur le numéro de port donnée
      ServerSocket ssg = new ServerSocket(numPort);

      // Création d'un socket client et connexion avec un serveur logger fonctionnant sur la même machine et sur le port 40000
      Socket sc = new Socket("localhost", 3244);
      
      while(true) {
        // On attend une connexion puis on l'accepte
        Socket sss = ssg.accept();
        System.out.println("Connect to client TCP Manager : "+sss);
        
        // Gérer le client
        Gerer1Client g1c = new Gerer1Client(sc,sss,la,"TCP", haveAdditionalRight);
        g1c.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
