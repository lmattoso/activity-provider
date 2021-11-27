package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = User.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = User.SEQUENCE, allocationSize = 1)
public class User extends BaseEntity implements UserDetails {

	public static final String TABLE = "sispj_user";
	public static final String SEQUENCE = "user_seq";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String PROFILE_ID = "profile_id";
	public static final String CHANGE_PASSWORD = "changePassword";
	public static final String DELETED = "deleted";

	@Column(name = NAME)
	private String name;

	@Column(name = EMAIL)
	private String email;

	@Column(name = PASSWORD)
	private String password;

	@ManyToOne
	@JoinColumn(name = PROFILE_ID)
	private Profile profile;

	@Column(name = CHANGE_PASSWORD)
	private boolean changePassword;

	@Column(name = DELETED)
	private boolean deleted;

	@Override
	public String getUsername() {
		return this.email;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(profile);
	}

	public boolean isAdmin() {
		return "ADMIN".equals(this.profile.getCode());
	}
}