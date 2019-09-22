package es.rmc.exception;

public class FlightsException extends Exception {

	private static final long serialVersionUID = 8747156543056979200L;
	
	  // =========================================== Fields =========================================
	
	private FlightsExceptionType exception;
	
	 public enum FlightsExceptionType {
		 
		  INVALID_INTERVAL("Interval time greater than 24 hours"), 
		  INVALID_DATES("Arrival datetime must be greater than departure datetime");

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
		}
	
	 // =========================================== Constructors =========================================
	public FlightsException(FlightsExceptionType exception) {
		this.exception = exception;
	}

	 // =========================================== Getters and setters =========================================
	public FlightsExceptionType getException() {
		return exception;
	}	
}

