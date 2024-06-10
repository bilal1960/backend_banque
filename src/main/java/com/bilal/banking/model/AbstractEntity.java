package com.bilal.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate
    @Column(
            name = "createdDate",
            nullable = false,
            updatable = false
    )
    private LocalDateTime creationDate;

    @LastModifiedBy
    @Column(
            name = "LastModifiedDate"
    )
    private LocalDateTime lastModifiedDate;

}
