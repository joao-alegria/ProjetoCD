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
            	st=state.APPRAISING_SITUATION;
            	order.log("Waiter: "+st.toString());
                switch(bar.lookAround()){
                    case presentMenu:
                        st=state.PRESENTING_THE_MENU;
                        order.log("Waiter: "+st.toString());
                        table.saluteClient();
                        bar.returnBar();
                        //kitchen.returnBar();
                        break;

                    case takeOrder:
                        st=state.TAKING_THE_ORDER;
                        order.log("Waiter: "+st.toString());
                        table.getOrder();
                        kitchen.handOrder();
                        bar.returnBar();
                        //kitchen.returnBar();
                        break;

                    case foodReady:
                        st=state.WAITING_FOR_PORTION;
                        order.log("Waiter: "+st.toString());
                        //for(int s=0; s<N; s++){
                            kitchen.collectPortion();
                            table.servePortion();
                            bar.returnBar();
                        //}
                        //table.returnBar();
                        break;

                    case getBill:
                        st=state.PROCESSING_THE_BILL;
                        order.log("Waiter: "+st.toString());
                        order.log("Waiter: "+st.toString());
                        bar.prepareBill();
                        table.presentBill();
                        table.waitPayment();
                        run=false;
                        break;

                }
                
            }
        }catch(MyException e){
            System.out.println(e);
        }
    }
}
