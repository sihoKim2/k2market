package com.DWmarket.market.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@MappedSuperclass

public class BaseTimeEntity {
    @CreatedDate // 엔티티가 생성되어 저장될때 시간을 자동으로 저장
    @Column(updatable = false)
    private LocalDateTime regTime;
    //column -> updatable false : update 쿼리 사용할때는 데이터 변경을 막음
    //          insertable false : insert 쿼리 할때 막음

    @LastModifiedDate // 엔티티의 값을 변경할때 시간을 자동으로 저장
    private LocalDateTime updateTime;

}
