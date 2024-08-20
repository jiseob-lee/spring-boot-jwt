package rg.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg.jwt.util.RoleType;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoles {
	
	@Id
	@Column(name = "roles_idx", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long rolesIdx;
	
	@Column(name = "user_idx", nullable = false)
	private Long userIdx;
	
	@Column(name = "user_role", nullable = false)
	private RoleType userRole;
}
