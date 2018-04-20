package com.jahnelgroup.auctionapp.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    private final static String[] DEFAULT_IGNORE_PROPS =
            new String[]{"id", "createdBy", "createdDatetime", "lastModifiedBy", "lastModifiedDatetime", "version"};

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

    public <T> T copyFields(T source, String... ignoreProperties){
        BeanUtils.copyProperties(source, this, concatenate(DEFAULT_IGNORE_PROPS, ignoreProperties));
        return (T)this;
    }

    private <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

}
