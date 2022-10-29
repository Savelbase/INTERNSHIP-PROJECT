package com.rmn.toolkit.bankinfoapplication.model;

import com.rmn.toolkit.bankinfoapplication.model.type.ContactType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private String id ;
    private String contact ;
    @Type(type = "jsonb")
    private ContactType type ;
}

