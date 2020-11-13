package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.dto.UserRegisterDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.utils.builder.UserBuilder;

public class UserRegisterMapper {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterMapper.class);
    public static User convertToEntity(UserRegisterDto userRegisterDto)
    {
        log.info("Map from UserRegisterDto to User requested.");
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.addUserLoginDetails(userRegisterDto.getEmail(), userRegisterDto.getPassword(), userRegisterDto.getUsername());
        userBuilder.addAddressAndBirthDate(userRegisterDto.getAddress(), userRegisterDto.getDateOfBirth());
        userBuilder.addUserNames(userRegisterDto.getFirstName(), userRegisterDto.getLastName());
        return userBuilder.getResult();
    }
}
