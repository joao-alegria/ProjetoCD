package Entities;

import MainPackage.*;
import SharedRegionStub.*;

/**
 * Entidade Student. Entidade em que o seu lifecycle replica o de um Estudante,
 * sendo esse o papel desta entidade no problema.
 *
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class Student extends Thread {

    /**
     * Enumerado que mantém listado todos os estados possíveis para o Student.
     */
    /*public static enum state {
        GOING_TO_THE_RESTAURANT, 
        TAKING_A_SEAT_AT_THE_TABLE, 
        SELECTING_THE_COURSES,
        ORGANIZING_THE_ORDER,
        CHATTING_WITH_COMPANIONS,
        ENJOYING_THE_MEAL,
        PAYING_THE_MEAL,
        GOING_HOME
    };*/
    private String st;           //estado interno do Student

    //zonas partilhadas
    private TableStub table;
    private BarStub bar;

    //informações necessárias
    private boolean first;      //true se é o primeiro
    private boolean last;       //true se é o ultímo
    private int N = 7, //número de estudantes
            M = 3, //número de pratos por estudante
            ID;     //identificador do estudante

    /**
     * Construtor da entidade Student.
     *
     * @param b Bar que indica a referência para a zona partilhada Bar a
     * considerar.
     * @param t Table que indica a referência para a zona partilhada Table a
     * considerar.
     * @param id int que indica qual o ID do Estudante.
     */
    public Student(BarStub b, TableStub t, int id) {
        table = t;
        bar = b;
        ID = id;
    }

    /*
     * Representa o lifecycle de cada entidade criada deste tipo.
     */
    @Override
    public void run() {
        walk();
        bar.enter();
        int pos = table.enter();

        if (pos == 1) {
            this.first = true;
            this.last = false;
        } else if (pos == N) {
            this.first = false;
            this.last = true;
        } else {
            this.first = false;
            this.last = false;
        }

        table.readTheMenu();

        if (last) {
            bar.callTheWaiter();
        }

        if (first) {
            while (!table.hasEverybodyChosen()) {
                table.prepareTheOrder();
            }
            table.describeTheOrder();
        } else {
            table.informCompanion();
        }
        table.joinTheTalk();
        for (int i = 0; i < M; i++) {
            table.startEating();
            table.endEating();
            if (i < M - 1) {
                if (table.hasEverybodyFinished()) {
                    bar.signalTheWaiter();
                }
                table.joinTheTalk();
            }
        }
        if (last) {
            bar.shouldHaveArrivedEarlier();
            table.honorTheBill();

        }
        table.goHome();
        bar.exit();
    }

    /**
     * Simula o tempo que um Estudante demora a ir para o restaurante.
     *
     * @throws MyException Exception que aparece quando existe um erro de
     * execução.
     */
    private void walk(){
        try {
            Thread.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
        }
    }

    /**
     * Retorna o estado atual do Estudante.
     *
     * @return String que indica qual o estado atual do Student.
     */
    public String getStatus() {
        return this.st;
    }

    public void setStatus(String state) {
        st = state;
    }

    /**
     * Retorna o ID do Estudante.
     *
     * @return int que indica qual o ID do Student.
     */
    public int getID() {
        return this.ID;
    }

}
