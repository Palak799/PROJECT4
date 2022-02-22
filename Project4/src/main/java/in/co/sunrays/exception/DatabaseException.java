package in.co.sunrays.exception;


/**
 * DatabaseException is prpogated by DAO classes when an unhandled Database
 * exception occurred
 * @author priyal
 *
 */
public class DatabaseException extends Exception{
	
	/**
	 * @param msg
	 * error message
	 */
	public DatabaseException(String msg){
		super(msg);
	}

}
