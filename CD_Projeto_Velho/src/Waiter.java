/**
 * Entidade Waiter. Entidade em que o seu lifecicle replica o de um Empregado, sendo esse o papel desta entidade no problema.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Waiter extends Thread{
    
    /**
     * Enumerado que mantém listado todos os estados possíveis para o Waiter.
     */
    static enum state{
        APPRAISING_SITUATION,
        PRESENTING_THE_MENU,
        TAKING_THE_ORDER,
        PLACING_THE_ORDER,
        WAITING_FOR_PORTION,
        PROCESSING_THE_BILL
    };
    
    private state st;           //estado interno do Waiter
    
    //zonas partilhadas
    private Table table;
    private Kitchen kitchen;
    private Bar bar;
    private GeneralMemory mem;
    
    /**
     * Construtor da entidade Waiter.
     * @param k Kitchen que indica a referência para a zona partilhada Kitchen a considerar.
     * @param b Bar que indica a referência para a zona partilhada Bar a considerar.
     * @param t Table que indica a referência para a zona partilhada Table a considerar.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMmory a considerar.
     */
    public Waiter(Kitchen k, Bar b, Table t, GeneralMemory m){
        st=state.APPRAISING_SITUATION;
        kitchen=k;
        bar=b;
        table=t;
        mem=m;
    }
    
    /**
     * Representa o lifecicle de cada entidade criada deste tipo.
     */
    @Override
    public void run(){
        try{
            boolean run=true;
            while(run){
            	st=state.APPRAISING_SITUATION;
            	mem.logWaiterState(st);
                switch(bar.lookAround()){
                    case presentMenu:
                        st=state.PRESENTING_THE_MENU;
                        mem.logWaiterState(st);
                        table.saluteClient();
                        bar.returnBar();
                        
                        break;

                    case takeOrder:
                        st=state.TAKING_THE_ORDER;
                        mem.logWaiterState(st);
                        table.getOrder();
                        kitchen.handOrder();
                        bar.returnBar();
                        break;

                    case foodReady:
                        st=state.WAITING_FOR_PORTION;
                        mem.logWaiterState(st);

                        kitchen.collectPortion();
                        table.servePortion();
                        bar.returnBar();

                        break;

                    case getBill:
                        st=state.PROCESSING_THE_BILL;
                        mem.logWaiterState(st);
                        bar.prepareBill();
                        table.presentBill();
                        table.waitPayment();
                        run=false;
                        break;
                }
            }
        }catch(MyException e){
            System.err.println(e);
        }
    }
}
