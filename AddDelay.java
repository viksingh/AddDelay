package com.saki.module;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;

import com.sap.aii.af.lib.mp.module.ModuleContext;
import com.sap.aii.af.lib.mp.module.ModuleData;
import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.Message;
import com.sap.engine.interfaces.messaging.api.MessageDirection;
import com.sap.engine.interfaces.messaging.api.MessageKey;
import com.sap.engine.interfaces.messaging.api.PublicAPIAccessFactory;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditAccess;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;

/**
 * Session Bean implementation class AddDelay
 */
@Stateless
public class AddDelay implements AddDelayRemote, AddDelayLocal {

    /**
     * Default constructor. 
     */
    public AddDelay() {
        // TODO Auto-generated constructor stub
    }
    
    
    public ModuleData process(ModuleContext moduleContext, ModuleData inputModuleData) throws ModuleException {
    	
    	
		AuditAccess audit = null;
		// Create the location always new to avoid serialization/transient of location
		try {
		} catch (Exception t) {
			t.printStackTrace();
			ModuleException me = new ModuleException("Unable to create trace location", t);
			throw me;
		}
		Object obj = null;
		Message msg = null;
		MessageKey key = null;
		try {
			obj = inputModuleData.getPrincipalData();
			msg = (Message) obj;
			if (msg.getMessageDirection().equals(MessageDirection.OUTBOUND))
				key = new MessageKey(msg.getMessageId(), MessageDirection.OUTBOUND);
			else
				key = new MessageKey(msg.getMessageId(), MessageDirection.INBOUND);
			audit = PublicAPIAccessFactory.getPublicAPIAccess().getAuditAccess();
			audit.addAuditLogEntry(key, AuditLogStatus.SUCCESS, " AddDelay: Module called");

			String delay = null;
			delay = (String) moduleContext.getContextData("delay");

			audit.addAuditLogEntry(key, AuditLogStatus.SUCCESS, " AddDelay: delay "+delay + " ms");
			audit.addAuditLogEntry(key, AuditLogStatus.SUCCESS, " AddDelay: Going to sleep  ");
			
			TimeUnit.MILLISECONDS.sleep(Long.parseLong(delay));
			
			audit.addAuditLogEntry(key, AuditLogStatus.SUCCESS, " AddDelay: Back from sleep  ");
			
		}
			
			catch (Exception e) {
				ModuleException me = new ModuleException(e);
//				throw me;
			}    	
    	
    	
		return inputModuleData;
    	
    	
    

}
    
}
