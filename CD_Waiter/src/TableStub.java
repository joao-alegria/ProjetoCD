import genclass.*;

public class TableStub {
    
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

    public TableStub(String hostName, int port) {
        serverHostName = hostName;
        serverPortNumb = port;
    }
    
    /**
     * Simula o cumprimento ao cliente. Liberta o Estudante que estava à espera que o Waiter lhe apresentasse o menu.
     * Bloqueia à espera que o Estudante leia o menu.
     */
    public void saluteTheClient(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.saluteClient);
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

    /**
     * Simula a entrada no restaurante. Bloqueia à espera que o Waiter lhe entregue o menu.
     * @return int que indica qual a posição em que o Estudante se sentou na mesa.
     */
    /*public int enter(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.enterTable, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackEnterTable) {
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
        return inMessage.getPosAtTable();
    }*/

    /**
     * Simula a leitura do menu pelo Estudante. 
     * Desbloqueia o Waiter que ficou à espera que o Estudante lê-se o menu para o Waiter poder voltar ao bar.
     */
    /*public void readTheMenu(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.readTheMenu, ((Student)Thread.currentThread()).getID());
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
    }*/

    /**
     * Simula a escolha da refeição pelo Estudante. 
     * Caso já todos tenham escolhido desbloqueia o primeiro Estudante que chegou à mesa, e por isso ficou responsável por organizar o pedido.
     */
    /*public void informCompanion(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.informCompanion, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(((Student)Thread.currentThread()).getID()!=inMessage.getStudentID()){
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        ((Student)Thread.currentThread()).setStatus(inMessage.getState());
        con.close();
    }*/

    /**
     * Devolve true se todos já tiverem escolhido a refeição ou false, caso contrário.
     * @return boolean que indica se já todos escolheram ou não.
     */
    /*public boolean hasEverybodyChosen() {
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.hasEverybodyChosen, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackEverybodyChosen) {
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
        return inMessage.getReply();
    }*/

    /**
     * Simula a preparação do pedido por parte do Estudante que chegou em primeiro lugar à mesa.
     * Caso nem todos os Estudantes tenham escolhido, este fica à espera que todos escolham.
     * Depois de todos terem escolhido, bloqueia à espera que o Waiter chegue com o Pad para anotar o pedido.
     */
    /*public void prepareTheOrder(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.prepareTheOrder, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
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
    }*/


    /**
     * Simula a descrição do pedido, por parte do Estudante, ao Empregado.
     * No final, desbloqueia o Empregado que estava à espera da descrição do pedido.
     */
    /*public void describeTheOrder(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.describeTheOrder, ((Student)Thread.currentThread()).getID());
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
    }*/

    /**
     * Simula a anotação do pedido organizado pelo primeiro Estudante a chegar à mesa, pelo Empregado.
     * Desbloqueia o Estudante que estava a organizar o pedido. Depois disso o Waiter bloqueia à espera da descrição do pedido do Estudante.
     */
    public void getThePad(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.getThePad);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
        ((Waiter)Thread.currentThread()).setStatus(inMessage.getState());
    }

    /**
     * Simula o serviço de um prato a um Estudante. Em primeiro lugar bloqueia para certificar-se que todos os Estudantes estejam a conversar.
     * Caso tenha servido o prato a todos os Estudantes, desbloqueia todos os Estudantes que estavam a conversar.
     */
    public void deliverPortion(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.deliverPortion);
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
     * Simula a conversa dos Estudantes.
     * À medida que os Estudantes bloqueiam à espera do próximo prato, avisam o Waiter que pode estar à espera que todos passem para a espera do próximo prato.
     * Pode bloquear os estudantes quando:
     * -Ainda nem todos os Estudantes tenham acabado de comer, ficando os que já acabaram a conversar.
     * -Enquanto os Estudantes esperam pela chegada do próximo prato.
     */
    /*public void joinTheTalk(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.joinTheTalk, ((Student)Thread.currentThread()).getID());
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
    }*/

    /**
     * Simula o início de cada Estudante comer um prato.
     */
    /*public void startEating(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.startEating, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackState) {
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
    }*/
    
    /**
     * Simula o término de cada Estudante comer um prato.
     * @return int que indica quantos Estudantes é que já acabaram de comer.
     */
    /*public int endEating(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.endEating, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackFinishEating) {
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
        return inMessage.getPosEating();
    }*/
    
    /**
     * Retorna true se todos os Estudantes já acabaram de comer ou false, caso contrário.
     * @return boolean que indica se todos os Estudantes acabaram de comer.
     */
    /*public boolean hasEverybodyFinished(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.hasEverybodyFinished, ((Student)Thread.currentThread()).getID());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ackEverybodyFinished) {
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
        return inMessage.getReply();
    }*/


    /**
     * Simula a apresentação da conta ao Estudante.
     * Acorda o Estudante que estava à espera da conta.
     * Bloqueia à espera que o Estudante(o último a chegar à mesa) pague a conta.
     */
    public void presentTheBill(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.presentTheBill);
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
     * Simula o pagamento da conta por parte do Estudante.
     * Acorda os possíveis Estudantes que estivessem à espera que todos os outros acabassem de comer.
     * Bloqueia à espera que o Waiter lhe apresente a conta para ele pagar, 
     * assim como espera que todos os outros Estudantes saiam do restaurante para ele proceder à transação.
     * No fim de pagar a conta sinaliza ao Waiter que a conta foi paga.
     */
    /*public void honorTheBill(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.honorTheBill, ((Student)Thread.currentThread()).getID());
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

    }*/

    /**
     * Simula a ida para casa de cada Estudante.
     * À medida que os Estudantes vão saindo vão avisando os restantes, 
     * podendo por isso desloquear o Estudante que estava à espera que todos saissem para ele pagar.
     */
    /*public void goHome(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.goHome, ((Student)Thread.currentThread()).getID());
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
    }*/
    
    /**
     * Simula a despedida do Waiter para cada Estudante que sai.
     */
    public void sayGoodbye(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.sayGoodbye);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.acknowledge) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Tipo inválido!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close();
    }
    
}
