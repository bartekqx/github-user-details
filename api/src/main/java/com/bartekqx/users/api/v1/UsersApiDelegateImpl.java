package com.bartekqx.users.api.v1;

import com.bartekqx.user.details.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private final UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<GithubUserDetailsDto> getGithubUserDetails(final String login) {
        return userDetailsService.getGithubUserDetails(login)
                .map(v -> {
                    final GithubUserDetailsDto dto = new GithubUserDetailsDto();
                    dto.setId(v.getId());
                    dto.setLogin(v.getLogin());
                    dto.setName(v.getName());
                    dto.setAvatarUrl(v.getAvatarUrl());
                    dto.setCalculations(v.getCalculations());
                    dto.setCreatedAt(v.getCreatedAt());
                    dto.setUpdatedAt(v.getUpdatedAt());
                    return ResponseEntity.ok(dto);
                })
                .getOrElse(() -> ResponseEntity.notFound().build());
    }
}
