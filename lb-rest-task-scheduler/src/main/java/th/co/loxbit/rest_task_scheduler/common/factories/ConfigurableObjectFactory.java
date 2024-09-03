package th.co.loxbit.rest_task_scheduler.common.factories;

public interface ConfigurableObjectFactory<T, U> {

  T create(U config);

}
