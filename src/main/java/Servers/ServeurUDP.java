package Servers;

import Actions.Gerer1Demande;
import JSON.JsonLogger;
import ListeAuth.ListeAuth;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe qui représente un serveur UDP
 */
public class ServeurUDP extends Thread{
  private ListeAuth la;
  private boolean haveAdditionalRight;
  private JsonLogger logger;

  /**
   * constructeur de la classe
   * @param la : la liste d'authentification
   * @param haveAdditionalRight : booléan qui implique si un client possède d'autre droit que CHECK
   */
  public ServeurUDP(ListeAuth la, boolean haveAdditionalRight){
    this.la = la;
    this.haveAdditionalRight = haveAdditionalRight;
  }
  public void run(){
    try {
      // Création d'un socket UDP sur le port 40000
      DatagramSocket socket = new DatagramSocket(40000);
      // tampon pour recevoir les données des datagrammes UDP
      final byte[] tampon = new byte[1024];

      // objet Java permettant de recevoir un datagramme UDP
      DatagramPacket dgram = new DatagramPacket(tampon, tampon.length);

      // Création d'un socket client et connexion avec un serveur logger fonctionnant sur la même machine et sur le port 40000
      Socket sc = new Socket("localhost", 3244);
      
      
      while(true) {
        // attente et réception d'un datagramme UDP
        socket.receive(dgram);

        // extraction des données
        String chaine = new String(dgram.getData(), 0, dgram.getLength());

        System.out.println("Chaine reçue : "+chaine);
        // Traiter la demande client
        Gerer1Demande g1d = new Gerer1Demande(la,"UDP",haveAdditionalRight);
        // String qui reçu le résultat
        String res = g1d.travail(chaine);

        // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
        PrintStream sortieSocketLog = new PrintStream(sc.getOutputStream());
        
        // Données à récupérer (proto,type,login,result)
        String stringReceived = g1d.getStringToLog();
        if (stringReceived != null) {
          sortieSocketLog.println(stringReceived); // on envoie la chaine au serveur Log
          this.logger(stringReceived,socket);
        }
        
        dgram.setData(res.getBytes());

        // on renvoie le message au client
        socket.send(dgram);

        // on replace la taille du tampon au max
        // elle a été modifiée lors de la réception
        dgram.setData(tampon);
        //dgram.setLength(tampon.length);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void logger(String stringReceived,DatagramSocket socket){
    String[] stringToLog = stringReceived.split("/");
    JsonLogger.log(socket.getLocalAddress().toString(),socket.getLocalPort(),stringToLog[0],stringToLog[1],stringToLog[2],stringToLog[3]);
  }

}
