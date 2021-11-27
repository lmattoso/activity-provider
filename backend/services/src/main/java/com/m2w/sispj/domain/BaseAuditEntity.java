package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(of = { "id" } )
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditEntity implements Serializable {

    public static final String ID = "id";
    public static final String CREATE_DATE = "create_date";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_DATE = "update_date";
    public static final String UPDATE_BY = "update_by";
    public static final String VERSION = "version";

    private static final long serialVersionUID = -149099193374222585L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Column(name = ID)
    private Long id;

    @CreatedDate
    @Column(name = CREATE_DATE)
    private LocalDateTime createDate;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = CREATE_BY)
    private User createBy;

    @LastModifiedDate
    @Column(name = UPDATE_DATE)
    private LocalDateTime updateDate;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = UPDATE_BY)
    private User updateBy;

    @Version
    @Column(name = VERSION)
    private long version;
}