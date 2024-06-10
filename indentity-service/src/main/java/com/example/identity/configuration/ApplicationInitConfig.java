package com.example.identity.configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.identity.entity.Permission;
import com.example.identity.entity.Role;
import com.example.identity.entity.User;
import com.example.identity.repository.PermissionRepository;
import com.example.identity.repository.RoleRepository;
import com.example.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    // DI in @Bean in SecurityConfig class
    PasswordEncoder passwordEncoder;

    @Bean
    // this Bean only init  if value : spring.datasource.driverClassName = com.mysql.cj.jdbc.Driver, this mean in .Yml
    // else H2 DB to test -> not init this bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(
            UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        log.info("init application");
        Permission perApprove = Permission.builder()
                .name("APPROVE_POST")
                .description("approve data from user")
                .build();
        Permission perReject = Permission.builder()
                .name("REJECT_POST")
                .description("reject data from user")
                .build();
        Permission perUpdate = Permission.builder()
                .name("UPDATE_POST")
                .description("update data from user")
                .build();
        Permission perCreate = Permission.builder()
                .name("CREATE_POST")
                .description("create data from user")
                .build();
        List<Permission> AllPermissions = List.of(perCreate, perApprove, perReject, perUpdate);
        //        List<String> idPermission = permissionsSave.stream().map(Permission::getName).toList();
        permissionRepository.saveAll(AllPermissions);
        List<Permission> permissionsUser = List.of(perUpdate, perCreate);
        // spotless:off
        Role roleAdmin = Role.builder()
                .name("ADMIN")
                .description("role admin")
                .permissions(new HashSet<>(AllPermissions))
                .build();
        Role roleUser = Role.builder()
                .name("USER")
                .description("role user")
                .permissions(new HashSet<>(permissionsUser))
                .build();
        //spotless:on
        List<Role> saveRoles = List.of(roleAdmin, roleUser);
        roleRepository.saveAll(saveRoles);
        Set<Role> roles = Set.of(roleAdmin);
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    }
}
