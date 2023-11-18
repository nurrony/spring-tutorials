package info.nurrony.tutorials.spring.eventrestapi.services;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Account;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.publishers.AccountEventPublisher;
import info.nurrony.tutorials.spring.eventrestapi.repositories.AccountRepository;
import info.nurrony.tutorials.spring.eventrestapi.rest.dtos.AccountDto;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountEventPublisher accountEventPublisher;

    public void registerAccount(final AccountDto request) throws Exception {
        final Account result = accountRepository.save(Account.builder()
                .password(request.getPassword())
                .nickname(request.getNickname())
                .userName(request.getUserName())
                .createDate(new Date())
                .state(1).build());

        accountEventPublisher.pushCreateUserEvent(result.getId(), result.getUserName());
    }

}
