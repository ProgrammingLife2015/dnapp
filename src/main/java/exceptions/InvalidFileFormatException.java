package exceptions;

public class InvalidFileFormatException extends RuntimeException {

  /**
   * The serial version of this exception
   */
  private static final long serialVersionUID = -3175084850174905842L;

  /**
   * Throw this exception when the file format is not not correct (as an example, a string as id
   * instead of an int should throw this exception)
   */
  public InvalidFileFormatException() {
    super();
  }

  /**
   * Throw this exception when the file format is incorrect and you want to give a message with this
   * exception
   */
  public InvalidFileFormatException(String message) {
    super(message);
  }
}
