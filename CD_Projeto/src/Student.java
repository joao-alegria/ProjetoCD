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

    private void walk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void enterRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void goHome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
    private boolean first;
    private boolean last;
    private state st;
    
    
    public Student(Bar bar, Table table){
        this.st= state.GOING_TO_THE_RESTAURANT;
        this.table = table;
        this.bar = bar;
    }
    
    
    @Override
    public void run(){
        walk();
        table.sit();
        table.readMenu();
        if(last){
            table.full();
        }
        table.choose();
        if(first){
            while(!table.allChose()){
                table.prepareOrder();
            }
            table.prepareOrder();
            bar.signalWaiter();
            table.describeOrder();
        }
        table.chat();	// comida chegou alertado pelo waiter
        for(int i=0; i<N; i++){ //3 vezes
            table.eat();
            if(table.lastToEat() && i<N-1){
                bar.signalWaiter();
                table.chat();
            }
        }
        if(table.lastArriving){
            bar.signalWaiter();
            table.payBill();
            table.waitReceipt();
            goHome();
        }else{
            goHome();
        }
    }
}
