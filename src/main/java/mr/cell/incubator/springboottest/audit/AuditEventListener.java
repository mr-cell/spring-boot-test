package mr.cell.incubator.springboottest.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuditEventListener {
	
	private ApplicationEventPublisher eventPublisher;
	
	public AuditEventListener(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@EventListener(condition = "#event.auditEvent.type != 'CUSTOM_AUDIT_EVENT'")
	public void onAuditEvent(AuditApplicationEvent event) {
		AuditEvent auditEvent = event.getAuditEvent();
		log.info("On audit application event: timestamp: {}, principal: {}, type: {}, data: {}",
				auditEvent.getTimestamp(),
				auditEvent.getPrincipal(),
				auditEvent.getType(),
				auditEvent.getData());
		
		eventPublisher.publishEvent(new AuditApplicationEvent(auditEvent.getPrincipal(), "CUSTOM_AUDIT_EVENT"));
	}
	
	@EventListener(condition = "#event.auditEvent.type == 'CUSTOM_AUDIT_EVENT'")
	public void onCustomAuditEvent(AuditApplicationEvent event) {
		log.info("Custom audit event - {}", event.getAuditEvent());
	}

}
