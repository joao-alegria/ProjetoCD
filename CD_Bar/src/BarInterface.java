/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joao-alegria
 */
public class BarInterface {
    
    BarInterface(Bar b){
        
    }

    public Message processAndReply(Message inMessage) throws MyException{
        switch(inMessage.getType()){
            case Message.alertTheWaiter:
                break;
            case Message.lookAround:
                break;
            case Message.preapareTheBill:
                break;
            case Message.enterBar:
                if(inMessage.getStudentID()<0 || inMessage.getStudentID()>7){
                    throw new MyException("ID de estudante inválido!");
                }
                break;
            case Message.callTheWaiter:
                if(inMessage.getStudentID()<0 || inMessage.getStudentID()>7){
                    throw new MyException("ID de estudante inválido!");
                }
                break;
            case Message.signalTheWaiter:
                if(inMessage.getStudentID()<0 || inMessage.getStudentID()>7){
                    throw new MyException("ID de estudante inválido!");
                }
                break;
            case Message.shouldHaveArrivedEarlier:
                if(inMessage.getStudentID()<0 || inMessage.getStudentID()>7){
                    throw new MyException("ID de estudante inválido!");
                }
                break;
            case Message.exitBar:
                if(inMessage.getStudentID()<0 || inMessage.getStudentID()>7){
                    throw new MyException("ID de estudante inválido!");
                }
                break;
        }
        
        switch(inMessage.getType()){
            case Message.alertTheWaiter:
                break;
            case Message.lookAround:
                break;
            case Message.preapareTheBill:
                break;
            case Message.enterBar:
                break;
            case Message.callTheWaiter:
                break;
            case Message.signalTheWaiter:
                break;
            case Message.shouldHaveArrivedEarlier:
                break;
            case Message.exitBar:
                break;
        }
    }
    
}
