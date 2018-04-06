/**
 *
 * @author joao-alegria
 */
public class Bar {
	private GeneralMemory mem;
	private boolean lookAround = true;
	
	private boolean waiter=false;

	public Bar(GeneralMemory m) {
		mem = m;
	}

	public synchronized Event lookAround() throws MyException {
            try {
                    while (lookAround) {
                        wait();
                    }
            } catch (InterruptedException e) {
                    throw new MyException("Error: Not looking around.");
            }
            lookAround=true;
            return mem.getEvent();
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