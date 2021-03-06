package com.bartekqx;

import com.bartekqx.config.BaseIntegrationTest;
import com.bartekqx.config.PostgresDbCleaner;
import com.bartekqx.user.store.UserEntity;
import com.bartekqx.user.store.UserRepository;
import com.bartekqx.users.api.v1.GithubUserDetailsDto;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(PostgresDbCleaner.class)
public class GithubUserDetailsTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetGithubUserDetailsAndOk200() throws Exception {
        //given
        String login = "bartekqx";

        //when
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/github-user-details/api/v1/users/{login}", login))
                .andExpect(status().isOk())
                .andReturn();

        //then
        GithubUserDetailsDto response =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), GithubUserDetailsDto.class);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getLogin()).isEqualTo(login);
        assertThat(response.getAvatarUrl()).isNotNull();
        assertThat(response.getCreatedAt()).isNotNull();

        UserEntity userEntity = userRepository.findByLogin(login).get();

        assertThat(userEntity.getLogin()).isEqualTo(login);
        assertThat(userEntity.getRequestCount()).isEqualTo(1L);
    }

    @Test
    public void shouldGetNotFound404() throws Exception {
        //given
        String login = UUID.randomUUID().toString();

        //when
        mockMvc.perform(get("http://localhost:8080/github-user-details/api/v1/users/{login}", login))
                .andExpect(status().isNotFound())
                .andReturn();

        //then
        Option<UserEntity> userEntity = userRepository.findByLogin(login);

        assertThat(userEntity.isDefined()).isFalse();
    }
}
