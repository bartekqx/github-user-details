package com.bartekqx.user.store;

import com.bartekqx.user.details.UserStore;
import com.bartekqx.user.details.model.GithubUser;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService implements UserStore {

    private final UserRepository userRepository;

    @Override
    public void save(final GithubUser githubUser) {
        final UserEntity entity = Option.ofOptional(userRepository.findByLogin(githubUser.getLogin()))
                .map(this::updateCounter)
                .getOrElse(() -> new UserEntity(githubUser.getLogin(), 1L));
        userRepository.save(entity);
    }

    private UserEntity updateCounter(UserEntity entity) {
        return new UserEntity(entity.getLogin(), entity.getRequestCount() + 1);
    }
}
