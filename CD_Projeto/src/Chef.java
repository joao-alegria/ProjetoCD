/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Chef extends Thread{
    
    static enum state{
        WAITING_FOR_AN_ORDER,
        PREPARING_THE_COURSE,
        DISHING_THE_PORTIONS,
        DELIVERING_THE_PORTIONS,
        CLOSING_SERVICE
    };
    
    private state st;
    private Kitchen kitchen;
    private Bar bar;
    
    public Chef(Kitchen kit, Bar bar){
        this.st=state.WAITING_FOR_AN_ORDER;
        this.kitchen=kit;
        this.bar=bar;
    }
    
    @Override
    public void run(){
        kitchen.watchNews();
        kitchen.startPrep();
        for(int i=0; i<M; i++){
            kitchen.proceedToPresent();
            for(int s=0; s<N; s++){
		        bar.signalWaiter();
		        kitchen.standBy();
		        if(!kitchen.allPortionsDelivered()){
			        kitchen.haveNextPortionReady();
                }
            }
            if(!kitchen.allOrdersDelivered()){
                kitchen.contPrep();
            }
        kitchen.cleanup();
        }
    }
}