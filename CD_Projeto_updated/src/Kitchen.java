/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Kitchen {
    private Order order;
    private boolean news=true, standBy=true;
    private int portionsDelivered=0, ordersDelivered=0, portions=0;
    private int N, M;
    
    //rivate boolean waiter=true;
    
    public Kitchen(Order o){
        order=o;
        this.N=o.getNumStudents();
        this.M=o.getDishPerStudents();
    }
    
    public synchronized void watchNews() throws MyException{
        try{
            while(news){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not watching the news.");
        }
    }
    
    public synchronized void startPrep() throws MyException{
        try{
            Thread.sleep((long)(1+150*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not preparing");
        }
        ordersDelivered+=1;
    }
    
    public synchronized void proceedToPresent() throws MyException{
        try{
            Thread.sleep((long)(1+40*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not presenting");
        }
        portions+=1;
        
        order.enableFoodReady();
    }
    
    //public synchronized void standBy() throws MyException{
    //    try{
    //        while(standBy){
    //            wait();
    //        }
    //    }catch(InterruptedException e){
    //        throw new MyException("Error: Not standing by.");
    //    }
    //}
    
    public synchronized boolean allPortionsDelivered(){
        if(portions==N){
            return true;
        }else{
            return false;
        }
    }
    
    /*public synchronized void returnBar(){
        waiter=false;
        notifyAll();
    }*/
    
    public synchronized void haveNextPortionReady() throws MyException{
        try{
            Thread.sleep((long)(1+40*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not presenting");
        }
        portions+=1;
        notifyAll();
        
        /*try{
            while(waiter){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not presenting because waiter ocupied.");
        }
        
        waiter=true;*/
        
        order.enableFoodReady();
    }
    
    public synchronized boolean allOrdersDelivered(){
    	System.out.println(ordersDelivered + " "+M);
        if(ordersDelivered==M){
            return true;
        }else{
            return false;
        }
    }
    
    public synchronized void contPrep() throws MyException{
        try{
            Thread.sleep((long)(1+150*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not preparing");
        }
        ordersDelivered+=1;
        portionsDelivered=0;
    }
    
    public synchronized void cleanup()  throws MyException{
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not cleaning");
        }
    }

    public synchronized void handOrder() { //waiter
        news=false;
        notifyAll();
    }

    public synchronized void collectPortion() throws MyException{ //waiter
        try{
            while(portions<=portionsDelivered){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not collecting portion.");
        }
        portionsDelivered+=1;
    }
}
