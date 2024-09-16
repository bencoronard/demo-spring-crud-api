package th.co.loxbit.rest_task_scheduler.audit.controller.implementations;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.controller.AuditRecordController;
import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.rest_task_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;

@RestController
@RequiredArgsConstructor
public class AuditRecordControllerImpl implements AuditRecordController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final AuditRecordService auditRecordService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> test() {

    auditRecordService.addAuditRecord(
        "helloworld1234",
        0,
        1726029777,
        "BEN",
        Instant.now().getEpochSecond(),
        AuditRecordType.OVERRIDE);

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
