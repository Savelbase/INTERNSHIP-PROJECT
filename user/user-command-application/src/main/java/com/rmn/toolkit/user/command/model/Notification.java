package com.rmn.toolkit.user.command.model;

import com.rmn.toolkit.user.command.model.type.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    private NotificationType type;
    private boolean state;
}
