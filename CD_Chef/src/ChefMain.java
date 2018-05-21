
import java.util.*;
import genclass.*;

/**
 * Main do Chef.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class ChefMain {

    /**
     * Função main.
     *
     * @param args String[] de possíveis argumentos passados à função main.
     */
    public static void main(String[] args) {

        int M = 7;
        int N = 3;

        System.out.println("Initializing all threads...");
        KitchenStub kitchen = new KitchenStub("l040101-ws03.ua.pt", 22503);
        BarStub bar = new BarStub("l040101-ws01.ua.pt", 22501);
        Chef chefe = new Chef(kitchen, bar);

        System.out.println("Launching all threads...");
        chefe.start();
        try {
            chefe.join();
        } catch (InterruptedException e) {
            System.err.println("Caught Exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Ending...");
    }

}
