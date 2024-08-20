package rg.jwt.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rg.jwt.util.RoleType;

@Entity
@Table(name = "user")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long memberId;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name_ko", nullable = false)
    private String name;

    @Column(name = "user_password", nullable = false)
    private String password;

    //@Enumerated(EnumType.STRING)
    //@Column(name = "ROLE", nullable = true)
    //private RoleType role;


	@OneToMany
	//@JoinTable(name="user_roles", 
		//joinColumns = @JoinColumn(name="user_idx"),
		//foreignKey = @ForeignKey(name = "user_idx"), 
	    //inverseJoinColumns = @JoinColumn(name = "user_idx", insertable = false, updatable = false),
	    //inverseForeignKey = @ForeignKey(name = "user_idx")
	//)
	@JoinColumn(name="user_idx", insertable = false, updatable = false)
	//@PrimaryKeyJoinColumn
	//@Column(name = "user_role", nullable = false)
	private List<UserRoles> roles;
	
    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    //private List<Category> categories;
    
	
    public List<RoleType> getRole() {
    	List<RoleType> roles = new ArrayList<>();
    	if (this.roles == null || this.roles.size() == 0) {
    		return null;
    	}
    	for (int i=0; i < this.roles.size(); i++) {
    		UserRoles userRoles = this.roles.get(i);
    		roles.add(userRoles.getUserRole());
    	}
    	return roles;
    }
    
}

