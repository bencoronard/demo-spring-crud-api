package th.co.loxbit.adminportal.gateway_scheduler.common.factories;

public interface ConfigurableObjectFactory<T, U> {

  T create(U config);

}
