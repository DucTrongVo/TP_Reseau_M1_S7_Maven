import ListeAuth.ListeAuth;
import Servers.ServeurTCP;
import Servers.ServeurTCPM;
import Servers.ServeurUDP;

/**
 * Classe à démarrer après le démarrage du MainLog
 */
public class Main {
  public static void main(String[] args){
    // Initialiser une liste authentification
    ListeAuth la = new ListeAuth();
    // Initialiser un serveur TCP
    ServeurTCP tcp = new ServeurTCP(la,false);
    // Initialiser un serveur udp
    ServeurUDP udp = new ServeurUDP(la,false);
    // Initialiser un serveur tcp avec numéro de port défini
    ServeurTCPM tcpm = new ServeurTCPM(la,28415,true);

    tcp.start();
    tcpm.start();
    udp.start();

  }
}
