package com.rmn.toolkit.user.registration.model;

import com.rmn.toolkit.user.registration.security.AuthorityType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @Column(columnDefinition = "string-array")
    @Type(type = "string-array")
    @Enumerated(EnumType.STRING)
    private AuthorityType[] authorities;

    @NotNull
    private Integer version;
}
