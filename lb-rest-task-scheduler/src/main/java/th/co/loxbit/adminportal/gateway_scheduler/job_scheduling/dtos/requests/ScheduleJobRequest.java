package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ScheduleJobRequest(
    @Min(value = 0, message = "Start timestamp must be a positive value") Long start,
    @Min(value = 0, message = "End timestamp must be a positive value") Long end,
    @Size(max = 255, message = "Message must not exceed 255 characters") String message) {
}
