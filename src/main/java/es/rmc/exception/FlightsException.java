package es.rmc.exception;

import java.util.function.Consumer;

/**  
 * Custom microservice exception
 * 
 * @author rmc
 *
 */
public class FlightsException extends Exception {

	private static final long serialVersionUID = 8747156543056979200L;
	
	  // =========================================== Fields =========================================
	
	 //custom enum attribute to generate exceptions with a pair of code/message 
	 private FlightsExceptionType exception;
	 
	 // =========================================== Constructors =========================================
	public FlightsException(FlightsExceptionType exception) {
		this.exception = exception;
	}

	 // =========================================== Getters and setters =========================================
	public FlightsExceptionType getException() {
		return exception;
	}	
	
	 public enum FlightsExceptionType {
		 
		  INVALID_INTERVAL("Interval time greater than 24 hours"), 
		  INVALID_DATES("Arrival datetime must be greater than departure datetime"), 
		  COMMUNICATION_ERROR("Required third-party resources do not respond");

		  // =========================================== Fields =========================================

		   private String message;
		  
		  // =========================================== Constructors =========================================

		    private FlightsExceptionType(String message) {
			   this.message = message;
		    }

		    // =========================================== Getters and setters =========================================
		    public String getMessage() {
				return message;
			}
		    
		    public String getCode() {
				return message;
			}
		}
	 
	// =========================================== Methods =========================================
//	 static <T, E extends Exception> Consumer<T>
//	  consumerWrapper(Consumer<T> consumer, Class<E> clazz) {
//	  
//	    return i -> {
//	        try {
//	            consumer.accept(i);
//	        } catch (Exception ex) {
//	            try {
//	                E exCast = clazz.cast(ex);
//	                System.err.println(
//	                  "Exception occured : " + exCast.getMessage());
//	            } catch (ClassCastException ccEx) {
//	                throw ex;
//	            }
//	        }
//	    };
//	}
	 
	 @FunctionalInterface
	 public interface ThrowingConsumer<T, E extends FlightsException> {
	     void accept(T t) throws E;
	 }
	 
	 public static <T> Consumer<T> throwingConsumerWrapper(
			  ThrowingConsumer<T, FlightsException> throwingConsumer) {
			  
			    return i -> {
			        try {
			            throwingConsumer.accept(i);
			        } catch (FlightsException ex) {
			            throw new RuntimeException(ex);
			        }
			    };
			}
}

