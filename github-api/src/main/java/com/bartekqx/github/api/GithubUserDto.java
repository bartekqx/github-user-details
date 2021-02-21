package com.bartekqx.github.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GithubUserDto implements Serializable {
    private long id;
    private String login;
    private String name;
    private String type;
    @JsonProperty(value = "avatar_url")
    private String avatarUrl;
    @JsonProperty(value = "created_at")
    private String createdAt;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    @JsonProperty(value = "public_repos")
    private long publicRepos;
    private long followers;
}
