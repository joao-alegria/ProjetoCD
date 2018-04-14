import genclass.TextFile;

/**
 * Tipo de dados que corresponde a uma entidade com a informação geral, que passa pelo número de clientes no restaurante e o número de pratos por cada um.
 * Este tipo de dados é também quem fornece ao waiter a informação de qual é o evento que ele deve fazer.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class GeneralMemory {
	private int numberOfStudents;
	private int dishesPerStudent;
	
	private TextFile writer;    //utilizado para escrever o log
        private Chef.state chefStatus;
        private Waiter.state waiterStatus;
        private Student.state[] studentStatus;
        private String file;
        
        /**
         * Construtor de GeneralMemory.
         * @param n int que indica o número de estudantes
         * @param d int que indica o número de pratos por estudante
         * @param path String que indica o path a considerar para escrever o log
         */
	public GeneralMemory(int n, int d, String path, String fic) {
                file=fic;
                chefStatus=Chef.state.WAITING_FOR_AN_ORDER;
                waiterStatus=Waiter.state.APPRAISING_SITUATION;
                studentStatus= new Student.state[n];
                for(int z=0; z<n; z++){
                    studentStatus[z]=Student.state.GOING_TO_THE_RESTAURANT;
                }
		numberOfStudents = n;
		dishesPerStudent = d;
		writer = new TextFile();
		writer.openForWriting(path, file);
                String header = String.format("%-28s%-23s", "Chef", "Waiter");
                for(int z=0; z<n; z++){
                    header+=String.format("%-29s", "Student "+(z+1));
                }
                writer.writelnString(header);
	}
        
	public synchronized int getDishPerStudents() {
		return this.dishesPerStudent;
	}

	public synchronized int getNumStudents() {
		return this.numberOfStudents;
	}

        public synchronized void logChefState(Chef.state st) {
                chefStatus=st;
                String line="";
                line+=String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
                for(int z=0; z<numberOfStudents; z++){
                    line+=String.format("%-29s", studentStatus[z].toString());
                }
		writer.writelnString(line);
	}
        
        public synchronized void logWaiterState(Waiter.state st) {
                waiterStatus=st;
                String line="";
                line+=String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
                for(int z=0; z<numberOfStudents; z++){
                    line+=String.format("%-29s", studentStatus[z].toString());
                }
		writer.writelnString(line);
	}
        
	public synchronized void logStudentState(Student.state st, int id) {
                studentStatus[id-1]=st;
                String line="";
                line+=String.format("%-28s%-23s", chefStatus.toString(), waiterStatus.toString());
                for(int z=0; z<numberOfStudents; z++){
                    line+=String.format("%-29s", studentStatus[z].toString());
                }
		writer.writelnString(line);
	}

	public synchronized void closeLog() {
		writer.close();
	}

}