import Servers.ServeurLog;

/**
 * Classe à démarrer au première
 */
public class MainLog {
  public static void main(String[] args){
    ServeurLog slog = new ServeurLog();

    slog.start();

  }
}
