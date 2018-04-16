/**
 * Entidade Student. Entidade em que o seu lifecicle replica o de um Estudante, sendo esse o papel desta entidade no problema.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Student extends Thread{

    /**
     * Simula o tempo que demora a um Estudante ir para o restaurante.
     * @throws MyException
     */
    private void walk() throws MyException{
        try{
            Thread.sleep((long)(1+300*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not walking.");
        }
    }
    
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
            
            int pos=table.enterRestaurant();
            bar.enablePresentMenu();
            
            bar.signalWaiter();
            st= state.TAKING_A_SEAT_AT_THE_TABLE;
            mem.logStudentState(st, ID);
            table.waitMenu();
            
            
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
            
            st= state.SELECTING_THE_COURSES;
            mem.logStudentState(st, ID);
            table.readMenu();
            table.choose();
            if(first){
                while(!table.allChose()){
                    st= state.ORGANIZING_THE_ORDER;
                    mem.logStudentState(st, ID);
                    table.prepareOrder();
                }
                table.giveOrder();
                bar.enableTakeOrder();
                bar.signalWaiter();
                table.describeOrder();
            }
            st= state.CHATTING_WITH_COMPANIONS;
            mem.logStudentState(st, ID);
            table.chat();                           // comida chegou alertado pelo waiter
            for(int i=0; i<M; i++){                 //3 vezes
            	st= state.ENJOYING_THE_MEAL;
            	mem.logStudentState(st, ID);
                int pos_eating=table.eat();
                if(i<M-1){
                    if(pos_eating==N){
                        table.allFinished();
                        st= state.CHATTING_WITH_COMPANIONS;
                        mem.logStudentState(st, ID);
                        table.chat();
                    }else{
                    	st= state.CHATTING_WITH_COMPANIONS;
                    	mem.logStudentState(st, ID);
                        table.chat();
                    }
                }
            }
            if(last){
            	st= state.PAYING_THE_MEAL;
            	mem.logStudentState(st, ID);
            	table.getBill();
                bar.enableGetBill();
                bar.signalWaiter();
                table.payBill();
                table.goHome();
                st= state.GOING_HOME;
                mem.logStudentState(st, ID);
            }else{
                table.goHome();
                st= state.GOING_HOME;
                mem.logStudentState(st, ID);
            }
        }catch(MyException e){
            System.err.println(e);
        }
    }
}
