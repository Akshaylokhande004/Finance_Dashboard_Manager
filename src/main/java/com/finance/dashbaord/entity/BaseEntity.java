package com.finance.dashbaord.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID id;

     @Column(name="created_at" ,nullable =false, updatable= false)
     private LocalDateTime createdAt;

     @Column(name = "updated_at")
     private LocalDateTime updatedAt;

     @PrePersist
    protected void onCreate()
     {
         this.createdAt = LocalDateTime.now();

     }

     @PreUpdate
     protected void onUpdate()
     {
         this.updatedAt = LocalDateTime.now();
     }


}
