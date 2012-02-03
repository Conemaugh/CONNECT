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
package gov.hhs.fha.nhinc.auditquery.entity;

import com.services.nhinc.schema.auditmessage.FindAuditEventsResponseType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.FindAuditEventsRequestType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.entityauditlogquerysaml.EntityAuditLogQuerySamlPortType;
import gov.hhs.fha.nhinc.entityauditlogquerysaml.EntityAuditLogQuerySamlService;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;
import java.util.Map;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Jon Hoppesch
 */
public class EntityAuditQueryImpl {

    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(EntityAuditQueryImpl.class);
    private static EntityAuditLogQuerySamlService service = new EntityAuditLogQuerySamlService();

    public FindAuditEventsResponseType findAuditEvents(FindAuditEventsRequestType findAuditEventsRequest) {
        FindAuditEventsResponseType response = new FindAuditEventsResponseType();

        try
        {
            String url = ConnectionManagerCache.getInstance().getLocalEndpointURLByServiceName(NhincConstants.ENTITY_AUDIT_QUERY_SECURED_SERVICE_NAME);

            EntityAuditLogQuerySamlPortType port = getPort(url);

            AssertionType assertIn = findAuditEventsRequest.getAssertion();
            SamlTokenCreator tokenCreator = new SamlTokenCreator();
            Map requestContext = tokenCreator.CreateRequestContext(assertIn, url, NhincConstants.AUDIT_QUERY_ACTION);
            ((BindingProvider) port).getRequestContext().putAll(requestContext);

            gov.hhs.fha.nhinc.common.nhinccommonentity.FindAuditEventsSecuredRequestType body = new gov.hhs.fha.nhinc.common.nhinccommonentity.FindAuditEventsSecuredRequestType();
            body.setFindAuditEvents(findAuditEventsRequest.getFindAuditEvents());
            body.setNhinTargetCommunities(findAuditEventsRequest.getNhinTargetCommunities());
            response = port.findAuditEvents(body);
        }
        catch (Exception ex)
        {
            log.error("Failed to send entity audit query from proxy EJB to secure interface: " + ex.getMessage(), ex);
        }

        return response;
    }

    private EntityAuditLogQuerySamlPortType getPort(String url) {
        EntityAuditLogQuerySamlPortType port = service.getEntityAuditLogQuerySamlPortTypeBindingPort();

        log.info("Setting endpoint address to Entity Audit Query Secured Service to " + url);
        ((BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        return port;
    }
}
