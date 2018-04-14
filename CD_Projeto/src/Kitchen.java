/**
 * 
 * @author joao-alegria
 */
public class Kitchen {
    private GeneralMemory mem;
    private boolean news=true;
    private int portionsDelivered=0, ordersDelivered=0, portions=0;
    private int N, M;
    
    
    public Kitchen(GeneralMemory m){
        mem=m;
        this.N=m.getNumStudents();
        this.M=m.getDishPerStudents();
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
        notifyAll();
        
    }
    
    public synchronized boolean allPortionsDelivered(){
        if(portionsDelivered==N){
            return true;
        }else{
            return false;
        }
    }
    
    public synchronized void haveNextPortionReady() throws MyException{
        try{
            Thread.sleep((long)(1+40*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not presenting");
        }
        portions+=1;

    }
    
    public synchronized boolean allOrdersDelivered(){
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
        portions=0;
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

    public synchronized void collectPortion() { //waiter
        portionsDelivered+=1;
    }
}
