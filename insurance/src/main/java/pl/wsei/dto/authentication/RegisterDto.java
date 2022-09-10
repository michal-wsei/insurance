package pl.wsei.dto.authentication;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import pl.wsei.dto.Dto;
import pl.wsei.model.authorization.AuthorityType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Maps the Register payload to User class
 */
@Getter
public class RegisterDto extends Dto {

    @NotNull
    @Length(max = 32, min = 4)
    private String name;

    @Email
    private String email;

    @NotNull
    @Length(max = 16, min = 8)
    private String password;

    @NotNull
    private AuthorityType authority;
}
