package pl.wsei.dto.authentication;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import pl.wsei.dto.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Maps the login payload to User class
 */
@Getter
public class LoginDto extends Dto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(max = 16, min = 8)
    private String password;
}
