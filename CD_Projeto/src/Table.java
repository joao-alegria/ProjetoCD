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
    private int atTable = 0, choosen = 0, finishedEating = 0, served = 0;
    private boolean noMenu = true, descOrder = true, chat = true, bill = true, waitPay = true, allFinished = false;

    /**
     * Construtor de Table.
     * @param m GeneralMemory que indica a referência para a zona partilhada GeneralMmory a considerar.
     */
    public Table(GeneralMemory m) {
        mem = m;
        this.N = m.getNumStudents();
    }

    /**
     * Simula o cumprimento ao cliente. Liberta o Estudante que se encontrava à espera pelo menu.
     */
    public synchronized void saluteClient() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not saluting client.");
        }
        noMenu = false;
        notifyAll();
    }

    /**
     * Simula a entrada no restaurante.
     * @return int que indica a quantidade de pessoas que se encontram na mesa.
     */
    public synchronized int enterRestaurant() throws MyException {
        atTable = atTable + 1;
        noMenu = true;
        return atTable;
    }

    /**
     * Simula a espera pelo menu. O Estudante fica à espera que o waiter fique desocupado.
     */
    public synchronized void waitMenu() throws MyException {
        try {
            while (noMenu) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not waiting for menu.");
        }
    }

    /**
     * Simula a leitura do menu. Se o número de Estudantes à mesa for inferiror a N ficam todos à espera. 
     * Caso contrário, são todos desbloqueados e a refeição prossegue.
     */
    public synchronized void readMenu() throws MyException {
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not reading menu.");
        }
        if (atTable == N) {
            notifyAll();
        }
        try {
            while (atTable < N) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not waiting after reading menu.");
        }
    }

    /**
     * Simula a escolha da refeição.
     */
    public synchronized void choose() throws MyException {
        try {
            Thread.sleep((long) (1 + 120 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not reading menu.");
        }
        choosen += 1;
        notifyAll();
    }

    /**
     * Devolve verdadeiro se todos já tiverem escolhido refeição ou falso contrário.
     * @return boolean que indica se já todos escolheram ou não.
     */
    public synchronized boolean allChose() {
        if (choosen == N) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Simula a preparação do pedido por parte do Estudante que chegou em primeiro lugar à mesa.
     * Caso nem todos os Estudantes tenham escolhido, este fica à espera.
     */
    public synchronized void prepareOrder() throws MyException {
        try {
            while (choosen < N) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not preparing the order.");
        }

    }

    /**
     * Simula a transmissão do pedido dos Estudantes, por parte do Empregado, ao chefe.
     */
    public synchronized void giveOrder() throws MyException {
        try {
            Thread.sleep((long) (1 + 80 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not giving order.");
        }
    }

    /**
     * Simula a descrição do pedido, por parte do Estudante, ao Empregado.
     * No final, desbloqueia o Empregado.
     */
    public synchronized void describeOrder() throws MyException {
        try {
            Thread.sleep((long) (1 + 80 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not dercribing order.");
        }

        descOrder = false;
        notifyAll();
    }

    /**
     * Simula a receção do pedido pelo Empregado, pedido feito pelo primero Estudante a chegar.
     * Empregado bloqueia à espera da descrição do pedido do Estudante.
     */
    public synchronized void getOrder() throws MyException {
        try {
            while (descOrder) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not getting order.");
        }
    }

    /**
     * Simula o serviço de um prato a um Estudante.
     * Desbloqueia todos os Estudantes que estavam a conversar.
     * Depois de servir todos os Estudantes bloqueia à espera do sinal do ultímo a comer para prosseguir o serviço.
     */
    public synchronized void servePortion() throws MyException {
        try {
            Thread.sleep((long) (1 + 80 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not serving portion.");
        }
        served += 1;
        notifyAll();

        if (served == N) {
            allFinished = true;
            chat = false;
            notifyAll();

            try {
                while (allFinished) {
                    wait();
                }
            } catch (InterruptedException e) {
                throw new MyException("Error: Not serving.");
            }

        }
    }

    /**
     * Simula a conversa à mesa.
     * Pode bloquear os estudantes quando:
     * -Ainda nem todos os Estudantes tenham acabado de comer, ficando os que já acabaram a conversar.
     * -Enquanto os Estudantes esperam pela chegada do próximo prato.
     */
    public synchronized void chat() throws MyException {

        try {
            while (allFinished) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not waiting for everyone.");
        }

        try {
            while (chat) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not chating.");
        }
    }

    /**
     * Simula o tempo que cada Estudante demora a comer.
     * @return int que indica quantos Estudantes é que já acabaram de comer.
     */
    public synchronized int eat() throws MyException {

        try {
            Thread.sleep((long) (1 + 250 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not eating.");
        }
        finishedEating = finishedEating + 1;

        return finishedEating;

    }

    /**
     * Simula o pedido da conta pelo último Estudante a chegar à mesa.
     * Avisa os restantes Estudantes que vai pagar.
     */
    public synchronized void getBill() {
        allFinished = false;
        chat = false;
        notifyAll();
    }

    /**
     * Simula a apresentação da conta ao Estudante.
     * Acorda o Estudante que estava à espera da conta.
     */
    public synchronized void presentBill() {
        bill = false;
        notifyAll();
    }

    /**
     * Simula o pagamento da conta por parte do Estudante.
     * No fim de pagar a conta sinaliza ao Waiter e aos restantes Estudantes que a conta foi paga.
     */
    public synchronized void payBill() throws MyException {
        try {
            while (bill) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not getting bill.");
        }
        try {
            Thread.sleep((long) (1 + 140 * Math.random()));
        } catch (InterruptedException e) {
            throw new MyException("Error: Not paying bill.");
        }

        waitPay = false;
        notifyAll();

    }

    /**
     * Simula a espera pelo pagamento por parte do Empregado.
     */
    public synchronized void waitPayment() throws MyException {
        try {
            while (waitPay) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not receiving payment.");
        }
    }

    /**
     * Garante o final da refeição quando já todos acabaram de comer.
     * Chamado pelo último Estudante a acabar de comer e simboliza que todos os Estudantes já acabaram de comer,
     * podendo por isso prosseguir na refeição.
     */
    public synchronized void allFinished() {
        chat = true;
        allFinished = false;
        notifyAll();
        finishedEating = 0;
        served = 0;
    }

    /**
     * Simula a ida para casa.
     * Estudantes só saem do restaurante caso a refeição já esteja paga pelo último Estudante a chegar.
     */
    public synchronized void goHome() throws MyException {
        try {
            while (waitPay) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new MyException("Error: Not waiting for honoring the payment.");
        }
    }

}
