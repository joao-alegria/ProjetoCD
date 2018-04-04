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
    //private boolean lastMeal;
    //private boolean lastArriving;
    private int atTable=0;
    
    private boolean noMenu=true;
    
    public Table(Order o){
        order=o;
    }

    public synchronized void saluteClient() {
        noMenu=false;
        notifyAll();
        noMenu=true;
    }

    public synchronized void getOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void servePortion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void presentBill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void waitPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return aux;
    }

    public synchronized void readMenu() throws MyException{
        try{
            Thread.sleep((long)(1+100*Math.random()));
        }catch(InterruptedException e){
            throw new MyException("Error: Not reading menu.");
        }
    }

    public synchronized void full() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void choose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized boolean allChose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void prepareOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void describeOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void chat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void eat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized boolean lastToEat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void payBill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
