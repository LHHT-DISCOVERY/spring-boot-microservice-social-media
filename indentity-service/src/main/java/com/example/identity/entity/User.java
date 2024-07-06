package com.example.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "username", unique = true, columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci")
    // attribute unique is true (not duplicate) , discern upperCase or lowerCase
    // -> (using "columnDefinition = dataType, COLLATE utf8_unicode_ci (if same -> save,to not discern up and lowerCase)
    // ,")
    String username;
    // -> this mean if username = "tri" exists in db -> not save username ="Tri" or username = "TRI")
    String password;

    //    1 user -> many roles -> many to many
    //    Create new table have two columns (1 -> column: pk "id" in entity user , 2 -> column "name" in Entity role)
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;
}
