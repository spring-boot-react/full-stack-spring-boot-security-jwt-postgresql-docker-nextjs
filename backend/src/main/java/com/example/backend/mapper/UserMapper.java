package com.example.backend.mapper;

import com.example.backend.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper {

    @Override
    public void createMapper(ModelMapper mm) {
        mm.createTypeMap(User.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setFirstname)).setPostConverter(mappingContext -> {
            User user = mappingContext.getSource();
            UserDTO userDTO = mappingContext.getDestination();
            userDTO.setFirstname(user.getFullName());
            return userDTO;
        });
    }
}
