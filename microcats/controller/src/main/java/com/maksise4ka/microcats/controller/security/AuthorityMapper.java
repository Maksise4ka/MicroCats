package com.maksise4ka.microcats.controller.security;

import com.maksise4ka.microcats.dao.entities.authorize.Permission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorityMapper {
    public static Set<SimpleGrantedAuthority> getAuthorities(Collection<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
