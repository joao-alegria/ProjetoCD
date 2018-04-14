/**
 * Tipo de dados representativos do ambiente Bar.
 * Bar é uma zona partilhada do programa, maioritariamente utilizada para ações relacionadas com o waiter.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Bar {
	private GeneralMemory mem;
	private boolean lookAround = true;
        private boolean presentMenu = false, takeOrder = false, foodReady = false, getBill = false; //flags utilizadas para determinar o 
                                                                                                    //evento a realizar pelo waiter
	
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
            return getEvent();
	}
        
        /**
         * Restaura os valores para valores por definição.
         */
	public synchronized void reset() {
		presentMenu = false;
		takeOrder = false;
		foodReady = false;
		getBill = false;
	}

        /**
         * Ativa o evento de apresentar o menu.
         */
	public synchronized void enablePresentMenu() {
            this.reset();
            presentMenu = true;
	}

        /**
         * Ativa o evento de receber a ordem.
         */
	public synchronized void enableTakeOrder() {
            this.reset();
            takeOrder = true;
	}

        /**
         * Ativa o evento de servir comida, visto que a comida está pronta.
         */
	public synchronized void enableFoodReady() {
            this.reset();
            foodReady = true;
	}
        
        /**
         * Ativa o evento de tratar da conta.
         */
	public synchronized void enableGetBill() {
            this.reset();
            getBill = true;
	}
        
        public synchronized Event getEvent() throws MyException {
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
            return aux;
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