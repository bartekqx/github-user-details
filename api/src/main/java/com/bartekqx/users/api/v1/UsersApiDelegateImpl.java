package com.bartekqx.users.api.v1;

import com.bartekqx.user.details.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UsersApiDelegateImpl implements UsersApiDelegate {

    private final UserDetailsService userDetailsService;
    private final GithubUserDetailsDtoMapper mapper;

    @Override
    public ResponseEntity<GithubUserDetailsDto> getGithubUserDetails(final String login) {
        return userDetailsService.getGithubUserDetails(login)
                .map(mapper::fromGithubUser)
                .map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.notFound().build());
    }
}
