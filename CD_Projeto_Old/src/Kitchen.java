/**
 * Entidade Kitchen. Entidade que representa a zona partilhada Kitchen, onde o Chef irá executar a maioria das suas atividades.
 * É nesta zona partilhada que o Waiter também irá executar algumas das suas operações.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Kitchen {
    
    //zona partilhada
    private GeneralMemory mem;
    
    //variáveis de controlo
    private boolean news=true;
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
     * Simula a espero do Chef enquanto não recebe nenhuma ordem.
     * É acordado quando o Waiter lhe entrega o pedido.
     * @throws MyException 
     */
    public synchronized void watchNews() throws MyException{
        try{
            while(news){
                wait();
            }
        }catch(InterruptedException e){
            throw new MyException("Error: Not watching the news.");
        }
    }
    
    /**
     * Simula a preparação de um tipo de pratos.
     * @throws MyException 
     */
    public synchronized void startPrep() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not starting the preparation.");
        }*/
        ordersDelivered+=1;
    }
    
    /**
     * Simula a preparação para apresentar os pratos antes preparados e o empratamento de um prato.
     * @throws MyException 
     */
    public synchronized void proceedToPresent() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not presenting.");
        }*/
        portions+=1;
        notifyAll();
        
    }
    
    /**
     * Verifica se todos os pratos desse tipo já foram entregues.
     * @return boolean que indica se todos os pratos já foram servidos ou não.
     */
    public synchronized boolean allPortionsDelivered(){
        if(portionsDelivered==N){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Simula a preparação de mais um prato.
     * @throws MyException 
     */
    public synchronized void haveNextPortionReady() throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not having the next portion ready.");
        }*/
        portions+=1;

    }
    
    /**
     * Verifica se todos os tipos de pratos já foram preparados e entregues ao cliente.
     * @return boolean que indica se ja tudo foi entregue.
     */
    public synchronized boolean allOrdersDelivered(){
        if(ordersDelivered==M){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Simula a continuação do serviço e a preparação de mais um tipo de prato.
     * @throws MyException 
     */
    public synchronized void contPrep() throws MyException{
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
     * Simula a limpeza da cozinha por parte do Chef, responsável por essa zona.
     * @throws MyException 
     */
    public synchronized void cleanup()  throws MyException{
        /*try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not cleaning up.");
        }*/
    }

    /**
     * Simula a entrega da ordem do Waiter ao Chef.
     * É com este evento que o Chef acorda e começa a preparação da refeição.
     */
    public synchronized void handOrder() {
        news=false;
        notifyAll();
    }

    /**
     * Simula o levantamento de um prato pelo Waiter para ser servido.
     * O Waiter só tem a capacidade de servir um prato de cada vez.
     */
    public synchronized void collectPortion() {
        portionsDelivered+=1;
    }
}
