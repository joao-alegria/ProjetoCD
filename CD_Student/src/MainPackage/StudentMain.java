package MainPackage;

import SharedRegionStub.*;
import Entities.*;
/**
 * Classe de main do Student. Classe que vai despoletar o desenrolar das ações do estudante.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class StudentMain {

    /**
     * Função main.
     * @param args String[] de possíveis argumentos passados à função main.
     */
    public static void main(String[] args) {
        
        int M = 7;
        int N = 3;
        
        
        System.out.println("Initializing all threads...");
        //GeneralMemoryStub mem = new GeneralMemoryStub(M, N, path, fic);
        BarStub bar = new BarStub("l040101-ws01.ua.pt", 22501);
        TableStub table = new TableStub("l040101-ws02.ua.pt", 22502);
        Student[] estudantes = new Student[M];
        for (int i = 0; i < M; i++) {
                estudantes[i] = new Student(bar, table, i + 1);
        }
        System.out.println("Launching all threads...");
        for (int i = 0; i < M; i++) {
                estudantes[i].start();
        }
        for (int t = 0; t < M; t++) {
                try {
                        estudantes[t].join();
                } catch (Exception e) {
                        System.err.println("Caught Exception: " + e.getMessage());
                        System.exit(1);
                }
        }
        System.out.println("Ending...");
    }

}
