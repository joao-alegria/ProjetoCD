package SharedRegions;
import MainPackage.*;

/**
 * Entidade Bar. Entidade que representa a zona partilhada Bar, onde o Waiter irá executar a maioria das suas atividades, sendo a principal esperar por eventos.
 * É nesta zona partilhada que o Chef, assim como os Estudantes irão executar algumas das suas operações, principalmente a sinalização do Waiter.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Bar {

    //zona partilhada
    private GeneralMemory mem;
    
    //variáveis de controlo
    private boolean lookAround = true;
    private boolean presentMenu = false, takeOrder = false, foodReady = false, getBill = false, end=false;
    private boolean waiter = false;

    /**
     * Construtor de Bar.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMemory a considerar.
     */
    public Bar(GeneralMemory m) {
        mem = m;
    }

    /**
     * Simula espera do Waiter.
     * Waiter bloqueia à espera que necessitem dele e é acordado quando o chamam para fazer alguma ação.
     * Pode acordar com o enter(), o exit(), o signalTheWaiter(), o callTheWaiter(), o alertTheWaiter() e o shouldHaveArrivedEarlier().
     * @return Event que indica qual evento que o Waiter deve satisfazer.
     * @throws MyException Exception que aparece quando existe um erro de execução.
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
     * Restaura os valores dos eventos para valores que indicam o evento nulo.
     */
    private synchronized void reset() {
        presentMenu = false;
        takeOrder = false;
        foodReady = false;
        getBill = false;
        end=false;
    }

    /**
     * Ativa quando um Estudante entra no restaurante. Simula o evento de entregar o menu e esperar que o Estudante o leia.
     * Acorda o Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void enter() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        presentMenu = true;
    }
    
    /**
     * Ativa o evento de um Estudante sair do restaurante. Simula a saida e a despedida respetiva.
     * Acorda o Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void exit() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        end = true;
    }

    /**
     * Ativa o evento de receber o pedido organizado pelo primeiro Estudante a chegar à mesa. 
     * Simula a receção do pedido pela parte do Waiter, e por isso espera que o Estudante lhe descreva o pedido.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void callTheWaiter() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        takeOrder = true;
    }

    /**
     * Ativa o evento de servir comida, visto que a comida está pronta. 
     * Simula o ato do Waiter ir buscar um prato e o ir entregar à mesa. Este evento é despoletado pelo Chef.
     * Acorda o Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void alertTheWaiter() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        foodReady = true;
    }
    
    /**
     * Ativa o evento de servir comida, visto que a ja todos acabaram de comer o último prato.
     * Simula o ato do Waiter ir buscar um prato e o ir entregar à mesa. 
     * Este evento é despoletado pelo último Estudante a comer o último prato.
     * Acorda o Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void signalTheWaiter() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        foodReady = true;
    }

    /**
     * Ativa o evento de preparar e proceder ao pagamento da conta.
     * Simula o ato de pagamento da refeição. Por isso o Waiter fica à espera que o Estudante(último a chegar à mesa) pague a conta.
     * Acorda o Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void shouldHaveArrivedEarlier() throws MyException{
        try{getWaiterAttention();}catch(MyException e){throw e;}
        this.reset();
        getBill = true;
    }

    /**
     * Retorna o evento que o Waiter deve satisfazer.
     * @return Event que indica de entre os eventos, qual deve ser atendido.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    private synchronized Event getEvent() throws MyException {
        Event aux = null;
        if (presentMenu) {
            aux = Event.presentMenu;
        } else if (takeOrder) {
            aux = Event.takeOrder;
        } else if (foodReady) {
            aux = Event.foodReady;
        } else if (getBill) {
            aux = Event.getBill;
        } else if (end) {
            aux = Event.end;
        } else {
            throw new MyException("Erro na função getEvent da classe Order!");
        }
        return aux;
    }

    /**
     * Simula a preparação da conta por parte do Waiter.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void prepareTheBill() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not preparing the bill.");
        }
    }

    /**
     * Simula o retorno para o Bar. Função usada exclusivamente pelo Waiter no fim de cada evento que ele executa.
     * Desbloqueia eventuais entidades que estejam à espera que o Waiter ficasse desocupado.
     */
    public synchronized void returnToTheBar() {
        waiter = false;
        notifyAll();
    }

    /**
     * Sinaliza o Waiter de que existe um evento a ser atendido.
     * Função interna usada indiretamente por todas as entidades que pretendam um serviço do Waiter, podendo bloquear caso o Waiter esteja ocupado.
     * Caso o Waiter esteja desocupado, ele é acordado, obtendo o evento a executar e fazendo-o.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    private synchronized void getWaiterAttention() throws MyException {
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
