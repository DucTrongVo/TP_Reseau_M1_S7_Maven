package Servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe qui représente un serveur de Log. L'idéé est
 * d'éffectuer le JsonLogger ici. Problème: le serveur ne peut pas
 * prendre plus d'1 client à la fois --> délégué la fonction logger au client
 */
public class ServeurLog extends Thread{
  // Constructor
  public ServeurLog(){
  }

  public void run(){
    try {
      // Création d'un socket serveur générique sur le port 3244
      ServerSocket ssg = new ServerSocket(3244);
      
      while(true) {
        Socket sss = ssg.accept();
        // On attend une connexion puis on l'accepte
        System.out.println("Connect to client (serveur) AS : " + sss);

        // Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
        BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));
        
        String stringReceived = "";
        while ((stringReceived = entreeSocket.readLine()) != null){
          String[] stringToLog  = stringReceived.split("/");
          //JsonLogger.log(stringToLog[1],Integer.parseInt(stringToLog[2]),stringToLog[3],stringToLog[4],stringToLog[5],stringToLog[6]);
        }
        sss.close();
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
