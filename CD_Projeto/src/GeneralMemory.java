
import genclass.TextFile;

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
    private int dishesPerStudent;           //número de pratos por estudante

    private TextFile writer;    //utilizado para escrever o log
    private Chef.state chefStatus;
    private Waiter.state waiterStatus;
    private Student.state[] studentStatus;
    private String file;

    /**
     * Construtor de GeneralMemory.
     *
     * @param n int que indica o número de estudantes.
     * @param d int que indica o número de pratos por estudante.
     * @param path String que indica o path a considerar para escrever o log.
     * @param fic String que indica o nome do ficheiro a considerar para escrever o log.
     */
    public GeneralMemory(int n, int d, String path, String fic) {
        file = fic;
        chefStatus = Chef.state.WAITING_FOR_AN_ORDER;
        waiterStatus = Waiter.state.APPRAISING_SITUATION;
        studentStatus = new Student.state[n];
        for (int z = 0; z < n; z++) {
            studentStatus[z] = Student.state.GOING_TO_THE_RESTAURANT;
        }
        numberOfStudents = n;
        dishesPerStudent = d;
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
    }

    /**
     * Retorna o número de pratos por estudante.
     *
     * @return int que indica o número de pratos a processar por estudante.
     */
    public synchronized int getDishPerStudents() {
        return this.dishesPerStudent;
    }

    /**
     * Retorna o número de estudantes no restaurante.
     *
     * @return int que indica o número estudantes a considerar.
     */
    public synchronized int getNumStudents() {
        return this.numberOfStudents;
    }

    /**
     * Fornece a opção de fazer log num ficheiro previamente definido.
     * @param entity Object que representa a entidade que quer fazer o log.
     */
    public synchronized void log(Object entity) {
        if(entity instanceof Chef){
            chefStatus = ((Chef) (entity)).getStatus();
        }
        else if(entity instanceof Waiter){
            waiterStatus = ((Waiter) (entity)).getStatus();
        }
        else if(entity instanceof Student){
            int id = ((Student) (entity)).getID();
            studentStatus[id-1] = ((Student) (entity)).getStatus();
        }
        else if(entity instanceof Integer){
            String line = "Chef can prepare the course nº "+(Integer)entity+".";
            writer.writelnString(line);
            return;
        }
        String line = "";
        line += String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
        for (int z = 0; z < numberOfStudents; z++) {
            line += String.format("%-29s", studentStatus[z].toString());
        }
        writer.writelnString(line);
    }
    
    /**
     * Fecha o ficheiro do log.
     */
    public synchronized void closeLog() {
        writer.close();
    }

}
