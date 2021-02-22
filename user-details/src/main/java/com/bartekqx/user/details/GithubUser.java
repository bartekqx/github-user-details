package com.bartekqx.user.details;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;


@Getter
@With
@RequiredArgsConstructor
public class GithubUser {
    private final long id;
    private final String login;
    private final String name;
    private final String type;
    private final String avatarUrl;
    private final String createdAt;
    private final String updatedAt;
    private final long followers;
    private final long publicRepos;
    private final double calculations;
}
