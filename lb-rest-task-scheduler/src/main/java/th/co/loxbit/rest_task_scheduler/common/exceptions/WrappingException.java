package th.co.loxbit.rest_task_scheduler.common.exceptions;

import lombok.Getter;

@Getter
public class WrappingException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int SERVICE_CODE;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public WrappingException(int serviceCode, Throwable cause) {
    super(cause);
    this.SERVICE_CODE = serviceCode;
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public int getRespCode() {

    Throwable cause = this.getCause();

    // CASE_1(1): a service exception wrapped in THIS wrapper
    if (cause instanceof WrappableException) {
      WrappableException wrappedException = (WrappableException) cause;
      return this.SERVICE_CODE + wrappedException.getERROR_CODE();
    }

    // CASE_2: a wrapper wrapped in THIS wrapper
    if (cause instanceof WrappingException) {

      WrappingException wrappedWrapper = (WrappingException) cause;
      Throwable wrappedCause = wrappedWrapper.getCause();

      // CASE_2(1): a service exception wrapped in the wrapper
      if (wrappedCause instanceof WrappableException) {
        WrappableException wrappedException = (WrappableException) wrappedCause;
        return this.SERVICE_CODE + wrappedWrapper.getSERVICE_CODE() / 10 + wrappedException.getERROR_CODE();
      }

      // CASE_2(2): a Java exception wrapped in the wrapper
      return this.SERVICE_CODE + wrappedWrapper.getSERVICE_CODE() / 10;
    }

    // CASE_1(2): a Java exception wrapped in THIS wrapper
    return this.SERVICE_CODE;
  }

  // ---------------------------------------------------------------------------//

  public String getRespMsg() {

    Throwable cause = this.getCause();

    // CASE_1(1): a service exception wrapped in THIS wrapper
    if (cause instanceof WrappableException) {
      WrappableException wrappedException = (WrappableException) cause;
      return wrappedException.getRESP_MSG();
    }

    // CASE_2: a wrapper wrapped in THIS wrapper
    if (cause instanceof WrappingException) {

      WrappingException wrappedWrapper = (WrappingException) cause;
      Throwable wrappedCause = wrappedWrapper.getCause();

      // CASE_2(1): a service exception wrapped in the wrapper
      if (wrappedCause instanceof WrappableException) {
        WrappableException wrappedException = (WrappableException) wrappedCause;
        return wrappedException.getRESP_MSG();
      }

      // CASE_2(2): a Java exception wrapped in the wrapper
      return "Unhandled exception at server side";
    }

    // CASE_1(2): a Java exception wrapped in THIS wrapper
    return "Unhandled exception at server side";
  }

  // ---------------------------------------------------------------------------//

  public String getErrorMessage() {

    Throwable cause = this.getCause();

    // CASE_1(1): a service exception wrapped in THIS wrapper
    if (cause instanceof WrappableException) {
      WrappableException wrappedException = (WrappableException) cause;
      return wrappedException.getMessage();
    }

    // CASE_2: a wrapper wrapped in THIS wrapper
    if (cause instanceof WrappingException) {

      WrappingException wrappedWrapper = (WrappingException) cause;
      Throwable wrappedCause = wrappedWrapper.getCause();

      // CASE_2(1): a service exception wrapped in the wrapper
      if (wrappedCause instanceof WrappableException) {
        WrappableException wrappedException = (WrappableException) wrappedCause;
        return wrappedException.getMessage();
      }

      // CASE_2(2): a Java exception wrapped in the wrapper
      return wrappedCause.getMessage();
    }

    // CASE_1(2): a Java exception wrapped in THIS wrapper
    return cause.getMessage();
  }

}
