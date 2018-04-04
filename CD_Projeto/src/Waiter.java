/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Waiter extends Thread{
    
    static enum state{
        APPRAISING_SITUATION,
        PRESENTING_THE_MENU,
        TAKING_THE_ORDER,
        PLACING_THE_ORDER,
        WAITING_FOR_PORTION,
        PROCESSING_THE_BILL
    };
    
    private state st;
    private Table table;
    private Kitchen kitchen;
    private Bar bar;
    private Order order;
    private int N;
    
    public Waiter(Kitchen k, Bar b, Table t, Order o){
        this.st=state.APPRAISING_SITUATION;
        this.kitchen=k;
        this.bar=b;
        this.table=t;
        this.order=o;
        this.N=o.getNumStudents();
    }
    
    
    @Override
    public void run(){
        try{
            boolean run=true;
            while(run){
                switch(bar.lookAround()){
                    case presentMenu:
                        st=state.PRESENTING_THE_MENU;
                        table.saluteClient();
                        break;

                    case takeOrder:
                        st=state.TAKING_THE_ORDER;
                        table.getOrder();
                        kitchen.handOrder();
                        break;

                    case foodReady:
                        st=state.WAITING_FOR_PORTION;
                        for(int s=0; s<N; s++){
                            kitchen.collectPortion();
                            table.servePortion();
                        }
                        break;

                    case getBill:
                        st=state.PROCESSING_THE_BILL;
                        bar.prepareBill();
                        table.presentBill();
                        table.waitPayment();
                        run=false;
                        break;

                    //case cleanUp:
                    //    bar.clean();
                    //    run=false;
                }
            }
        }catch(MyException e){
            System.out.println(e);
        }
    }
}
