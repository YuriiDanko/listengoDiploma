package com.urilvv.listengo.models.mappers;

import com.urilvv.listengo.dto.UserDto;
import com.urilvv.listengo.dto.UserDto.UserDtoBuilder;
import com.urilvv.listengo.models.User;

public class UserMapper {

    public static UserDto mapToDto(User user) {
        UserDto userDto = UserDtoBuilder.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .playlists(user.getPlaylists())
                .build();

        return userDto;
    }

}
