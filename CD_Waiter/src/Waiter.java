
/**
 * Entidade Waiter. Entidade em que o seu lifecycle replica o de um Empregado, sendo esse o papel desta entidade no problema.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Waiter extends Thread {

    /**
     * Enumerado que mantém listado todos os estados possíveis para o Waiter.
     */
    /*public static enum state{
        APPRAISING_SITUATION,
        PRESENTING_THE_MENU,
        TAKING_THE_ORDER,
        PLACING_THE_ORDER,
        WAITING_FOR_PORTION,
        PROCESSING_THE_BILL
    };*/
    private String st;           //estado interno do Waiter

    //zonas partilhadas
    private TableStub table;
    private KitchenStub kitchen;
    private BarStub bar;

    //info
    private int N = 7, //número de estudantes: 7 por defeito
            goodbye;        //número de estudantes que foram embora

    /**
     * Construtor da entidade Waiter.
     *
     * @param k Kitchen que indica a referência para a zona partilhada Kitchen a
     * considerar.
     * @param b Bar que indica a referência para a zona partilhada Bar a
     * considerar.
     * @param t Table que indica a referência para a zona partilhada Table a
     * considerar.
     */
    public Waiter(KitchenStub k, BarStub b, TableStub t) {
        kitchen = k;
        bar = b;
        table = t;
        goodbye = 0;
    }

    /**
     * Retorna o estado atual do Waiter.
     *
     * @return state que indica qual o estado atual do Waiter.
     */
    public String getStatus() {
        return this.st;
    }

    public void setStatus(String state) {
        st = state;
    }

    /*
     * Representa o lifecycle de cada entidade criada deste tipo.
     */
    @Override
    public void run() {
        boolean run = true;
        while (run) {
            switch (bar.lookAround()) {
                case "presentMenu":
                    table.saluteTheClient();
                    bar.returnToTheBar();

                    break;

                case "takeOrder":
                    table.getThePad();
                    kitchen.handTheNoteToTheChef();
                    bar.returnToTheBar();
                    break;

                case "foodReady":
                    kitchen.collectPortion();
                    table.deliverPortion();
                    bar.returnToTheBar();

                    break;

                case "getBill":
                    bar.prepareTheBill();
                    table.presentTheBill();
                    bar.returnToTheBar();
                    break;

                case "end":
                    table.sayGoodbye();
                    goodbye += 1;
                    if (goodbye == N) {
                        run = false;
                    } else {
                        //st=state.APPRAISING_SITUATION;
                        //mem.log(this);
                        bar.returnToTheBar();
                    }
                    break;
            }
        }
    }
}
