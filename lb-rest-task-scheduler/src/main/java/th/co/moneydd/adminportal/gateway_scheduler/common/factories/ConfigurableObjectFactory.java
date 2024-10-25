package th.co.moneydd.adminportal.gateway_scheduler.common.factories;

public interface ConfigurableObjectFactory<T, U> {

  T create(U config);

}
