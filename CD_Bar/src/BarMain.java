
import genclass.*;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarMain {

    /**
     * Número do port de escuta do serviço a ser prestado (22501, por defeito)
     *
     * @serialField portNumb
     */

    private static final int portNumb = 22501;
    public static boolean waitConnection;                              // sinalização de actividade

    /**
     * Programa principal.
     */
    public static void main(String[] args) {
        GeneralMemoryStub mem;
        Bar bar;
        BarInterface barInter;
        ServerCom scon, sconi;                               // canais de comunicação
        APS aps;                                // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerCom(portNumb);                      // criação do canal de escuta e sua associação
        scon.start();                                        // com o endereço público
        mem=new GeneralMemoryStub("l040101-ws04.ua.pt", 22504);
        bar = new Bar(mem);                                  // activação do serviço
        barInter = new BarInterface(bar);         // activação do interface com o serviço
        GenericIO.writelnString("O serviço foi estabelecido!");
        GenericIO.writelnString("O servidor esta em escuta.");

        /* processamento de pedidos */
        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();                           // entrada em processo de escuta
                aps = new APS(sconi, barInter);                  // lançamento do agente prestador do serviço
                aps.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(BarMain.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        scon.end();                                         // terminação de operações
        GenericIO.writelnString("O servidor foi desactivado.");
    }
}
