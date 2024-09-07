package rg.jwt.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rg.jwt.dto.CustomUserInfoDto;
import rg.jwt.entity.UserRoles;
import rg.jwt.util.RoleType;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5429094940700037908L;
	
	private final CustomUserInfoDto member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    	List<String> roles = new ArrayList<>();
        
    	List<UserRoles> role = member.getRoles();
        
        if (role == null || role.size() == 0) {
        	return null;
        }
        
        for (int i=0; i < role.size(); i++) {
        	UserRoles userRoles = role.get(i);
    		//roles.add(userRoles.getUserRole());
        	//RoleType roleType = role.get(i);
        	RoleType roleType = userRoles.getUserRole();
        	roles.add(roleType.toString());
        	//roles.add("ROLE_" + RoleType.[userRoles.getUserRole()].toString());
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId().toString();
    }

    public String getUserId() {
        return member.getUserId().toString();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

