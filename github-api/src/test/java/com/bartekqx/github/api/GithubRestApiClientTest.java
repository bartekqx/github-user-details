package com.bartekqx.github.api;

import com.bartekqx.user.details.model.GithubUser;
import io.vavr.control.Option;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Tag("unit")
public class GithubRestApiClientTest {

    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private GithubRestApiClient client = new GithubRestApiClient(restTemplate);

    @Test
    public void shouldGetGithubUser() {
        //given
        GithubUserDto response = getGithubUserDto();

        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any(), Mockito.any(String.class)))
                .thenReturn(response);

        //when
        Option<GithubUser> optionUser = client.getUserDetails(response.getLogin());

        //then
        GithubUser user = optionUser.get();

        assertThat(user.getLogin()).isEqualTo(response.getLogin());
        assertThat(user.getAvatarUrl()).isEqualTo(response.getAvatarUrl());
        assertThat(user.getCreatedAt()).isEqualTo(response.getCreatedAt());
        assertThat(user.getUpdatedAt()).isEqualTo(response.getUpdatedAt());
        assertThat(user.getName()).isEqualTo(response.getName());
        assertThat(user.getId()).isEqualTo(response.getId());
        assertThat(user.getType()).isEqualTo(response.getType());
    }

    @Test
    public void shouldReturnOptionNoneIfExceptionOccurs() {
        //given
        GithubUserDto response = getGithubUserDto();

        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any(), Mockito.any(String.class)))
                .thenThrow(new RestClientException("Exception"));

        //when
        Option<GithubUser> optionUser = client.getUserDetails(response.getLogin());

        //then
        assertThat(optionUser.isDefined());
    }

    private static GithubUserDto getGithubUserDto() {
        return new GithubUserDto(
                1L,
                "bartekqx",
                "bartek",
                "User",
                "url..",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );
    }
}
