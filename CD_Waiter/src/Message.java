
import java.io.*;

public class Message implements Serializable{
    
    //Servidor-Cliente
    public static final int acknowledge=0;
    public static final int ackAllPortionsDel=1;
    public static final int ackNextPortionRed=2;
    public static final int ackOrderCompleted=3;
    public static final int ackEverybodyChosen=4;
    public static final int ackEverybodyFinished=5;
    public static final int ackEnterTable=6;
    public static final int ackwaiterEvent=7;
    public static final int ackFinishEating=8;
    public static final int ackState=9;
    
    
    public static final int error=99;
    
    
    //Cliente-Servidor
    //Bar
    public static final int alertTheWaiter=101;
    public static final int lookAround=102;
    public static final int returnToTheBar=103;
    public static final int preapareTheBill=104;
    public static final int enterBar=105;
    public static final int callTheWaiter=106;
    public static final int signalTheWaiter=107;
    public static final int shouldHaveArrivedEarlier=108;
    public static final int exitBar=109;
    
    //Kitchen
    public static final int watchTheNews=201;
    public static final int startPreparation=202;
    public static final int proceedToPresent=203;
    public static final int haveAllPortionsBeenDelivered=204;
    public static final int haveNextPortionReady=205;
    public static final int hasTheOrderBeenCompleted=206;
    public static final int continuePreparation=207;
    public static final int cleanUp=208;
    public static final int handTheNoteToTheChef=209;
    public static final int collectPortion=210;
    
    //Table
    public static final int saluteClient=301;
    public static final int getThePad=302;
    public static final int deliverPortion=303;
    public static final int presentTheBill=304;
    public static final int sayGoodbye=305;
    public static final int enterTable=306;
    public static final int readTheMenu=307;
    public static final int hasEverybodyChosen=308;
    public static final int prepareTheOrder=309;
    public static final int describeTheOrder=310;
    public static final int informCompanion=311;
    public static final int joinTheTalk=312;
    public static final int startEating=313;
    public static final int endEating=314;
    public static final int hasEverybodyFinished=315;
    public static final int honorTheBill=316;
    public static final int goHome=317;
    
    //GeneralMemory
    public static final int studentLog=401;
    public static final int chefLog=402;
    public static final int waiterLog=403;
    
    
    private int msgType=-1;
    
    private int posAtTable=-1;
    private int posEating=-1;
    
    private Boolean control=null;
    private String waiterEvent=null;
    
    private String waiterState=null;
    private String chefState=null;
    private String studentState=null;
    private int studentID=-1;
    
    private String errorMsg=null;
    
    private String passState=null;
    

    public Message(int type){
        msgType=type;
    }
    
    public Message(int type, int id){
        msgType=type;
        studentID=id;
    }
    
    public Message(int type, int pos, int id){
        msgType=type;
        posEating=pos;
        studentID=id;
    }
    
    public Message(int type, boolean reply){
        msgType=type;
        control=reply;
    }
    
    public Message(int type, boolean reply, int id){
        msgType=type;
        control=reply;
        studentID=id;
    }
          
    public Message(int type, String st){
        msgType=type;
        switch(type){
            case Message.waiterLog:
                waiterState=st;
                break;
            case Message.error:
                errorMsg=st;
                break;
            case Message.chefLog:
                chefState=st;
                break;
            case Message.ackState:
                passState=st;
                break;
        }
    }
    
    public Message(int type, String event, String state){
        msgType=type;
        waiterEvent=event;
        waiterState=state;
    }
    
    public Message(int type, String st, int id){
        msgType=type;
        studentState=st;
        studentID=id;
    }
    
    public Message(int type, int pos, String state, int id){
        msgType=type;
        posAtTable=pos;
        passState=state;
        studentID=id;
    }
    
    
    public void addStudentState(String st, int id){
        studentState=st;
        studentID=id;
    }
    
    public void addWaiterState(String st){
        waiterState=st;
    }
    
    public void addChefState(String st){
        chefState=st;
    }
    
    

    public int getType() {
        return msgType;
    }

    public int getPosAtTable() {
        return posAtTable;
    }
    
    public int getPosEating() {
        return posEating;
    }

    public boolean getReply() {
        return control;
    }

    public String getWaiterEvent() {
        return waiterEvent;
    }

    public String getWaiterState() {
        return waiterState;
    }

    public String getChefState() {
        return chefState;
    }

    public String getStudentState() {
        return studentState;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    
    public String getState(){
        return passState;
    }
    
}
