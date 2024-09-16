package th.co.loxbit.rest_task_scheduler.audit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;

@RestController
@RequestMapping("/audit")
public interface JobRecordController {

  @PostMapping("/test")
  ResponseEntity<GlobalResponseBody<Void>> test();

  @GetMapping("/test")
  void testError();

}
