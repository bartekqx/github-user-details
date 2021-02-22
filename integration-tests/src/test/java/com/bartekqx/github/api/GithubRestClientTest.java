package com.bartekqx.github.api;

import com.bartekqx.config.BaseIntegrationTest;
import com.bartekqx.user.details.GithubUser;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GithubRestClientTest extends BaseIntegrationTest {

    @Autowired
    private GithubRestApiClient githubRestApiClient;

    @Test
    public void shouldGetUserDetails() {
        //given
        String login = "bartekqx";

        //when
        Option<GithubUser> optionUser = githubRestApiClient.getUserDetails(login);

        //then
        GithubUser dto = optionUser.get();
        assertThat(dto.getLogin()).isEqualTo(login);
        assertThat(dto.getAvatarUrl()).isNotNull();
        assertThat(dto.getCreatedAt()).isNotNull();
        assertThat(dto.getId()).isNotNull();
        assertThat(dto.getType()).isNotNull();
    }
}
