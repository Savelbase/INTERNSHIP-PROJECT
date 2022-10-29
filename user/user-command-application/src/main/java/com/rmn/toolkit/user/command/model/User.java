package com.rmn.toolkit.user.command.model;

import com.rmn.toolkit.user.command.model.iface.Versionable;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Versionable {
    @Id
    @NotNull
    private String id;
    @Type(type = "jsonb")
    private List<Notification> notifications;
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
