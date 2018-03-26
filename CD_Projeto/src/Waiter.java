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
    
    public Waiter(Kitchen kit, Bar bar, Table tab){
        this.st=state.APPRAISING_SITUATION;
        this.kitchen=kit;
        this.bar=bar;
        this.table=tab;
    }
    
    
    @Override
    public void run(){
        boolean run=true;
        while(run){
            switch(bar.lookAround()){
		case presentMenu:
                    table.saluteClient();
                    break;

                case takeOrder:
                    table.getOrder();
                    kitchen.handOrder();
                    break;

                case foodReady:
                    for(int s=0; s<N; s++){
                        kitchen.collectPortion();
                        table.servePortion();
                    }
                    break;

                case getBill:
                    bar.prepareBill();
                    table.presentBill();
                    table.waitPayment();
                    break;

                case cleanUp:
                    bar.clean();
                    run=false;
            }
        }
    }
}
