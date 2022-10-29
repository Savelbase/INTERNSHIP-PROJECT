package com.rmn.toolkit.user.query.dto.response.proections;

import com.rmn.toolkit.user.query.model.Notification;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserDto {
      String securityQuestion, email;
      List<Notification> notifications;
}
