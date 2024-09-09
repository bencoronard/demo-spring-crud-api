package th.co.loxbit.rest_task_scheduler.audit.entities;

public enum ActionType {

  CREATE("CREATE"),
  EDIT("EDIT"),
  DELETE("DELETE"),
  OVERRIDE("OVERRIDE");

  private final String action;

  ActionType(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }

  public static ActionType fromAction(String action) {
    for (ActionType actionType : values()) {
      if (actionType.getAction().equals(action)) {
        return actionType;
      }
    }
    throw new IllegalArgumentException("Invalid action: " + action);
  }

}
