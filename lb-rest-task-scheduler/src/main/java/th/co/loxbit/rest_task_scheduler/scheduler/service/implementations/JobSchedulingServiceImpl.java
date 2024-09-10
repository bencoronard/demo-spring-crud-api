package th.co.loxbit.rest_task_scheduler.scheduler.service.implementations;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.exceptions.CatchAllServiceException;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;
import th.co.loxbit.rest_task_scheduler.scheduler.repository.ScheduleRepository;
import th.co.loxbit.rest_task_scheduler.scheduler.service.JobSchedulingService;

@Service
@RequiredArgsConstructor
public class JobSchedulingServiceImpl implements JobSchedulingService {

  private final ScheduleRepository scheduleRepository;

  @Override
  public void scheduleJob(int startTime, int endTime, String message) {

    final int SECTION_CODE = 0;

    try {

      String jobId = UUID.randomUUID().toString();

      Trigger closeGatewayTrigger = TriggerBuilder.newTrigger().withIdentity("closeOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(startTime)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();

      Trigger openGatewayTrigger = TriggerBuilder.newTrigger().withIdentity("openOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(endTime)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();

      scheduleRepository.createSchedule(jobId, message, closeGatewayTrigger, openGatewayTrigger);

    } catch (RuntimeException | SchedulerException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }
  }

  @Override
  public void descheduleJob(String jobId) {

    final int SECTION_CODE = 0;

    try {

      scheduleRepository.deleteScheduleById(jobId);

    } catch (RuntimeException | SchedulerException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }

  }

  @Override
  public List<GatewaySchedule> getScheduledJobs() {

    final int SECTION_CODE = 0;

    try {

      return scheduleRepository.findAllSchedules();

    } catch (RuntimeException | SchedulerException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }

  }

  @Override
  public void updateJob(String jobId, int newStartTime, int newEndTime, String newMessage) {

    final int SECTION_CODE = 0;

    try {

      scheduleJob(newStartTime, newEndTime, newMessage);

      scheduleRepository.deleteScheduleById(jobId);

    } catch (RuntimeException | SchedulerException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }

  }

}
