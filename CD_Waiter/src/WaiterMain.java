import java.util.*;
import genclass.*;
/**
 * Classe de teste da aplicação. Classe que vai despoletar o desenrolar das ações do restaurante.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class WaiterMain {

    /**
     * Função main.
     * @param args String[] de possíveis argumentos passados à função main.
     */
    public static void main(String[] args) {
        
        int M = 7;
        int N = 3;

        System.out.println("Initializing all threads...");
        KitchenStub kitchen = new KitchenStub("l040101-ws03.ua.pt", 22503);
        BarStub bar = new BarStub("l040101-ws01.ua.pt", 22501);
        TableStub table = new TableStub("l040101-ws02.ua.pt", 22502);
        Waiter empregado = new Waiter(kitchen, bar, table);
        
        System.out.println("Launching all threads...");
        empregado.start();
        try {
                empregado.join();
        } catch (InterruptedException e) {
                System.err.println("Caught Exception: " + e.getMessage());
                System.exit(1);
        }
        System.out.println("Ending...");
    }

}

