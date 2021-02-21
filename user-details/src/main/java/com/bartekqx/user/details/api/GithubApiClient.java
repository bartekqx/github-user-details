package com.bartekqx.user.details.api;

import com.bartekqx.user.details.model.GithubUser;
import io.vavr.control.Option;

public interface GithubApiClient {

    Option<GithubUser> getUserDetails(String login);
}
