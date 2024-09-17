package th.co.moneydd.api_gateway_demo.state.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import th.co.moneydd.api_gateway_demo.common.http.dtos.GlobalResponseBody;

public interface StateController {

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody> status();

  @PostMapping("/open")
  ResponseEntity<GlobalResponseBody> open();

  @PostMapping("/close")
  ResponseEntity<GlobalResponseBody> close();

}
