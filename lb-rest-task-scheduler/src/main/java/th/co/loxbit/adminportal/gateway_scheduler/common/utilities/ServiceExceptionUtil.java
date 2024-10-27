package th.co.loxbit.adminportal.gateway_scheduler.common.utilities;

import java.util.concurrent.Callable;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappingException;

public class ServiceExceptionUtil {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public static <T> T executeWithExceptionWrapper(Callable<T> action, int serviceCode) {
    try {
      return action.call();
    } catch (Exception e) {
      throw new WrappingException(serviceCode, e);
    }
  }

}
