/**
 * Exceção criada para manter o código uniforme.
 * @author João Alegria[85048] e Lucas Silva[85036]
 */
public class MyException extends Exception{
    
    /**
     * Construto da classe
     * @param errorMessage String que indica qual o erro a indicar.
     */
    public MyException(String errorMessage){
        super(errorMessage);
    }
    
}

