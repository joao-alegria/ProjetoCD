/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class Order{

    private int numberOfStudents=0;
    private int dishesPerStudent=0;
    private boolean presentMenu=false, takeOrder=false, foodReady=false, getBill=false; //cleanUp=false;
    
    public Order(int n, int d){
        numberOfStudents=n;
        dishesPerStudent=d;
    }
    
    public synchronized void reset(){
        presentMenu=false;
        takeOrder=false;
        foodReady=false;
        getBill=false;
        //cleanUp=false;
    }
    
    public synchronized void enablePresentMenu(){
        presentMenu=true;
    }
    
    public synchronized void enableTakeOrder(){
        takeOrder=true;
    }
    
    public synchronized void enableFoodReady(){
        foodReady=true;
    }
    
    public synchronized void enableGetBill(){
        getBill=true;
    }
    
    //public synchronized void enableCleanUp(){
    //    cleanUp=true;
    //}
    
    public synchronized int getDishPerStudents(){
        return this.dishesPerStudent;
    }
    
    public synchronized int getNumStudents(){
        return this.numberOfStudents;
    }
    
    public synchronized Event getEvent() throws MyException{
    	if(presentMenu) {
            return Event.presentMenu;
    	}else if(takeOrder) {
            return Event.takeOrder;
    	}else if(foodReady) {
            return Event.foodReady;
    	}else if (getBill) {
            return Event.getBill;
    	}else {
            throw new MyException("Erro na função getEvent da classe Order!");
    	}
    }
}