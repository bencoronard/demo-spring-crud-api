package th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.inbound;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CloseGatewayRequestInbound(
    @NotNull(message = "End timestamp cannot be null") @Min(value = 0, message = "End timestamp must be a positive value") Long end) {
}
