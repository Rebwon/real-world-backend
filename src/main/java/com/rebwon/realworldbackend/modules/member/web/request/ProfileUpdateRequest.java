package com.rebwon.realworldbackend.modules.member.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.modules.member.application.command.ProfileUpdateCommand;
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

  public ProfileUpdateCommand toCommand() {
    return new ProfileUpdateCommand(this.username, this.email, this.password, this.bio, this.image);
  }
}
