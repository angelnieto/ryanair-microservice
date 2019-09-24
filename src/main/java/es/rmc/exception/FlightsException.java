package es.rmc.exception;

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
}

