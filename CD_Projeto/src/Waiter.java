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
    
    //info
    private int N,              //número de estudantes
                goodbye;        //número de estudantes que foram embora
    
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
        N=m.getNumStudents();
        goodbye=0;
    }
    
    /**
     * Retorna o estado atual do Waiter.
     * @return state que indica qual o estado atual do Waiter.
     */
    public state getStatus(){
        return this.st;
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
            	mem.log(this);
                switch(bar.lookAround()){
                    case presentMenu:
                        st=state.PRESENTING_THE_MENU;
                        mem.log(this);
                        table.saluteTheClient();
                        bar.returnToTheBar();
                        
                        break;

                    case takeOrder:
                        st=state.TAKING_THE_ORDER;
                        mem.log(this);
                        table.getThePad();
                        kitchen.handTheNoteToTheChef();
                        bar.returnToTheBar();
                        break;

                    case foodReady:
                        st=state.WAITING_FOR_PORTION;
                        mem.log(this);
                        
                        kitchen.collectPortion();
                        table.deliverPortion();
                        bar.returnToTheBar();

                        break;

                    case getBill:
                        st=state.PROCESSING_THE_BILL;
                        mem.log(this);
                        bar.prepareTheBill();
                        table.presentTheBill();
                        bar.returnToTheBar();
                        break;
                        
                    case end:
                        table.sayGoodbye();
                        goodbye+=1;
                        if(goodbye==N){
                            run=false;
                        }else{
                            bar.returnToTheBar();
                        }
                        break;
                }
            }
            
        }catch(MyException e){
            System.err.println(e);
        }
    }
}
