/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria eu 
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
                        System.out.println(state.PRESENTING_THE_MENU.toString());
                        st=state.PRESENTING_THE_MENU;
                        table.saluteClient();
                        bar.returnBar();
                        //kitchen.returnBar();
                        break;

                    case takeOrder:
                        System.out.println(state.TAKING_THE_ORDER.toString());
                        st=state.TAKING_THE_ORDER;
                        table.getOrder();
                        kitchen.handOrder();
                        bar.returnBar();
                        //kitchen.returnBar();
                        break;

                    case foodReady:
                        System.out.println(state.WAITING_FOR_PORTION.toString());
                        st=state.WAITING_FOR_PORTION;
                        //for(int s=0; s<N; s++){
                            kitchen.collectPortion();
                            table.servePortion();
                            bar.returnBar();
                        //}
                        //table.returnBar();
                        break;

                    case getBill:
                        System.out.println(state.PROCESSING_THE_BILL.toString());
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
