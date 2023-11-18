package info.nurrony.tutorials.spring.eventrestapi.rest.dtos;

import java.util.Date;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AccountDto {
    private final Long id;
    private final String password;
    private final String userName;
    private final String nickname;
    private final Integer state;
    private final Date createDate;

    public static AccountDto ofEntity(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .userName(entity.getUserName())
                .nickname(entity.getNickname())
                .state(entity.getState())
                .createDate(entity.getCreateDate())
                .build();
    }
}
