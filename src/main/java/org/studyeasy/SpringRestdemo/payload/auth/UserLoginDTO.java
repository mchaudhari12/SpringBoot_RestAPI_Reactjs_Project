package org.studyeasy.SpringRestdemo.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {
    
    @Email
    @Schema(description = "Email Address", example = "manishdchaudhari@gmail.com",requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Size(min = 6, max =20)
    @Schema(description = "Password",example = "password",requiredMode = RequiredMode.REQUIRED,minLength = 6 , maxLength = 20)
    private String password;
}
