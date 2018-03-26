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
    
    public synchronized void setNumStudents(int num){
        this.numberOfStudents=num;
    }
    
    public synchronized void setDishPerStudents(int num){
        this.dishesPerStudent=num;
    }
    
    public synchronized int getDishPerStudents(){
        return this.dishesPerStudent;
    }
    
    public synchronized int getNumStudents(){
        return this.numberOfStudents;
    }
}
