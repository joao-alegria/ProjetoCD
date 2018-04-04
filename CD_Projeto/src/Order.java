/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
import genclass.TextFile;

public class Order {

	private int numberOfStudents = 0;
	private int dishesPerStudent = 0;
	private boolean presentMenu = false, takeOrder = false, foodReady = false, getBill = false; // cleanUp=false;
	private TextFile writer;

	public Order(int n, int d, String path) {
		numberOfStudents = n;
		dishesPerStudent = d;
		writer = new TextFile();
		writer.openForWriting(path, "log.txt");
	}

	public synchronized void reset() {
		presentMenu = false;
		takeOrder = false;
		foodReady = false;
		getBill = false;
		// cleanUp=false;
	}

	public synchronized void enablePresentMenu() {
            presentMenu = true;
	}

	public synchronized void enableTakeOrder() {
            takeOrder = true;
	}

	public synchronized void enableFoodReady() {
            foodReady = true;
	}

	public synchronized void enableGetBill() {
            getBill = true;
	}

	// public synchronized void enableCleanUp(){
	// cleanUp=true;
	// }

	public synchronized int getDishPerStudents() {
		return this.dishesPerStudent;
	}

	public synchronized int getNumStudents() {
		return this.numberOfStudents;
	}

	public synchronized Event getEvent() throws MyException {
            System.out.println(presentMenu+" " + takeOrder +" "+ foodReady+" "+getBill);
            Event aux=null;
            if (presentMenu) {
                    aux= Event.presentMenu;
            } else if (takeOrder) {
                    aux= Event.takeOrder;
            } else if (foodReady) {
                    aux= Event.foodReady;
            } else if (getBill) {
                    aux= Event.getBill;
            } else {
                    throw new MyException("Erro na função getEvent da classe Order!");
            }
            this.reset();
            return aux;
	}

	public synchronized void logger(String s) {
		writer.writelnString(s);
	}

	public synchronized void closeLog() {
		writer.close();
	}

}