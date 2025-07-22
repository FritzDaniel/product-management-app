package com.api.product.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Nullable
    @Column(name = "created_by")
    private Long createdBy;

    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_by")
    private Long updatedBy;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Nullable
    @Column(name = "deleted_by")
    private Long deletedBy;

    @Nullable
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Nullable
    @Column(name = "status")
    private String status;
}
