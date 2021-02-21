package com.bartekqx.user.details;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserStore userStore;
    private final GithubApiClient githubApiClient;

    public Option<GithubUser> getGithubUserDetails(final String login) {
        return githubApiClient.getUserDetails(login)
                .map(this::performCalculations)
                .peek(userStore::save);
    }

    /**
     * Here anotherp possibility was to have 'calculations' field of type #BigDecimal
     * For simplicity I assumed double is enough, and ideal precision is not needed
     */
    private GithubUser performCalculations(GithubUser githubUser) {
        if (githubUser.getFollowers() == 0) {
            return githubUser;
        }

        final double calculations = 6.0 / githubUser.getFollowers() * (2 + githubUser.getPublicRepos());
        return githubUser.withCalculations(Precision.round(calculations, 1));
    }
}
