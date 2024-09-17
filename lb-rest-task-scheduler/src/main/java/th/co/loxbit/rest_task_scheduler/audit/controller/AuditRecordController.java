package th.co.loxbit.rest_task_scheduler.audit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;

@RequestMapping("/audit")
public interface AuditRecordController {

  @PostMapping("/test")
  ResponseEntity<GlobalResponseBody<Void>> test(@AuthenticationPrincipal String userId);

  @GetMapping("/test")
  void testError();

}
