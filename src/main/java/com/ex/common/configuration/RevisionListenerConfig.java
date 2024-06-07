package com.ex.common.configuration;

import com.ex.models.entities.core.AuditLog;
import com.ex.security.utils.AuditInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class RevisionListenerConfig implements RevisionListener {

    private final HttpServletRequest request;

    @Override
    public void newRevision(Object revisionEntity) {

        AuditLog auditLog = (AuditLog) revisionEntity;
        try {
            String username = AuditInfo.getUsername();
            auditLog.setUsername(username != null ? username : "anonymousUser");
            auditLog.setIpAddress(request.getRemoteAddr());
        }
        catch (Exception e){
            System.err.println("Error in RevisionListenerConfig.newRevision:"+e);
        }
    }
}
