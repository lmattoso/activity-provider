package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(of = { "id" } )
public class BaseEntity implements Serializable {

    public static final String ID = "id";
    public static final String VERSION = "version";

    private static final long serialVersionUID = -149099193374222585L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Column(name = ID)
    private Long id;

    @Version
    @Column(name = VERSION)
    private long version;
}