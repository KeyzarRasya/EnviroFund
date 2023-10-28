package enviro.fund.production.exception;

public class TransactionalException extends RuntimeException{
    public TransactionalException(String msg){
        super(msg);
    }
}
