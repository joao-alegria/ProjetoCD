/**
 *
 * @author joao-alegria
 */
public class Bar {
	private Order order;
	private boolean lookAround = true;
	
	private boolean waiter=false;

	public Bar(Order o) {
		order = o;
	}

	public synchronized Event lookAround() throws MyException {
            //order.reset();
            try {
                    while (lookAround) {
                        wait();
                    }
            } catch (InterruptedException e) {
                    throw new MyException("Error: Not looking around.");
            }
            lookAround=true;
            return order.getEvent();
	}

	public synchronized void prepareBill() throws MyException{
            try{
                Thread.sleep((long) Math.random() * 100 + 1);
            }catch(InterruptedException e){
                throw new MyException("Error: Not preparing bill.");
            }
	}

	public synchronized void returnBar() {
		waiter=false;
		notifyAll();
	}

	//private synchronized Event getEvent() {
	//	return order.getEvent();
	//}

	
	
	public synchronized void signalWaiter() throws MyException{
			try{
	            while(waiter){
	                wait();
	            }
	        }catch(InterruptedException e){
	            throw new MyException("Error: No waiter available at bar.");
	        }
			waiter=true;
            lookAround = false;
            notifyAll();
	}

}