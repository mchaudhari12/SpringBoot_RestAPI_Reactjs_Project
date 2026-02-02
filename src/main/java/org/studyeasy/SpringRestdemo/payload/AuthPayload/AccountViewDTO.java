package org.studyeasy.SpringRestdemo.payload.AuthPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountViewDTO {
    
    private long id;

    private String email;

    private String authorities;
}
