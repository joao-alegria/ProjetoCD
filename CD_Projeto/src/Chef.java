/**
 * Entidade Chef. Entidade em que o seu lifecycle replica o de um Chef, sendo esse o papel desta entidade no problema.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Chef extends Thread {

    /**
     * Enumerado que mantém listado todos os estados possíveis para o Chef.
     */
    static enum state {
        WAITING_FOR_AN_ORDER,
        PREPARING_THE_COURSE,
        DISHING_THE_PORTIONS,
        DELIVERING_THE_PORTIONS,
        CLOSING_SERVICE
    };

    private state st;       //estado interno do chef
    
    //zonas partilhadas
    private Kitchen kitchen;
    private Bar bar;
    private GeneralMemory mem;
    
    //info
    private int N,       //número de estudantes
                M,      //número de pratos por estudante
                meal;   //qual a refeiçao que esta a preparar

    /**
     * Construtor da entidade Chef.
     * @param k Kitchen que indica a referência para a zona partilhada Kitchen a considerar.
     * @param b Bar que indica a referência para a zona partilhada Bar a considerar.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMemory a considerar.
     */
    public Chef(Kitchen k, Bar b, GeneralMemory m) {
        st = state.WAITING_FOR_AN_ORDER;
        kitchen = k;
        bar = b;
        mem = m;
        M = m.getDishPerStudents();
        N = m.getNumStudents();
        meal=0;
    }

    /**
     * Retorna o estado atual do Chef.
     * @return state que indica qual o estado atual do Chef.
     */
    public state getStatus(){
        return this.st;
    }
    
    /**
     * Representa o lifecycle de cada entidade criada deste tipo.
     */
    @Override
    public void run() {
        int meal=1;
        try {
            kitchen.watchTheNews();
            st = state.PREPARING_THE_COURSE;
            mem.log(meal);
            mem.log(this);
            kitchen.startPreparation();
            for (int i = 0; i < M; i++) {
                st = state.DISHING_THE_PORTIONS;
                mem.log(this);
                kitchen.proceedToPresent();
                //bar.enableFoodReady();
                for (int s = 0; s < N; s++) {
                    st = state.DELIVERING_THE_PORTIONS;
                    mem.log(this);
                    if(i==0 || s!=0){
                        bar.alertTheWaiter();
                    }
                    
                    if (!kitchen.haveAllPortionsBeenDelivered()) {
                        st = state.DISHING_THE_PORTIONS;
                        mem.log(this);
                        kitchen.haveNextPortionReady();
                    }
                }
                
                if (!kitchen.hasTheOrderBeenCompleted()) {
                    meal++;
                    mem.log(meal);
                    st = state.PREPARING_THE_COURSE;
                    mem.log(this);
                    kitchen.continuePreparation();
                }
            }
            st = state.CLOSING_SERVICE;
            mem.log(this);
            kitchen.cleanUp();
        } catch (MyException e) {
            System.err.println(e);
        }
    }
}
