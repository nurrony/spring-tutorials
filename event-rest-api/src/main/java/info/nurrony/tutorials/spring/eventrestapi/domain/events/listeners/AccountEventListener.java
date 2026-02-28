package info.nurrony.tutorials.spring.eventrestapi.domain.events.listeners;

import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Account;
import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Post;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.CreateAccountEvent;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.LoginAccountEvent;
import info.nurrony.tutorials.spring.eventrestapi.repositories.AccountRepository;
import info.nurrony.tutorials.spring.eventrestapi.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    @EventListener // when createAccount
    public void pushEmailListener(CreateAccountEvent event) {
        log.info(this.getClass().getName() + ".pushEmailListener Start");

        log.info("userId : " + event.getUserId());
        log.info("userName : " + event.getUserName());

        System.out.println("push Email To " + event.getUserName() + " Hello!");
    }

    @SuppressWarnings("null")
    @EventListener // when createAccount
    @Transactional(rollbackFor = Exception.class)
    public void createDefaultPost(CreateAccountEvent event) {

        final Account account = accountRepository.findById(event.getUserId())
                .orElseThrow(() -> new NoSuchElementException("cannot found account with id : " + event.getUserId()));

        postRepository.save(Post.builder()
                .createDate(new Date())
                .content("default Post")
                .title("Default Post from Listener")
                .account(account).build());

    }

    @EventListener // when login success
    public void checkAccountLogin(LoginAccountEvent event) {
        log.info(this.getClass().getName() + ".user Login Completed");
        log.info("userId : " + event.getUserId());
        log.info("userRole : " + event.getUserRole());
    }
}
