package com.example.identity.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    String name;

    String description;

    //    1 role -> many permission -> many to many
    //    create new table include two column is two primary key (pk "name" in entity role, and pk "name" in entity
    // Permission)
    @ManyToMany(
            fetch =
                    FetchType
                            .EAGER) // declare that we want to load Set<Permission> together Role when get all list Role
    Set<Permission> permissions;
}
