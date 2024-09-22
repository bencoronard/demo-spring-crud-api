package th.co.loxbit.rest_task_scheduler.audit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;

@RequestMapping("/api/audit")
public interface AuditRecordController {

  String USER_ID_KEY = "USER_ID";

  @PostMapping("/test")
  ResponseEntity<GlobalResponseBody<Void>> test(@RequestAttribute(USER_ID_KEY) String userId);

  @GetMapping("/test")
  void testError();

}
