/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria eu mesmo 2
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
    private GeneralMemory mem;
    
    public Waiter(Kitchen k, Bar b, Table t, GeneralMemory m){
        st=state.APPRAISING_SITUATION;
        kitchen=k;
        bar=b;
        table=t;
        mem=m;
    }
    
    
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
                        //kitchen.returnBar();
                        break;

                    case takeOrder:
                        st=state.TAKING_THE_ORDER;
                        mem.logWaiterState(st);
                        table.getOrder();
                        kitchen.handOrder();
                        bar.returnBar();
                        //kitchen.returnBar();
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