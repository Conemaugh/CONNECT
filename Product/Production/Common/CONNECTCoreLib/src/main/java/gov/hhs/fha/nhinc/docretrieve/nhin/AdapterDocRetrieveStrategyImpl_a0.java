/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.docretrieve.nhin;

import gov.hhs.fha.nhinc.auditrepository.AuditRepositoryLogger;
import gov.hhs.fha.nhinc.auditrepository.nhinc.proxy.AuditRepositoryProxy;
import gov.hhs.fha.nhinc.auditrepository.nhinc.proxy.AuditRepositoryProxyObjectFactory;
import gov.hhs.fha.nhinc.common.auditlog.LogEventRequestType;
import gov.hhs.fha.nhinc.common.nhinccommon.AcknowledgementType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.docretrieve.adapter.proxy.AdapterDocRetrieveProxy;
import gov.hhs.fha.nhinc.docretrieve.adapter.proxy.AdapterDocRetrieveProxyObjectFactory;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.util.HomeCommunityMap;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mweaver
 */
public class AdapterDocRetrieveStrategyImpl_a0 implements AdapterDocRetrieveStrategy {

    private static Log log = LogFactory.getLog(AdapterDocRetrieveStrategyImpl_a0.class);
    
    public AdapterDocRetrieveStrategyImpl_a0() {
        
    }

    private Log getLogger()
    {
        return log;
    }

    public void execute(NhinDocRetrieveOrchestratable message) {
        getLogger().debug("Begin NhinDocRetrieveOrchestratableImpl_g0.process");
        if (message == null) {
            getLogger().debug("NhinOrchestratable was null");
            return;
        }
        
        if (message instanceof NhinDocRetrieveOrchestratableImpl_g0) {
            NhinDocRetrieveOrchestratableImpl_g0 NhinDRMessage = (NhinDocRetrieveOrchestratableImpl_g0) message;
            String requestCommunityID = HomeCommunityMap.getCommunityIdForRDRequest(NhinDRMessage.getRequest());

            getLogger().debug("Calling audit log for doc retrieve request (g0) sent to adapter (a0)");
            auditRequestMessage(NhinDRMessage.getRequest(), NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION, NhincConstants.AUDIT_LOG_ADAPTER_INTERFACE,
                    NhinDRMessage.getAssertion(), requestCommunityID);

            getLogger().debug("Creating adapter (a0) doc retrieve proxy");
            AdapterDocRetrieveProxy proxy = new AdapterDocRetrieveProxyObjectFactory().getAdapterDocRetrieveProxy();
            getLogger().debug("Sending adapter doc retrieve to adapter (a0)");
            NhinDRMessage.setResponse(proxy.retrieveDocumentSet(NhinDRMessage.getRequest(), NhinDRMessage.getAssertion()));

            getLogger().debug("Calling audit log for doc retrieve response received from adapter (a0)");
            auditResponseMessage(NhinDRMessage.getResponse(), NhincConstants.AUDIT_LOG_INBOUND_DIRECTION, NhincConstants.AUDIT_LOG_ADAPTER_INTERFACE,
                    NhinDRMessage.getAssertion(), requestCommunityID);
        } else {
            getLogger().error("NhinDocRetrieve_g0AdapterDelegateImpl_a0.process recieved a message which was not of type NhinDocRetrieveOrchestratableImpl_g0.");
        }
        getLogger().debug("End NhinDocRetrieveOrchestratableImpl_g0.process");
    }

    private void auditRequestMessage(RetrieveDocumentSetRequestType request, String direction, String connectInterface, AssertionType assertion, String requestCommunityID) {
        gov.hhs.fha.nhinc.common.auditlog.DocRetrieveMessageType message = new gov.hhs.fha.nhinc.common.auditlog.DocRetrieveMessageType();
        message.setRetrieveDocumentSetRequest(request);
        message.setAssertion(assertion);
        AuditRepositoryLogger auditLogger = new AuditRepositoryLogger();
        LogEventRequestType auditLogMsg = auditLogger.logDocRetrieve(message, direction, connectInterface, requestCommunityID);
        if (auditLogMsg != null) {
            auditMessage(auditLogMsg, assertion);
        }
    }

    private void auditResponseMessage(RetrieveDocumentSetResponseType response, String direction, String connectInterface, AssertionType assertion, String requestCommunityID) {
        gov.hhs.fha.nhinc.common.auditlog.DocRetrieveResponseMessageType message = new gov.hhs.fha.nhinc.common.auditlog.DocRetrieveResponseMessageType();
        message.setRetrieveDocumentSetResponse(response);
        message.setAssertion(assertion);
        AuditRepositoryLogger auditLogger = new AuditRepositoryLogger();
        LogEventRequestType auditLogMsg = auditLogger.logDocRetrieveResult(message, direction, connectInterface, requestCommunityID);
        if (auditLogMsg != null) {
            auditMessage(auditLogMsg, assertion);
        }
    }

    private AcknowledgementType auditMessage(LogEventRequestType auditLogMsg, AssertionType assertion) {
        AuditRepositoryProxyObjectFactory auditRepoFactory = new AuditRepositoryProxyObjectFactory();
        AuditRepositoryProxy proxy = auditRepoFactory.getAuditRepositoryProxy();
        return proxy.auditLog(auditLogMsg, assertion);
    }
}
