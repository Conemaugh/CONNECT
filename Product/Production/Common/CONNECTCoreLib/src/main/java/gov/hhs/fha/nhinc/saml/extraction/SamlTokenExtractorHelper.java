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
package gov.hhs.fha.nhinc.saml.extraction;

import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import org.uddi.api_v3.BusinessEntity;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.properties.PropertyAccessException;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jon Hoppesch
 */
public class SamlTokenExtractorHelper {

    private static Log log = LogFactory.getLog(SamlTokenExtractorHelper.class);
    public static final String INTERNAL_SUBJECT_DISCOVERY = "nhincsubjectdiscovery";
    public static final String INTERNAL_DOC_QUERY = "nhincdocumentquery";
    public static final String INTERNAL_DOC_RETRIEVE = "nhincdocumentretrieve";
    public static final String INTERNAL_AUDIT_QUERY = "nhincauditquery";
    public static final String INTERNAL_HIEM_SUBSCRIBE = "nhincnotificationproducer";
    public static final String INTERNAL_HIEM_UNSUBSCRIBE = "nhincsubscriptionmanager";
    public static final String INTERNAL_HIEM_NOTIFY = "nhincnotificationconsumer";

    public static String getHomeCommunityId(){
        log.debug("Entering SamlTokenExtractorHelper.getHomeCommunityId");
        String propFile = "gateway";
        String propName = "localHomeCommunityId";
        String homeCommunityId = "";
        try {
            homeCommunityId = PropertyAccessor.getProperty(propFile, propName);
        } catch (PropertyAccessException ex) {
            log.error("SamlTokenExtractorHelper.getHomeCommunityId failed to access property: " + ex.getMessage());
        }
        
        log.debug("Exiting SamlTokenExtractorHelper.getHomeCommunityId: " + homeCommunityId);
        return homeCommunityId;
    }
    
    public static String getEndpointURL(String homeCommunityId, String service) {
        log.debug("Entering SamlTokenExtractorHelper.getEndpointURL");

        BusinessEntity oCMBusinessEntity = new BusinessEntity();
        String url = null;

        if (NullChecker.isNotNullish(homeCommunityId) &&
                NullChecker.isNotNullish(service)) {
            try {
                oCMBusinessEntity = ConnectionManagerCache.getInstance().getBusinessEntityByServiceName(homeCommunityId, service);
            } catch (Throwable t) {
                log.error("Failed to retrieve business entity.  Error: " + t.getMessage());
            }

            if (oCMBusinessEntity != null &&
                    oCMBusinessEntity.getBusinessServices() != null &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService() != null &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().size() > 0 &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().get(0) != null &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates() != null &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates().getBindingTemplate() != null &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates().getBindingTemplate().size() > 0 &&
                    oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates().getBindingTemplate().get(0) != null &&
                    NullChecker.isNotNullish(oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates().getBindingTemplate().get(0).getAccessPoint().getValue())) {
                url = oCMBusinessEntity.getBusinessServices().getBusinessService().get(0).getBindingTemplates().getBindingTemplate().get(0).getAccessPoint().getValue();
            }

            if (NullChecker.isNotNullish(url)) {
                log.info("Returning URL: " + url);
            } else {
                log.error("Did not find a URL for home community id: " + homeCommunityId + " and service: " + service);
            }
        }
        else {
            log.error("A Null input parameter was passed to this method");
        }

        log.debug("Exiting SamlTokenExtractorHelper.getEndpointURL");
        return url;
    }
}
