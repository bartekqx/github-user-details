package com.bartekqx.user.details;

import com.bartekqx.user.details.model.GithubUser;
import io.vavr.control.Option;

public interface UserStore {

    void save(GithubUser githubUser);
}
