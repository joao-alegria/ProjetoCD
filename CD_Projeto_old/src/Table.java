/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Table {
    private Order order;
    private int M, N;
    //private boolean lastMeal;
    //private boolean lastArriving;
    private int atTable=0, choosen=0, finishedEating=0;
    
    private boolean noMenu=true, descOrder=true, chat=true, bill=true, waitPay=true;
    
    public Table(Order o){
        order=o;
        this.M=o.getDishPerStudents();
        this.N=o.getNumStudents();
    }

    public synchronized void saluteClient() throws MyException{
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not saluting client.");
        }
        noMenu=false;
        notifyAll();
        
    }

    public synchronized void getOrder() throws MyException{
        try{
            while(descOrder){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not getting order.");
        }
    }

    public synchronized void servePortion() throws MyException{
        chat=false;
        notifyAll();
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not serving portion.");
        }
    }

    public synchronized void presentBill() {
        bill=false;
        notifyAll();
    }

    public synchronized void waitPayment() throws MyException{
        try{
            while(waitPay){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not chating.");
        }
    }

    public synchronized int enterRestaurant() throws MyException{
        int aux=atTable++;
        order.enablePresentMenu();
        try{
            while(noMenu){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not watching the news.");
        }
        noMenu=true;
        return aux;
    }
    /*
    public synchronized void waitMenu() throws MyException{
        try{
            while(noMenu){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not waiting for menu.");
        }
    }*/

    public synchronized void readMenu() throws MyException{
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not reading menu.");
        }
    }

    public synchronized void choose() throws MyException{
        try{
            Thread.sleep((long)(1+120*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not reading menu.");
        }
        choosen+=1;
        notifyAll();
    }

    public synchronized boolean allChose() {
        if(choosen==N){
            return true;
        }else{
            return false;
        }
    }

    public synchronized void prepareOrder() throws MyException{
        try{
            while(choosen<N){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not preparing the order.");
        }
        
        order.enableTakeOrder();
    }

    public synchronized void describeOrder() throws MyException{
        try{
            Thread.sleep((long)(1+80*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not dercribing order.");
        }
        
        descOrder=false;
        notifyAll();
    }

    public synchronized void chat() throws MyException{
        try{
            while(chat){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not chating.");
        }
    }

    public synchronized int eat() throws MyException{
        try{
            Thread.sleep((long)(1+250*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not eating.");
        }
        int aux=finishedEating+1;
        return aux;
            
    }

    //public synchronized boolean allFinished() {
    //    order.enableFoodReady();
    //    finishedEating=0;
    //}

    public synchronized void payBill() throws MyException{
        try{
            while(bill){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not paying bill.");
        }
        
        try{
            Thread.sleep((long)(1+140*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not paying bill.");
        }
        
        waitPay=false;
        notifyAll();
        
    }
    
}
