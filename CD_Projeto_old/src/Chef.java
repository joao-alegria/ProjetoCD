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
    private Order order;
    private int M, N;
    
    public Chef(Kitchen k, Bar b, Order o){
        this.st=state.WAITING_FOR_AN_ORDER;
        this.kitchen=k;
        this.bar=b;
        this.order=o;
        this.M=o.getDishPerStudents();
        this.N=o.getNumStudents();
    }
    
    @Override
    public void run(){
        try{
            kitchen.watchNews();
            kitchen.startPrep();st=state.PREPARING_THE_COURSE;
            for(int i=0; i<M; i++){
                kitchen.proceedToPresent();st=state.DISHING_THE_PORTIONS;
                for(int s=0; s<N; s++){
                    bar.signalWaiter();st=state.DELIVERING_THE_PORTIONS;
                    //kitchen.standBy();
                    if(!kitchen.allPortionsDelivered()){
                            kitchen.haveNextPortionReady();st=state.DISHING_THE_PORTIONS;
                    }
                }
                if(!kitchen.allOrdersDelivered()){
                    kitchen.contPrep();st=state.PREPARING_THE_COURSE;
                }
            kitchen.cleanup();st=state.CLOSING_SERVICE;
            }
        }catch(MyException e){
            System.out.println(e);
        }
    }
}