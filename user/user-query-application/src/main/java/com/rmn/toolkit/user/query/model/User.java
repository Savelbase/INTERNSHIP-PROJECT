package com.rmn.toolkit.user.query.model;

import com.rmn.toolkit.user.query.model.iface.Versionable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Versionable {
    @Id
    @NotNull
    private String id;
    @Type(type = "jsonb")
    private List<Notification> notifications ;
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String securityQuestion;
    @NotNull
    private String securityAnswer;
    private String pinCode;
    @NotNull
    private Integer version;
}
