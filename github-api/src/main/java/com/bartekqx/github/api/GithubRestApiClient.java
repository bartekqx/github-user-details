package com.bartekqx.github.api;

import com.bartekqx.user.details.GithubApiClient;
import com.bartekqx.user.details.GithubUser;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
class GithubRestApiClient implements GithubApiClient {

    private final RestTemplate restTemplate;

    @Value("${github.user.url}")
    private String getUserUrl;

    @Override
    public Option<GithubUser> getUserDetails(final String login) {
        return Try.of(() -> Option.of(restTemplate.getForObject(getUserUrl, GithubUserDto.class, login))
                .map(this::map))
                .getOrElse(Option::none);
    }

    private GithubUser map(final GithubUserDto response) {
        return new GithubUser(
                response.getId(),
                response.getLogin(),
                response.getName(),
                response.getType(),
                response.getAvatarUrl(),
                response.getCreatedAt(),
                response.getUpdatedAt(),
                response.getFollowers(),
                response.getPublicRepos(),
                0.0
        );
    }
}
