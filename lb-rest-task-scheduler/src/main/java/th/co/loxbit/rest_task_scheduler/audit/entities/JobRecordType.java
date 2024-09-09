package th.co.loxbit.rest_task_scheduler.audit.entities;

public enum JobRecordType {

  CREATE("CREATE"),
  EDIT("EDIT"),
  DELETE("DELETE"),
  OVERRIDE("OVERRIDE");

  private final String type;

  JobRecordType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static JobRecordType fromType(String type) {
    for (JobRecordType recordType : values()) {
      if (recordType.getType().equals(type)) {
        return recordType;
      }
    }
    throw new IllegalArgumentException("Invalid type: " + type);
  }

}
