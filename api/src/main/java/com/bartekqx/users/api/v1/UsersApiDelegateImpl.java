package com.bartekqx.users.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

    @Override
    public ResponseEntity<GithubUserDetailsDto> getGithubUserDetails(String login) {
        return null;
    }
}
