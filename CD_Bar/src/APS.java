import genclass.*;
public class APS extends Thread implements StudentInterface, WaiterInterface, ChefInterface
{
  /**
   *  Contador de threads lançados
   *
   *    @serialField nProxy
   */

   private static int nProxy = 0;

  /**
   *  Canal de comunicação
   *
   *    @serialField sconi
   */

   private ServerCom sconi;

  /**
   *  Interface à barbearia
   *
   *    @serialField bShopInter
   */

   private BarInterface barInter;

  /**
   *  Instanciação do interface à barbearia.
   *
   *    @param sconi canal de comunicação
   *    @param bi interface ao bar
   */
   
   
   private int studentId=-1;
   private String waiterState=null;
   private String studentState=null;
   private String chefState=null;
   

   public APS (ServerCom sconi, BarInterface bi)
   {
      super ("Proxy_" + APS.getProxyId ());

      this.sconi = sconi;
      this.barInter = bi;
   }

  /**
   *  Ciclo de vida do thread agente prestador de serviço.
   */

   @Override
   public void run ()
   {
      Message inMessage = null,                                      // mensagem de entrada
              outMessage = null;                      // mensagem de saída

      inMessage = (Message) sconi.readObject ();                     // ler pedido do cliente
      if(inMessage.getStudentID()!=-1){
          studentId=inMessage.getStudentID();
      }
      try
      { outMessage = barInter.processAndReply (inMessage);         // processá-lo
      }
      catch (MyException e)
      { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
        //GenericIO.writelnString (e.getMessageVal ().toString ());
        System.exit (1);
      }
      
      if(studentState!=null){
          outMessage.addStudentState(studentState, studentId);
      }
      if(waiterState!=null){
          outMessage.addWaiterState(waiterState);
      }
      if(chefState!=null){
          outMessage.addChefState(chefState);
      }
      
      sconi.writeObject (outMessage);                                // enviar resposta ao cliente
      sconi.close ();                                                // fechar canal de comunicação
   }

  /**
   *  Geração do identificador da instanciação.
   *
   *    @return identificador da instanciação
   */

   private static int getProxyId ()
   {
      Class<?> cl = null;                                  // representação do tipo de dados APS na máquina
                                                           //   virtual de Java
      int proxyId;                                         // identificador da instanciação

      try
      { cl = Class.forName ("APS");
      }
      catch (ClassNotFoundException e)
      { GenericIO.writelnString ("O tipo de dados APS não foi encontrado!");
        e.printStackTrace ();
        System.exit (1);
      }

      synchronized (cl)
      { proxyId = nProxy;
        nProxy += 1;
      }

      return proxyId;
   }

  /**
   *  Obtenção do canal de comunicação.
   *
   *    @return canal de comunicação
   */

   public ServerCom getScon ()
   {
      return sconi;
   }

    @Override
    public void setStudentState(String st) {
        studentState=st;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public void setWaiterState(String st) {
        waiterState=st;
    }

    @Override
    public void setChefState(String st) {
        chefState=st;
    }
   
   
}