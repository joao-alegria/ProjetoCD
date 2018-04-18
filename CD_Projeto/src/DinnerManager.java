/**
 * Classe de teste da aplicação. Classe que vai despoletar o desenrolar das ações do restaurante.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class DinnerManager {

    /**
     * Função main.
     * @param args String[] de possíveis argumentos passados à função main.
     */
    public static void main(String[] args) {
        
        int M = 7;
        int N = 3;

        System.out.println("Initializing all threads...");
        GeneralMemory mem = new GeneralMemory(M, N, "/home/joao-alegria/Desktop", "log1.txt");
        Kitchen kitchen = new Kitchen(mem);
        Bar bar = new Bar(mem);
        Table table = new Table(mem);
        Chef chefe = new Chef(kitchen, bar, mem);
        Waiter empregado = new Waiter(kitchen, bar, table, mem);
        Student[] estudantes = new Student[M];
        for (int i = 0; i < M; i++) {
                estudantes[i] = new Student(bar, table, mem, i + 1);
        }

        System.out.println("Launching all threads...");
        chefe.start();
        empregado.start();
        for (int i = 0; i < M; i++) {
                estudantes[i].start();
        }
        try {
                chefe.join();
                empregado.join();
        } catch (InterruptedException e) {
                System.err.println("Caught Exception: " + e.getMessage());
                System.exit(1);
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
        mem.closeLog();

        
    }

}
