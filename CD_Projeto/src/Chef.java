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
    private GeneralMemory mem;
    private int M, N;
    
    public Chef(Kitchen k, Bar b, GeneralMemory m){
        st=state.WAITING_FOR_AN_ORDER;
        kitchen=k;
        bar=b;
        mem=m;
        M=m.getDishPerStudents();
        N=m.getNumStudents();
    }
    
    @Override
    public void run(){
        try{
            mem.logChefState(st);
            kitchen.watchNews();
            st=state.PREPARING_THE_COURSE;
            mem.logChefState(st);
            kitchen.startPrep();
            for(int i=0; i<M; i++){
            	st=state.DISHING_THE_PORTIONS;
            	mem.logChefState(st);
                kitchen.proceedToPresent();
                bar.enableFoodReady();
                for(int s=0; s<N; s++){
                    st=state.DELIVERING_THE_PORTIONS;
                    mem.logChefState(st);
                    bar.signalWaiter();//st=state.DELIVERING_THE_PORTIONS;
                    //kitchen.standBy();
                    if(!kitchen.allPortionsDelivered()){
                        st=state.DISHING_THE_PORTIONS;
                        mem.logChefState(st);
                        kitchen.haveNextPortionReady();
                        bar.enableFoodReady();
                    
                        //bar.signalWaiter();//st=state.DELIVERING_THE_PORTIONS;
                    }
                }
                if(!kitchen.allOrdersDelivered()){
                	st=state.PREPARING_THE_COURSE;
                	mem.logChefState(st);
                    kitchen.contPrep();
                }
            }
            st=state.CLOSING_SERVICE;
            mem.logChefState(st);
            kitchen.cleanup();
        }catch(MyException e){
            System.err.println(e);
        }
    }
}