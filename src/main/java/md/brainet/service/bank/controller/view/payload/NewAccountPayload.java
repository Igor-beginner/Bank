package md.brainet.service.bank.controller.view.payload;

import md.brainet.service.bank.entity.AccountType;

public record NewAccountPayload(
        String title,
        AccountType accountType,
        Integer balance
) {
}
