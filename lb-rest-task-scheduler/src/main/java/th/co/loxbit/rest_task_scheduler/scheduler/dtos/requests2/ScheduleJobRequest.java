package th.co.loxbit.rest_task_scheduler.scheduler.dtos.requests2;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ScheduleJobRequest(
    @NotNull(message = "Start timestamp cannot be null") @Min(value = 0, message = "Start timestamp must be a positive value") Integer start,
    @NotNull(message = "End timestamp cannot be null") @Min(value = 0, message = "End timestamp must be a positive value") Integer end,
    @NotNull(message = "Message cannot be null") @Size(min = 1, max = 255, message = "Message must be between 1 and 255 characters") String message,
    @NotNull(message = "Missing user ID") String userId) {
}
