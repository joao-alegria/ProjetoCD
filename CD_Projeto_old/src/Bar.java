/**
 *
 * @author joao-alegria
 */
public class Bar {
	private Order order;
	private boolean lookAround = true;

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
            return order.getEvent();
	}

	public synchronized void prepareBill() throws MyException{
            try{
                Thread.sleep((long) Math.random() * 100 + 1);
            }catch(InterruptedException e){
                throw new MyException("Error: Not preparing bill.");
            }
	}

	// public synchronized void clean() {
	// order.enableCleanUp();
	// }

	//private synchronized Event getEvent() {
	//	return order.getEvent();
	//}

	public synchronized void signalWaiter() {
            lookAround = false;
            notifyAll();
	}

}