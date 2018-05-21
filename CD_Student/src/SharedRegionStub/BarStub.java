package SharedRegionStub;
import Entities.Student;
import MainPackage.*;
import genclass.*;

public class BarStub {

    /**
     * Nome do sistema computacional onde está localizado o servidor
     *
     * @serialField serverHostName
     */
    private String serverHostName = null;

    /**
     * Número do port de escuta do servidor
     *
     * @serialField serverPortNumb
     */
    private int serverPortNumb;

    public BarStub(String hostName, int port) {
        serverHostName = hostName;
        serverPortNumb = port;
    }

    /**
     * Simula espera do Waiter. Waiter bloqueia à espera que necessitem dele e é
     * acordado quando o chamam para fazer alguma ação. Pode acordar com o
     * enter(), o exit(), o signalTheWaiter(), o callTheWaiter(), o
     * alertTheWaiter() e o shouldHaveArrivedEarlier().
     * 
     * @return String que indica o evento que o Waiter deve executar.
     */
    /*public String lookAround() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.lookAround);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackwaiterEvent) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Waiter)Thread.currentThread()).setStatus(inMessage.getState());
        return inMessage.getWaiterEvent();
    }*/

    /**
     * Ativa quando um Estudante entra no restaurante. Simula o evento de
     * entregar o menu e esperar que o Estudante o leia. Acorda o Waiter.
     */
    public void enter() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.enterBar, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
    }

    /**
     * Ativa o evento de um Estudante sair do restaurante. Simula a saida e a
     * despedida respetiva. Acorda o Waiter.
     */
    public void exit(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.exitBar, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    /**
     * Ativa o evento de receber o pedido organizado pelo primeiro Estudante a
     * chegar à mesa. Simula a receção do pedido pela parte do Waiter, e por
     * isso espera que o Estudante lhe descreva o pedido.
     */
    public void callTheWaiter(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.callTheWaiter, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    /**
     * Ativa o evento de servir comida, visto que a comida está pronta. Simula o
     * ato do Waiter ir buscar um prato e o ir entregar à mesa. Este evento é
     * despoletado pelo Chef. Acorda o Waiter..
     */
    public void alertTheWaiter(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.alertTheWaiter);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
    }

    /**
     * Ativa o evento de servir comida, visto que a ja todos acabaram de comer o
     * último prato. Simula o ato do Waiter ir buscar um prato e o ir entregar à
     * mesa. Este evento é despoletado pelo último Estudante a comer o último
     * prato. Acorda o Waiter.
     */
    public void signalTheWaiter(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.signalTheWaiter, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    /**
     * Ativa o evento de preparar e proceder ao pagamento da conta. Simula o ato
     * de pagamento da refeição. Por isso o Waiter fica à espera que o
     * Estudante(último a chegar à mesa) pague a conta. Acorda o Waiter.
     */
    public void shouldHaveArrivedEarlier(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.shouldHaveArrivedEarlier, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        ((Student)Thread.currentThread()).setStatus(inMessage.getState());
    }

    /**
     * Simula a preparação da conta por parte do Waiter.
     */
    /*public void prepareTheBill(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.preapareTheBill);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Waiter)Thread.currentThread()).setStatus(inMessage.getState());
    }*/

    /**
     * Simula o retorno para o Bar. Função usada exclusivamente pelo Waiter no
     * fim de cada evento que ele executa. Desbloqueia eventuais entidades que
     * estejam à espera que o Waiter ficasse desocupado.
     */
    /*public void returnToTheBar() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.returnToTheBar);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
    }*/

}
