/**
 * Entidade Kitchen. Entidade que representa a zona partilhada Kitchen, onde o Chef irá executar a maioria das suas atividades.
 * É nesta zona partilhada que o Waiter também irá executar algumas das suas operações.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Kitchen {
    
    //zona partilhada
    private GeneralMemory mem;
    
    //variáveis de controlo
    private boolean news=true, note=true, portion=true, nextPortion=true;
    private int portionsDelivered=0, ordersDelivered=0, portions=0;
    
    //info
    private int N,      //número de estudantes
                M;      //número de pratos por estudante

    
    
    /**
     * Construtor de Kitchen.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMmory a considerar.
     */
    public Kitchen(GeneralMemory m){
        mem=m;
        this.N=m.getNumStudents();
        this.M=m.getDishPerStudents();
    }
    
    /**
     * Simula a espera do Chef enquanto não recebe nenhum pedido.
     * É desbloqueado quando o Waiter lhe entrega o pedido(handTheNoteToTheChef()).
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void watchTheNews() throws MyException{
        try{
            while(news){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not watching the news.");
        }
    }
    
    /**
     * Simula a preparação de um dos pratos da refeição.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void startPreparation() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not starting the preparation.");
        }*/
        ordersDelivered+=1;
        note=false;
        notifyAll();
    }
    
    /**
     * Simula a preparação para apresentar os pratos antes preparados e o empratamento de um prato.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void proceedToPresent() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not presenting.");
        }*/
        //portions+=1;   
    }
    
    /**
     * Verifica se todos os pratos desse tipo já foram entregues.
     * Pode desbloquear o Waiter que necessita da confirmação que o prato que pretende servir está realmente empratado.
     * @return boolean que indica se todos os pratos já foram servidos ou não.
     */
    public synchronized boolean haveAllPortionsBeenDelivered(){
        portion=false;
        notifyAll();
        if(portionsDelivered==N){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Simula a preparação de mais um prato.
     * Bloqueia caso o Waiter não tenha recolhido o prato anterior.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void haveNextPortionReady() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not having the next portion ready.");
        }*/
        portions+=1;
        try{
            while(nextPortion){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Waiting to prepare next portion.");
        }
        nextPortion=true;
    }
    
    /**
     * Verifica se todo o pedido já foi preparado e entregue ao cliente.
     * @return boolean que indica se já tudo foi entregue.
     */
    public synchronized boolean hasTheOrderBeenCompleted(){
        if(ordersDelivered==M){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Simula a continuação do serviço e a preparação de mais um prato da refeição.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void continuePreparation() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not continuing to prepare.");
        }*/
        ordersDelivered+=1;
        portionsDelivered=0;
        portions=0;
    }
    
    /**
     * Simula a limpeza da cozinha por parte do Chef, entidade responsável por essa zona.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void cleanUp()  throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not cleaning up.");
        }*/
    }

    /**
     * Simula a entrega do pedido anotado pelo Waiter ao Chef.
     * É com este evento que o Chef acorda e começa a preparação da refeição.
     * O Chef espera que o Waiter lhe entregue o pedido.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void handTheNoteToTheChef() throws MyException{
        news=false;
        notifyAll();
        
        try{
            while(note){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Waiting the chef to receive the note.");
        }
    }

    /**
     * Simula o levantamento de um prato pelo Waiter para ser servido.
     * O Waiter só tem a capacidade de servir um prato de cada vez.
     * O Waiter espera que a porção esteja realmente empratada. Depois de ter a certeza que está empratada, avisa que pede ser empratada outra.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void collectPortion() throws MyException{
        try{
            while(portion){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Waiting for portion");
        }
        portion=true;
        portionsDelivered+=1;
        nextPortion=false;
        notifyAll();
    }
}
