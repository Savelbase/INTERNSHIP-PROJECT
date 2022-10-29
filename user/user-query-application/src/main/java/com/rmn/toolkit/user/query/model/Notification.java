package com.rmn.toolkit.user.query.model;

import com.rmn.toolkit.user.query.model.type.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean state;
}
