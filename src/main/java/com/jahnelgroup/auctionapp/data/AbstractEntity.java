package com.jahnelgroup.auctionapp.data;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition="TIMESTAMP")
    protected ZonedDateTime createdDatetime;

    @LastModifiedBy
    @Column(nullable = false)
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition="TIMESTAMP")
    protected ZonedDateTime lastModifiedDatetime;

    @Version
    @Column(nullable = false)
    protected Long version;

}
