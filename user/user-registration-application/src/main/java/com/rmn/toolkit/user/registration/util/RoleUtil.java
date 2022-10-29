package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.exception.notfound.RoleNotFoundException;
import com.rmn.toolkit.user.registration.model.Role;
import com.rmn.toolkit.user.registration.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleUtil {
    private final RoleRepository roleRepository;

    public Role findRoleById(String roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role with id='{}' not found", roleId);
                    throw new RoleNotFoundException(roleId);
                });
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("Role with name='{}' not found", name);
                    throw new RoleNotFoundException(name, true);
                });
    }
}