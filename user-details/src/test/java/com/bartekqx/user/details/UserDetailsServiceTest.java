package com.bartekqx.user.details;

import io.vavr.control.Option;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("unit")
public class UserDetailsServiceTest {

    private final UserStore userStore = Mockito.mock(UserStore.class);
    private final GithubApiClient githubApiClient = Mockito.mock(GithubApiClient.class);

    private final UserDetailsService userDetailsService = new UserDetailsService(userStore, githubApiClient);

    @Test
    public void shouldPerformCalculationsOnUserData() {
        //given
        GithubUser response = getGithubUser();

        Mockito.when(githubApiClient.getUserDetails(Mockito.anyString()))
                .thenReturn(Option.of(response));

        String login = "bartekqx";

        //when
        GithubUser githubUser = userDetailsService.getGithubUserDetails(login).get();

        //then
        assertThat(githubUser.getCalculations()).isEqualTo(40.4);
    }

    @Test
    public void shouldCalculateZero() {
        //given
        GithubUser response = getGithubUserWithZeroFollowers();

        Mockito.when(githubApiClient.getUserDetails(Mockito.anyString()))
                .thenReturn(Option.of(response));

        String login = "bartekqx";

        //when
        GithubUser githubUser = userDetailsService.getGithubUserDetails(login).get();

        //then
        assertThat(githubUser.getCalculations()).isEqualTo(0.0);
    }

    private GithubUser getGithubUserWithZeroFollowers() {
        return new GithubUser(
                1L,
                "bartekqx",
                "bartek",
                "User",
                "url..",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                0,
                99,
                0L
        );
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
                15,
                99,
                0L
        );
    }
}
