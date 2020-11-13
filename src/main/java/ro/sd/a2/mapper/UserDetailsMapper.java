package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import ro.sd.a2.dto.UserDetailsDto;
import ro.sd.a2.entity.User;

public class UserDetailsMapper {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsMapper.class);
    public static UserDetailsDto convertToDto(User user)
    {
        log.info("Map from User to UserDetailsDto requested.");
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setAddress(user.getAddress());
        userDetailsDto.setDateOfBirth(user.getBirthDate());
        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        userDetailsDto.setUsername(user.getUsername());
        userDetailsDto.setRegistrationDate(user.getRegistrationDate());
        return userDetailsDto;
    }
}
