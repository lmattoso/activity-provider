package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@FieldNameConstants
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Profile.TABLE)
public class Profile implements GrantedAuthority {
    public static final String TABLE = "sispj_profile";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String NAME = "name";

    @Id
    @Column(name = Profile.ID)
    private Long id;

    @Column(name = Profile.CODE)
    private String code;

    @Column(name = Profile.NAME)
    private String name;

    @Override
    public String getAuthority() {
        return this.code;
    }
}
