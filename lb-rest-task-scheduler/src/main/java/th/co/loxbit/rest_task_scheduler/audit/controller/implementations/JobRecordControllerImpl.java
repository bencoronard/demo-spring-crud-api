package th.co.loxbit.rest_task_scheduler.audit.controller.implementations;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.controller.JobRecordController;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;
import th.co.loxbit.rest_task_scheduler.audit.service.JobRecordService;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;

@RestController
@RequiredArgsConstructor
public class JobRecordControllerImpl implements JobRecordController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobRecordService jobRecordService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> test() {

    jobRecordService.addJobRecord(
        "helloworld1234",
        0,
        1726029777,
        "BEN",
        Instant.now().getEpochSecond(),
        JobRecordType.OVERRIDE);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils
        .createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void testError() {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      throw new UnsupportedOperationException("Unimplemented method 'testError'");
    }, 9000);
  }

}
