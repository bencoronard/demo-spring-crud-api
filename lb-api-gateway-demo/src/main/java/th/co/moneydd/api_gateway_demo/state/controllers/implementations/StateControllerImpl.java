package th.co.moneydd.api_gateway_demo.state.controllers.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.moneydd.api_gateway_demo.common.http.dtos.GlobalResponseBody;
import th.co.moneydd.api_gateway_demo.state.controllers.StateController;
import th.co.moneydd.api_gateway_demo.state.services.StateService;

@RestController
@RequiredArgsConstructor
public class StateControllerImpl implements StateController {

  private final StateService stateService;

  @Override
  public ResponseEntity<GlobalResponseBody> status() {

    boolean state = stateService.getState();

    GlobalResponseBody body = GlobalResponseBody.builder().respCode(0).desc(state ? "OPEN" : "CLOSED").build();

    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody> open() {

    stateService.setState(true);

    GlobalResponseBody body = GlobalResponseBody.builder().respCode(0).build();

    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody> close() {

    stateService.setState(false);

    GlobalResponseBody body = GlobalResponseBody.builder().respCode(0).build();

    return new ResponseEntity<>(body, HttpStatus.OK);
  }

}
