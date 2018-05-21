package MainPackage;
import genclass.*;

/**
 * Entidade GeneralMemory.
 * Tipo de dados que corresponde a uma entidade com a informação geral, 
 * que passa pelo número de clientes no restaurante e o número de pratos por cada um. 
 * Este tipo de dados é também quem fornece a funcionalidade de fazer log dos vários estados das diversas entidades.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
    public class GeneralMemory {

    private int numberOfStudents;           //número de estudantes
    //private int dishesPerStudent;           //número de pratos por estudante

    private TextFile writer;    //utilizado para escrever o log
    private String chefStatus;
    private String waiterStatus;
    private String[] studentStatus;
    private String file, path;

    /**
     * Construtor de GeneralMemory.
     *
     * @param n int que indica o número de estudantes.
     * @param d int que indica o número de pratos por estudante.
     * @param path String que indica o path a considerar para escrever o log.
     * @param fic String que indica o nome do ficheiro a considerar para escrever o log.
     */
    public GeneralMemory(int n, String p, String fic) {
        file = fic;
        chefStatus = "";
        waiterStatus = "";
        studentStatus = new String[n];
        for (int z = 0; z < n; z++) {
            studentStatus[z] = "";
        }
        numberOfStudents = n;
        //dishesPerStudent = d;
        path=p;
        writer = new TextFile();
        writer.openForWriting(path, file);
        String header = String.format("%-28s%-23s", "Chef", "Waiter");
        for (int z = 0; z < n; z++) {
            header += String.format("%-29s", "Student " + (z + 1));
        }
        String rest = "";
        for (int z = 0; z < header.length() / 2 - "Restaurante".length(); z++) {
            rest += " ";
        }
        writer.writelnString(rest + "Restaurante");
        writer.writelnString(header);
        writer.close();
    }
    
    public synchronized void studentLog(String state, int id) {
        writer.openForAppending(path, file);
        
        studentStatus[id-1] = state;
        String line = "";
        line += String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
        for (int z = 0; z < numberOfStudents; z++) {
            line += String.format("%-29s", studentStatus[z].toString());
        }
        writer.writelnString(line);
        writer.close();
    }
    
    public synchronized void chefLog(String state) {
        writer.openForAppending(path, file);
        
        chefStatus = state;
        String line = "";
        line += String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
        for (int z = 0; z < numberOfStudents; z++) {
            line += String.format("%-29s", studentStatus[z].toString());
        }
        writer.writelnString(line);
        writer.close();
    }
    
    public synchronized void waiterLog(String state) {
        writer.openForAppending(path, file);
        
        waiterStatus = state;
        String line = "";
        line += String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
        for (int z = 0; z < numberOfStudents; z++) {
            line += String.format("%-29s", studentStatus[z].toString());
        }
        writer.writelnString(line);
        writer.close();
    }

}
