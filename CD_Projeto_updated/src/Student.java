/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Student extends Thread{

    private void walk() throws MyException{
        try{
            Thread.sleep((long)(1+300*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not walking.");
        }
    }

    /*private void goHome() throws MyException{
        try{
            Thread.sleep((long)(1+200*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not going home.");
        }
    }*/
    
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
    
    private Table table;
    private Bar bar;
    private GeneralMemory mem;
    private boolean first;
    private boolean last;
    private state st;
    private int N, M, ID;
    
    public Student(Bar b, Table t, GeneralMemory m, int id){
        st= state.GOING_TO_THE_RESTAURANT;
        table = t;
        bar = b;
        mem=m;
        N=m.getNumStudents();
        M=m.getDishPerStudents();
        ID=id;
        mem.log("Creating Student "+id+"...");
    }
    
    
    @Override
    public void run(){
        try{
        	mem.log("Student "+ID+": "+st.toString());
            walk();
            st= state.TAKING_A_SEAT_AT_THE_TABLE;
            mem.log("Student "+ID+": "+st.toString());
            int pos=table.enterRestaurant();
            bar.signalWaiter();
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
            mem.log("Student "+ID+": "+st.toString());
            table.readMenu();
            //if(last){
            //    table.full();
            //}
            table.choose();
            if(first){
                while(!table.allChose()){
                	st= state.ORGANIZING_THE_ORDER;
                	mem.log("Student "+ID+": "+st.toString());
                    table.prepareOrder();
                }
                table.giveOrder();
                bar.signalWaiter();
                table.describeOrder();
            }
            st= state.CHATTING_WITH_COMPANIONS;
            mem.log("Student "+ID+": "+st.toString());
            table.chat();	// comida chegou alertado pelo waiter
            for(int i=0; i<M; i++){ //3 vezes
            	st= state.ENJOYING_THE_MEAL;
            	mem.log("Student "+ID+": "+st.toString());
                int pos_eating=table.eat();
                if(i<M-1){
                    if(pos_eating==N){
                        table.allFinished();
                        //bar.signalWaiter();
                        st= state.CHATTING_WITH_COMPANIONS;
                        mem.log("Student "+ID+": "+st.toString());
                        table.chat();
                    }else{
                    	st= state.CHATTING_WITH_COMPANIONS;
                    	mem.log("Student "+ID+": "+st.toString());
                        table.chat();
                    }
                }
            }
            if(last){
            	st= state.PAYING_THE_MEAL;
            	mem.log("Student "+ID+": "+st.toString());
            	table.getBill();
                bar.signalWaiter();
                table.payBill();
                table.goHome();
                st= state.GOING_HOME;
                mem.log("Student "+ID+": "+st.toString());
            }else{
                table.goHome();
                st= state.GOING_HOME;
                mem.log("Student "+ID+": "+st.toString());
            }
        }catch(MyException e){
            System.out.println(e);
        }
    }
}
