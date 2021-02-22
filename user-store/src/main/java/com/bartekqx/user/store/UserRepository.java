package com.bartekqx.user.store;

import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Option<UserEntity> findByLogin(String login);

}
