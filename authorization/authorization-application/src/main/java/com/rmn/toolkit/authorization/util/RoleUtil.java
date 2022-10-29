package com.rmn.toolkit.authorization.util;

import com.rmn.toolkit.authorization.exception.notfound.RoleNotFoundException;
import com.rmn.toolkit.authorization.model.Role;
import com.rmn.toolkit.authorization.repository.RoleRepository;
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
}
