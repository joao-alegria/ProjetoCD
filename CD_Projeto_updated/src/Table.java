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
    private int atTable=0, choosen=0, finishedEating=0, served=0;
    
    private boolean noMenu=true, descOrder=true, chat=true, bill=true, waitPay=true, menuWait=false;
    private boolean waiter=false;
    
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
    
    public synchronized int enterRestaurant() throws MyException{
        try{
            while(waiter){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not entering.");
        }
        waiter=true;
        int aux=atTable;
        atTable=atTable+1;
        order.enablePresentMenu();
        noMenu=true;
        return aux;
    }
    
    public synchronized void waitMenu() throws MyException{
        try{
            while(noMenu){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not waiting for menu.");
        }
    }

    public synchronized void readMenu() throws MyException{
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not reading menu.");
        }
        if(atTable==N) notifyAll();
        try{
            while(atTable<N){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not waiting after reading menu.");
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
    
    public synchronized void giveOrder(){
        waiter=true;
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
        if(served==0){
            chat=false;
            notifyAll();
        }
        try{
            Thread.sleep((long)(1+80*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not serving portion.");
        }
        served+=1;

        
        if(served==N){
            try{
                while(finishedEating!=N){
                    wait();
                }
            }catch(InterruptedException e){
                throw new MyException("Error: Not serving.");
            }
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

    public synchronized void returnBar(){
        waiter=false;
        notifyAll();
    }

    public synchronized void chat() throws MyException{
        chat=true;
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
        finishedEating=finishedEating+1;
        
        /*try{
            while(finishedEating<N){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not waiting after eating.");
        }*/
        System.out.println(finishedEating + " " + N);
        notifyAll();
        return finishedEating;
            
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

    public synchronized void allFinished() {
        //order.enableFoodReady();
        finishedEating=0;
        served=0;
    }
    
}
