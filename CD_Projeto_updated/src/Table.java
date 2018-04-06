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
    private GeneralMemory mem;
    private int N;
    private int atTable=0, choosen=0, finishedEating=0, served=0;
    
    private boolean noMenu=true, descOrder=true, chat=true, bill=true, waitPay=true, allFinished=false;
    
    public Table(GeneralMemory m){
        mem=m;
        this.N=m.getNumStudents();
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
        atTable=atTable+1;
        mem.enablePresentMenu();
        noMenu=true;
        return atTable;
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
        
    }
    
    public synchronized void giveOrder(){
        mem.enableTakeOrder();
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
        try{
            Thread.sleep((long)(1+80*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not serving portion.");
        }
        served+=1;
        notifyAll();
        
        if(served==N){
        	allFinished=true;
        	chat=false;
            notifyAll();
            
            try{
                while(allFinished){
                    wait();
                }
            }catch(InterruptedException e){
                throw new MyException("Error: Not serving.");
            }
            
        }
    }

    public synchronized void chat() throws MyException{
        
    	try{
	        while(allFinished){
	            wait();
	        }
	    }catch(InterruptedException e){
	        throw new MyException("Error: Not waiting for everyone.");
	    }
    	

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
        notifyAll();
        
        return finishedEating;
            
    }

    public synchronized void getBill() {
    	allFinished=false;
    	chat=false;
    	notifyAll();
    	mem.enableGetBill();
    }

    public synchronized void presentBill() {
        bill=false;
        notifyAll();
    }
    
    public synchronized void payBill() throws MyException{
    	try{
            while(bill){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not getting bill.");
        }
        try{
            Thread.sleep((long)(1+140*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not paying bill.");
        }
        
        waitPay=false;
        notifyAll();
        
    }
    
    public synchronized void waitPayment() throws MyException{
        try{
            while(waitPay){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not receiving payment.");
        }
    }

    public synchronized void allFinished() {
    	chat=true;
    	allFinished=false;
    	notifyAll();
        finishedEating=0;
        served=0;
    }
    
    public synchronized void goHome() throws MyException{
    	try{
            while(waitPay){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not waiting for honoring the payment.");
        }
    }
    
}
