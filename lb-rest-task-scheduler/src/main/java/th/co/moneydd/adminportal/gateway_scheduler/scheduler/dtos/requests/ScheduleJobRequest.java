package th.co.moneydd.adminportal.gateway_scheduler.scheduler.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ScheduleJobRequest(
    @NotNull(message = "Start timestamp cannot be null") @Min(value = 0, message = "Start timestamp must be a positive value") Long start,
    @NotNull(message = "End timestamp cannot be null") @Min(value = 0, message = "End timestamp must be a positive value") Long end,
    @Size(max = 255, message = "Message must not exceed 255 characters") String message) {
}
