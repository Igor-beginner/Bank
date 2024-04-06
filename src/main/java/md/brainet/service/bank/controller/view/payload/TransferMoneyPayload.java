package md.brainet.service.bank.controller.view.payload;

import jakarta.validation.constraints.PositiveOrZero;

public record TransferMoneyPayload(
        Integer sender,
        Integer receiver,

        @PositiveOrZero
        Integer sum
) {
}
