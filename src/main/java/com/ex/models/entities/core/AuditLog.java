package com.ex.models.entities.core;

import com.ex.common.configuration.RevisionListenerConfig;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_logs")
@RevisionEntity(RevisionListenerConfig.class)
public class AuditLog extends DefaultRevisionEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "ip_address")
    private String ipAddress;

}
