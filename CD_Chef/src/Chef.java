
/**
 * Entidade Chef. Entidade em que o seu lifecycle replica o de um Chef, sendo esse o papel desta entidade no problema.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Chef extends Thread {

    /**
     * Enumerado que mantém listado todos os estados possíveis para o Chef.
     */
    /*public static enum state {
        WAITING_FOR_AN_ORDER,
        PREPARING_THE_COURSE,
        DISHING_THE_PORTIONS,
        DELIVERING_THE_PORTIONS,
        CLOSING_SERVICE
    };*/

    private String st=null;       //estado interno do chef
    
    //zonas partilhadas
    private KitchenStub kitchen;
    private BarStub bar;
    
    //info
    private int N=7,       //número de estudantes
                M=3;      //número de pratos por estudante

    /**
     * Construtor da entidade Chef.
     * @param k Kitchen que indica a referência para a zona partilhada Kitchen a considerar.
     * @param b Bar que indica a referência para a zona partilhada Bar a considerar.
     */
    public Chef(KitchenStub k, BarStub b) {
        kitchen = k;
        bar = b;
    }

    /**
     * Retorna o estado atual do Chef.
     * @return state que indica qual o estado atual do Chef.
     */
    public String getStatus(){
        return this.st;
    }
    
    /**
     * Renova o estado atual do Chef.
     * @param state String.
     */
    public void setStatus(String state){
        st=state;
    }
    
    /**
     * Representa o lifecycle de cada entidade criada deste tipo.
     */
    @Override
    public void run() {
            kitchen.watchTheNews();
            kitchen.startPreparation();
            for (int i = 0; i < M; i++) {
                kitchen.proceedToPresent();
                //bar.enableFoodReady();
                for (int s = 0; s < N; s++) {
                    if(i==0 || s!=0){
                        bar.alertTheWaiter();
                    }
                    
                    if (!kitchen.haveAllPortionsBeenDelivered()) {
                        kitchen.haveNextPortionReady();
                    }
                }
                
                if (!kitchen.hasTheOrderBeenCompleted()) {
                    kitchen.continuePreparation();
                }
            }
            kitchen.cleanUp();
    }
}
