package md.brainet.service.bank.service;

import lombok.AllArgsConstructor;
import md.brainet.service.bank.controller.view.payload.NewAccountPayload;
import md.brainet.service.bank.controller.view.payload.TransferMoneyPayload;
import md.brainet.service.bank.entity.Account;
import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.repository.AccountRepository;
import md.brainet.service.bank.service.exception.AccountNotExistException;
import md.brainet.service.bank.service.exception.NotEnoughMoneyException;
import md.brainet.service.bank.service.exception.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findAccountBy(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new UnknownAccountException("Account with id " + id + " doesn't exist"));
    }

    public void saveNewAccount(NewAccountPayload newAccountPayload, User user) {
        String id = UUID.randomUUID().toString();
        Account newAccount = Account.builder()
                .title(newAccountPayload.title())
                .accountType(newAccountPayload.accountType())
                .balance(newAccountPayload.balance())
                .owner(user)
                .enabled(true)
                .build();

        accountRepository.save(newAccount);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(TransferMoneyPayload payload) {
        Optional<Account> senderOptional = accountRepository.findById(payload.sender());
        Optional<Account> reciverOptional = accountRepository.findById(payload.receiver());

        if(senderOptional.isEmpty() || reciverOptional.isEmpty()) {
            throw new AccountNotExistException("Account is not exist");
        }

        Account sender = senderOptional.get();
        Account receiver = reciverOptional.get();

        int senderBalance = sender.getBalance();
        int receiverBalance = receiver.getBalance();

        if (senderBalance < payload.sum()) {
            throw new NotEnoughMoneyException(String
                    .format("Requested sum is %s, but actual balance is less then that = %s", payload.sum(), senderBalance));
        }

        senderBalance -= payload.sum();
        receiverBalance += payload.sum();

        sender.setBalance(senderBalance);
        receiver.setBalance(receiverBalance);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
