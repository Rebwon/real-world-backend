package com.rebwon.realworldbackend.member.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
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
public class RegisterRequest {

  @NotBlank(message = "can't be empty username")
  private String username;
  @NotBlank(message = "can't be empty email")
  @Email(message = "should be an email")
  private String email;
  @NotBlank(message = "can't be empty password")
  @Length(min = 8, max = 50)
  private String password;
}
