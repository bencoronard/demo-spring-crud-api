package th.co.loxbit.rest_task_scheduler.scheduler.repository;

import java.util.List;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.data.util.Pair;

import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;

public interface ScheduleRepository {

  String CLOSE_TASK_KEY = "closeTask";
  String OPEN_TASK_KEY = "openTask";

  Map<String, Pair<JobDetail, ? extends Trigger>> findScheduleById(String jobId) throws SchedulerException;

  List<GatewaySchedule> findAllSchedules() throws SchedulerException;

  void createSchedule(String jobId, String message, Trigger jobTriggerStart, Trigger jobTriggerEnd)
      throws SchedulerException;

  void deleteScheduleById(String jobId) throws SchedulerException;

}
