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
     * Retorna o estado atual do Student.
     * @return state que indica qual o estado atual do Student.
     */
    public state getStatus(){
        return this.st;
    }
    
    /**
     * Retorna o ID do Student.
     * @return int que indica qual o ID do Student.
     */
    public int getID(){
        return this.ID;
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
            st= state.TAKING_A_SEAT_AT_THE_TABLE;
            System.out.println(st);
            mem.log(this);
            bar.enablePresentMenu();
            //bar.signalWaiter();
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
            System.out.println(st);
            mem.log(this);
            table.readMenu();
            table.choose();
            if(first){
                while(!table.allChose()){
                    st= state.ORGANIZING_THE_ORDER;
                    System.out.println(st);
                    mem.log(this);
                    table.prepareOrder();
                }
                table.giveOrder();
                bar.enableTakeOrder();
                System.out.println("all");
                //bar.signalWaiter();
                table.describeOrder();
            }
            st= state.CHATTING_WITH_COMPANIONS;
            System.out.println(st);
            mem.log(this);
            table.chat();                           // comida chegou alertado pelo waiter
            for(int i=0; i<M; i++){                 //3 vezes
            	st= state.ENJOYING_THE_MEAL;
                System.out.println(st);
            	mem.log(this);
                int pos_eating=table.eat();
                if(i<M-1){
                    if(pos_eating==N){
                        table.allFinished();
                        st= state.CHATTING_WITH_COMPANIONS;
                        System.out.println(st);
                        mem.log(this);
                        table.chat();
                    }else{
                    	st= state.CHATTING_WITH_COMPANIONS;
                        System.out.println(st);
                    	mem.log(this);
                        table.chat();
                    }
                }
            }
            if(last){
            	st= state.PAYING_THE_MEAL;
                System.out.println(st);
            	mem.log(this);
            	table.getBill();
                bar.enableGetBill();
                //bar.signalWaiter();
                System.out.println("antes pay");
                table.payBill();
                System.out.println("depois pay");
                table.goHome();
                st= state.GOING_HOME;
                System.out.println(st);
                mem.log(this);
            }else{
                table.goHome();
                st= state.GOING_HOME;
                System.out.println(st);
                mem.log(this);
            }
        }catch(MyException e){
            System.err.println(e);
        }
    }
}
