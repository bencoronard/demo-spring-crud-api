package th.co.loxbit.adminportal.gateway_scheduler.scheduler.repository;

import java.time.Instant;
import java.util.List;
import org.quartz.SchedulerException;

import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;

public interface ScheduleRepository {

  String CLOSE_TASK_KEY = "close";
  String OPEN_TASK_KEY = "open";
  String JDATA_MSG_KEY = "message";
  String JDATA_CREATOR_KEY = "creator";
  String JDATA_START_KEY = "start";
  String JDATA_END_KEY = "end";

  String createJobSchedule(Instant start, Instant end, String message, String createdBy) throws SchedulerException;

  String createPartialJobSchedule(Instant start, Instant end, String message, String createdBy)
      throws SchedulerException;

  GatewaySchedule findJobScheduleById(String jobId) throws SchedulerException;

  List<GatewaySchedule> findAllJobSchedules() throws SchedulerException;

  GatewaySchedule deleteJobScheduleById(String jobId) throws SchedulerException;

  boolean updateJobById(String jobId, Instant newStart, Instant newEnd, String newMessage, String updatedBy)
      throws SchedulerException;

}
