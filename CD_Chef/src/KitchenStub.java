import genclass.*;
public class KitchenStub {
    
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

    public KitchenStub(String hostName, int port) {
        serverHostName = hostName;
        serverPortNumb = port;
    }
    
    public void watchTheNews(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.watchTheNews);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }
    
    /**
     * Simula a preparação de um dos pratos da refeição.
     */
    public void startPreparation(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.startPreparation);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }
    
    /**
     * Simula a preparação para apresentar os pratos antes preparados e o empratamento de um prato.
     */
    public void proceedToPresent(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.proceedToPresent);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }
    
    /**
     * Verifica se todos os pratos desse tipo já foram entregues.
     * Pode desbloquear o Waiter que necessita da confirmação que o prato que pretende servir está realmente empratado.
     * @return boolean que indica se todos os pratos já foram servidos ou não.
     */
    public boolean haveAllPortionsBeenDelivered(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.haveAllPortionsBeenDelivered);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackAllPortionsDel) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        return inMessage.getReply();
    }
    
    /**
     * Simula a preparação de mais um prato.
     * Bloqueia caso o Waiter não tenha recolhido o prato anterior.
     */
    public void haveNextPortionReady() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.haveNextPortionReady);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }
    
    /**
     * Verifica se todo o pedido já foi preparado e entregue ao cliente.
     * @return boolean que indica se já tudo foi entregue.
     */
    public boolean hasTheOrderBeenCompleted(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.hasTheOrderBeenCompleted);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackOrderCompleted) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        return inMessage.getReply();
    }
    
    /**
     * Simula a continuação do serviço e a preparação de mais um prato da refeição.
     */
    public void continuePreparation() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.continuePreparation);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }
    
    /**
     * Simula a limpeza da cozinha por parte do Chef, entidade responsável por essa zona.
     */
    public void cleanUp() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.cleanUp);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Chef)Thread.currentThread()).setStatus(inMessage.getState());
    }

    /**
     * Simula a entrega do pedido anotado pelo Waiter ao Chef.
     * É com este evento que o Chef acorda e começa a preparação da refeição.
     * O Chef espera que o Waiter lhe entregue o pedido.
     */
    public void handTheNoteToTheChef() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.handTheNoteToTheChef);
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
     * Simula o levantamento de um prato pelo Waiter para ser servido.
     * O Waiter só tem a capacidade de servir um prato de cada vez.
     * O Waiter espera que a porção esteja realmente empratada. Depois de ter a certeza que está empratada, avisa que pede ser empratada outra.
     */
    /*
    public void collectPortion(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.collectPortion);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Waiter)Thread.currentThread()).setStatus(inMessage.getState());
    }
    */
    
}
