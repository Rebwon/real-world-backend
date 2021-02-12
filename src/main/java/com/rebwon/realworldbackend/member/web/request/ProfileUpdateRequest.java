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
public class ProfileUpdateRequest {
  @NotBlank(message = "can't be empty username")
  private String username;
  @Email(message = "should be an email")
  @NotBlank(message = "can't be empty email")
  private String email;
  @NotBlank(message = "can't be empty password")
  @Length(min = 8, max = 50)
  private String password;
  private String bio;
  private String image;
}
