/**
 * Entidade Student. Entidade em que o seu lifecicle replica o de um Estudante, sendo esse o papel desta entidade no problema.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Student extends Thread{
    
    /**
     * Enumerado que mantém listado todos os estados possíveis para o Student.
     */
    static enum state {
        GOING_TO_THE_RESTAURANT, 
        TAKING_A_SEAT_AT_THE_TABLE, 
        SELECTING_THE_COURSES,
        ORGANIZING_THE_ORDER,
        CHATTING_WITH_COMPANIONS,
        ENJOYING_THE_MEAL,
        PAYING_THE_MEAL,
        GOING_HOME
    };
    
    private state st;           //estado interno do Student
    
    //zonas partilhadas
    private Table table;
    private Bar bar;
    private GeneralMemory mem;
    
    //informações necessárias
    private boolean first;      //true se é o primeiro
    private boolean last;       //true se é o ultímo
    private int N,      //número de estudantes
                M,      //número de pratos por estudante
                ID;     //identificador do estudante
    
    
    /**
     * Construtor da entidade Student.
     * @param b Bar que indica a referência para a zona partilhada Bar a considerar.
     * @param t Table que indica a referência para a zona partilhada Table a considerar.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMmory a considerar.
     * @param id int que indica qual o ID do Estudante.
     */
    public Student(Bar b, Table t, GeneralMemory m, int id){
        st= state.GOING_TO_THE_RESTAURANT;
        table = t;
        bar = b;
        mem=m;
        N=m.getNumStudents();
        M=m.getDishPerStudents();
        ID=id;
    }
    
    /**
     * Representa o lifecicle de cada entidade criada deste tipo.
     */
    @Override
    public void run(){
        try{
            walk();
            
            st= state.TAKING_A_SEAT_AT_THE_TABLE;
            mem.log(this);
            bar.enter();
            int pos=table.enter();
            
            //bar.signalWaiter();
            //table.waitMenu();
            
            
            if(pos==1){
                this.first=true;
                this.last=false;
            }else if(pos==N){
                this.first=false;
                this.last=true;
            }else{
                this.first=false;
                this.last=false;
            }
            
            
            table.readTheMenu();
            
            if(last){
                bar.callTheWaiter();
            }
            
            if(first){
                while(!table.hasEverybodyChosen()){
                    st= state.ORGANIZING_THE_ORDER;
                    mem.log(this);
                    table.prepareTheOrder();
                }
                table.describeTheOrder();
            }else{
                st= state.SELECTING_THE_COURSES;
                mem.log(this);
                table.informCompanion();
            }
            st= state.CHATTING_WITH_COMPANIONS;
            mem.log(this);
            table.joinTheTalk();
            for(int i=0; i<M; i++){
                table.startEating();
                st= state.ENJOYING_THE_MEAL;
            	mem.log(this);
                table.endEating();
                if(i<M-1){
                    if(table.hasEverybodyFinished()){
                        bar.signalTheWaiter();
                    }
                    st= state.CHATTING_WITH_COMPANIONS;
                    mem.log(this);
                    table.joinTheTalk();
                }
            }
            if(last){
            	st= state.PAYING_THE_MEAL;
            	mem.log(this);
                bar.shouldHaveArrivedEarlier();
                table.honorTheBill();
                
            }
            st= state.GOING_HOME;
            mem.log(this);
            table.goHome();
            bar.exit();
        }catch(MyException e){
            System.err.println(e);
        }
    }
    
    /**
     * Simula o tempo que demora a um Estudante ir para o restaurante.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    private void walk() throws MyException{
        try{
            Thread.sleep((long)(1+300*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not walking.");
        }
    }
    
    /**
     * Retorna o estado atual do Estudante.
     * @return state que indica qual o estado atual do Student.
     */
    public state getStatus(){
        return this.st;
    }
    
    /**
     * Retorna o ID do Estudante.
     * @return int que indica qual o ID do Student.
     */
    public int getID(){
        return this.ID;
    }
    
}
