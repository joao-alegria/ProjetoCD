
/**
 * Entidade Bar. Entidade que representa a zona partilhada Bar, onde o Waiter irá executar a maioria das suas atividades, sendo a principal esperar por eventos.
 * É nesta zona partilhada que não só o Chef como tambem os Estudantes irão executar algumas das suas operações, principalmente a sinalização do Waiter.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Bar {

    //zona partilhada
    private GeneralMemory mem;
    
    //variáveis de controlo
    private boolean lookAround = true;
    private boolean presentMenu = false, takeOrder = false, foodReady = false, getBill = false;
    private boolean waiter = false;

    /**
     * Construtor de Table.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMmory a considerar.
     */
    public Bar(GeneralMemory m) {
        mem = m;
    }

    /**
     * Simila a espera do Waiter.
     * Waiter bloqueia à espera que necessitem dele e é acordado quando o chamam para fazer alguma ação.
     * @return Event que indica qual evento que o Waiter deve satisfazer.
     * @throws MyException 
     */
    public synchronized Event lookAround() throws MyException {
        try {
            while (lookAround) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not looking around.");
        }
        lookAround = true;
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
        try{signalWaiter();}catch(MyException e){}
        this.reset();
        presentMenu = true;
        //try{signalWaiter();}catch(MyException e){}
    }

    /**
     * Ativa o evento de receber o pedido.
     */
    public synchronized void enableTakeOrder() {
        try{signalWaiter();}catch(MyException e){}
        this.reset();
        takeOrder = true;
        //try{signalWaiter();}catch(MyException e){}
    }

    /**
     * Ativa o evento de servir comida, visto que a comida está pronta.
     */
    public synchronized void enableFoodReady() {
        try{signalWaiter();}catch(MyException e){}
        this.reset();
        foodReady = true;
        //try{signalWaiter();}catch(MyException e){}
    }

    /**
     * Ativa o evento de tratar da conta.
     */
    public synchronized void enableGetBill() {
        try{signalWaiter();}catch(MyException e){}
        this.reset();
        getBill = true;
        //try{signalWaiter();}catch(MyException e){}
    }

    /**
     * Retorna o evento que o Waiter deve satisfazer.
     * @return Event que indica de entre os eventos, qual deve ser atendido.
     * @throws MyException 
     */
    public synchronized Event getEvent() throws MyException {
        Event aux = null;
        if (presentMenu) {
            aux = Event.presentMenu;
        } else if (takeOrder) {
            aux = Event.takeOrder;
        } else if (foodReady) {
            aux = Event.foodReady;
        } else if (getBill) {
            aux = Event.getBill;
        } else {
            throw new MyException("Erro na função getEvent da classe Order!");
        }
        return aux;
    }

    /**
     * Simula a preparação da conta por parte do Waiter.
     * @throws MyException 
     */
    public synchronized void prepareBill() throws MyException {
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not preparing the bill.");
        }*/
    }

    /**
     * Simula o retorno para o Bar. Função usada exclusivamente pelo Waiter no fim de cada evento.
     * Desbloqueia eventuais entidades que estivessem à espera que o Waiter ficasse desocupado.
     */
    public synchronized void returnBar() {
        waiter = false;
        notifyAll();
    }

    /**
     * Sinaliza o Waiter de que existe um evento a ser atendido.
     * Função usada por todas as entidades que pertendam um serviço do Waiter, podendo ser o Chef ou um Estudante.
     * Caso o Waiter esteja desocupado, ele é acordado, obtendo o evento a executar e fazendo-o.
     * @throws MyException 
     */
    public synchronized void signalWaiter() throws MyException {
        try {
            while (waiter) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: No waiter available at bar.");
        }
        waiter = true;
        lookAround = false;
        notifyAll();
    }

}
