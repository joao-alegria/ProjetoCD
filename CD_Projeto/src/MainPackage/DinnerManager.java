package MainPackage;
import Entities.*;
import SharedRegions.*;
import java.util.*;
import genclass.*;
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
        String path, fic;
        String OS=System.getProperty("os.name");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Em que diretótio pretende que o ficheiro seja escrito(full path)?\n->");
        path=sc.nextLine();
        
        
        if(OS.startsWith("Linux")){
            while(!FileOp.isDirectory((path.substring(0, path.lastIndexOf("/"))) , (path.substring(path.lastIndexOf("/"), path.length())))){
                System.err.println("Directório Inválido! Insira outra vez por favor.");
                System.out.print("Em que diretório pretende que o ficheiro seja escrito(full path)?\n->");
                path=sc.nextLine(); 
            }
        }
        else if(OS.startsWith("Windows")){
            while(!FileOp.isDirectory((path.substring(0, path.lastIndexOf("\\"))) , (path.substring(path.lastIndexOf("\\"), path.length())))){
                System.err.println("Directório Inválido! Insira outra vez por favor.");
                System.out.print("Em que diretório pretende que o ficheiro seja escrito(full path)?\n->");
                path=sc.nextLine(); 
            }
        }
        
        System.out.print("Qual deve ser o nome do ficheiro?\n->");
        fic=sc.nextLine();
        
        System.out.print("O programa correrá com 7 Estudantes com 3 pratos para cada um.\nPretende alterar esses valores?(s/n)\n->");
        String control=sc.next();
        if(control.toLowerCase()=="s" || control.toLowerCase() == "sim"){
            System.out.print("Quantos Estudantes pretende ter?\n->");
            M=sc.nextInt();
            System.out.print("Quantos pratos por Estudante pretende ter?\n->");
            N=sc.nextInt();
        }
        
        
        
        System.out.println("Initializing all threads...");
        GeneralMemory mem = new GeneralMemory(M, N, path, fic);
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
