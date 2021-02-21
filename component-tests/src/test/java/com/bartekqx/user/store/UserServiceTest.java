package com.bartekqx.user.store;

import com.bartekqx.config.BaseComponentTest;
import com.bartekqx.user.details.GithubUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceTest extends BaseComponentTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveNewUser() {
        //given
        GithubUser githubUser = getGithubUser();

        //when
        userService.save(githubUser);

        //then
        UserEntity userEntity = userRepository.findByLogin(githubUser.getLogin()).get();

        assertThat(userEntity.getLogin()).isEqualTo(githubUser.getLogin());
        assertThat(userEntity.getRequestCount()).isEqualTo(1L);
    }

    @Test
    public void shouldIncrementRequestCounterOfAlreadyStoredUser() {
        //given
        GithubUser githubUser = getGithubUser();
        userRepository.save(new UserEntity(githubUser.getLogin(),403L));

        //when
        userService.save(githubUser);

        //then
        UserEntity userEntity = userRepository.findByLogin(githubUser.getLogin()).get();

        assertThat(userEntity.getLogin()).isEqualTo(githubUser.getLogin());
        assertThat(userEntity.getRequestCount()).isEqualTo(404L);
    }

    private static GithubUser getGithubUser() {
        return new GithubUser(
                1L,
                "bartekqx",
                "bartek",
                "User",
                "url..",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                10,
                10,
                0.0
        );
    }
}
