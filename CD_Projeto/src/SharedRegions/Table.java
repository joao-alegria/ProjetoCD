package SharedRegions;
import MainPackage.*;

/**
 * Entidade Table. Entidade que representa a zona partilhada Table, onde os estudantes irão executar a maioria das suas atividades.
 * É nesta zona partilhada que o Waiter também irá executar algumas das suas operações.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Table {
    
    //zona partilhada
    private GeneralMemory mem;

    //info
    private int N;      //número de estudantes

    //variáveis de controlo
    private int atTable = 0, choosen = 0, finishedEating = 0, served = 0, chating=0, wentHome=0;
    private boolean waitingPad=true, menu= true, enter = true, descOrder = true, chat = true, bill = true, waitPay = true, allFinished = false;

    /**
     * Construtor de Table.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMemory a considerar.
     */
    public Table(GeneralMemory m) {
        mem = m;
        this.N = m.getNumStudents();
    }

    /**
     * Simula o cumprimento ao cliente. Liberta o Estudante que estava à espera que o Waiter lhe apresentasse o menu.
     * Bloqueia à espera que o Estudante leia o menu.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void saluteTheClient() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not saluting client.");
        }
        enter = false;
        notifyAll();
        
        try {
            while (menu) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Waiting for client to read the menu.");
        }
    }

    /**
     * Simula a entrada no restaurante. Bloqueia à espera que o Waiter lhe entregue o menu.
     * @return int que indica qual a posição em que o Estudante se sentou na mesa.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized int enter() throws MyException {
        atTable = atTable + 1;
        menu=true;
        try {
            while (enter) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Can not enter.");
        }
        return atTable;
    }

    /**
     * Simula a leitura do menu pelo Estudante. 
     * Desbloqueia o Waiter que ficou à espera que o Estudante lê-se o menu para o Waiter poder voltar ao bar.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void readTheMenu() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not reading the menu.");
        }
        menu=false;
        notifyAll();
    }

    /**
     * Simula a escolha da refeição pelo Estudante. 
     * Caso já todos tenham escolhido desbloqueia o primeiro Estudante que chegou à mesa, e por isso ficou responsável por organizar o pedido.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void informCompanion() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not choosing.");
        }
        choosen += 1;
        notifyAll();
    }

    /**
     * Devolve true se todos já tiverem escolhido a refeição ou false, caso contrário.
     * @return boolean que indica se já todos escolheram ou não.
     */
    public synchronized boolean hasEverybodyChosen() {
        if(choosen == N) {
            return true;
        }
        return false;
    }

    /**
     * Simula a preparação do pedido por parte do Estudante que chegou em primeiro lugar à mesa.
     * Caso nem todos os Estudantes tenham escolhido, este fica à espera que todos escolham.
     * Depois de todos terem escolhido, bloqueia à espera que o Waiter chegue com o Pad para anotar o pedido.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void prepareTheOrder() throws MyException {
        choosen+=1;
        try {
            while (choosen != N) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not preparing the order.");
        }
        
        try {
            while (waitingPad) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Waiting for the pad.");
        }
    }


    /**
     * Simula a descrição do pedido, por parte do Estudante, ao Empregado.
     * No final, desbloqueia o Empregado que estava à espera da descrição do pedido.
     * @throws  MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void describeTheOrder() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not describing the order.");
        }

        descOrder = false;
        notifyAll();
    }

    /**
     * Simula a anotação do pedido organizado pelo primeiro Estudante a chegar à mesa, pelo Empregado.
     * Desbloqueia o Estudante que estava a organizar o pedido. Depois disso o Waiter bloqueia à espera da descrição do pedido do Estudante.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void getThePad() throws MyException {
        waitingPad=false;
        notifyAll();
        try {
            while (descOrder) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not getting order.");
        }
    }

    /**
     * Simula o serviço de um prato a um Estudante. Em primeiro lugar bloqueia para certificar-se que todos os Estudantes estejam a conversar.
     * Caso tenha servido o prato a todos os Estudantes, desbloqueia todos os Estudantes que estavam a conversar.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void deliverPortion() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not serving the portion.");
        }
        
        try {
            while (chating!=N) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not possible to serve.");
        }
        
        served += 1;
        notifyAll();

        if (served == N) {
            allFinished = true;
            chat = false;
            notifyAll();
        }
    }

    /**
     * Simula a conversa dos Estudantes.
     * À medida que os Estudantes bloqueiam à espera do próximo prato, avisam o Waiter que pode estar à espera que todos passem para a espera do próximo prato.
     * Pode bloquear os estudantes quando:
     * -Ainda nem todos os Estudantes tenham acabado de comer, ficando os que já acabaram a conversar.
     * -Enquanto os Estudantes esperam pela chegada do próximo prato.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void joinTheTalk() throws MyException {
        try {
            while (allFinished) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not waiting for everyone.");
        }
        
        chating+=1;
        notifyAll();
        
        try {
            while (chat) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not chating.");
        }
        chating-=1;
    }

    /**
     * Simula o início de cada Estudante comer um prato.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void startEating() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not starting to eat.");
        }
    }
    
    /**
     * Simula o término de cada Estudante comer um prato.
     * @return int que indica quantos Estudantes é que já acabaram de comer.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized int endEating() throws MyException {
        finishedEating = finishedEating + 1;

        return finishedEating;

    }
    
    /**
     * Retorna true se todos os Estudantes já acabaram de comer ou false, caso contrário.
     * @return boolean que indica se todos os Estudantes acabaram de comer.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized boolean hasEverybodyFinished() throws MyException {
        if(finishedEating==N){
            chat = true;
            allFinished = false;
            notifyAll();
            finishedEating = 0;
            served = 0;
            return true;
        }
        return false;
    }


    /**
     * Simula a apresentação da conta ao Estudante.
     * Acorda o Estudante que estava à espera da conta.
     * Bloqueia à espera que o Estudante(o último a chegar à mesa) pague a conta.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void presentTheBill() throws MyException{
        bill = false;
        notifyAll();
        
        try {
            while (waitPay) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Didn't receive payment.");
        }
    }

    /**
     * Simula o pagamento da conta por parte do Estudante.
     * Acorda os possíveis Estudantes que estivessem à espera que todos os outros acabassem de comer.
     * Bloqueia à espera que o Waiter lhe apresente a conta para ele pagar, 
     * assim como espera que todos os outros Estudantes saiam do restaurante para ele proceder à transação.
     * No fim de pagar a conta sinaliza ao Waiter que a conta foi paga.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void honorTheBill() throws MyException {
        allFinished = false;
        chat = false;
        notifyAll();
        try {
            while (bill) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not getting bill.");
        }
        
        try {
            while (wentHome!=N-1) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Students still in the resturant.");
        }
        
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not paying the bill.");
        }

        waitPay = false;
        notifyAll();

    }

    /**
     * Simula a ida para casa de cada Estudante.
     * À medida que os Estudantes vão saindo vão avisando os restantes, 
     * podendo por isso desloquear o Estudante que estava à espera que todos saissem para ele pagar.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void goHome() throws MyException {
        wentHome+=1;
        notifyAll();
    }
    
    /**
     * Simula a despedida do Waiter para cada Estudante que sai.
     * @throws MyException Exception que aparece quando existe um erro de execução.
     */
    public synchronized void sayGoodbye() throws MyException{
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not saying goodbye.");
        }
    }

}
