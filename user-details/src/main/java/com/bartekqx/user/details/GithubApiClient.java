package com.bartekqx.user.details;

import io.vavr.control.Option;

public interface GithubApiClient {

    Option<GithubUser> getUserDetails(String login);
}
