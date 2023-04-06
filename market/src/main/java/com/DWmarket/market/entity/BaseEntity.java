package com.DWmarket.market.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @CreatedBy
    @Column(updatable = false)
    private String createBy; // 등록자

    @LastModifiedBy
    private String modifiedBy; // 수정자
}
