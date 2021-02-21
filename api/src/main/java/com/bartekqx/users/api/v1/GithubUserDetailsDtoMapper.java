package com.bartekqx.users.api.v1;

import com.bartekqx.user.details.GithubUser;
import org.mapstruct.Mapper;

@Mapper
interface GithubUserDetailsDtoMapper {

    GithubUserDetailsDto fromGithubUser(GithubUser githubUser);

}
