package rg.jwt.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg.jwt.entity.Member;
import rg.jwt.entity.UserRoles;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public class CustomUserInfoDto extends Member {
	
    private Long memberId;
    
    private String email;
    
    private String name;

    private String password;

    private List<UserRoles> roles;

}

