package th.co.moneydd.api_gateway_demo.state.services.implementations;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import th.co.moneydd.api_gateway_demo.state.services.StateService;

@Service
public class StateServiceImpl implements StateService {

  private AtomicBoolean state = new AtomicBoolean(true);

  @Override
  public boolean getState() {
    return state.get();
  }

  @Override
  public void setState(boolean state) {
    this.state.set(state);
  }

}
