package com.rebwon.realworldbackend.modules.member.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.modules.member.application.command.LoginCommand;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@JsonRootName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "can't be empty email")
    @Email(message = "should be an email")
    private String email;
    @NotBlank(message = "can't be empty password")
    @Length(min = 8, max = 50)
    private String password;

    public LoginCommand toCommand() {
        return new LoginCommand(this.email, this.password);
    }
}

