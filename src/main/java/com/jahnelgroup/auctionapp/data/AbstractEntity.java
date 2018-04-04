package com.jahnelgroup.auctionapp.data;

import lombok.Data;
import lombok.experimental.var;
import org.springframework.data.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition="TIMESTAMP")
    private ZonedDateTime createdDatetime;

    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition="TIMESTAMP")
    private ZonedDateTime lastModifiedDatetime;

    @Version
    @Column(nullable = false)
    private Long version;

}
