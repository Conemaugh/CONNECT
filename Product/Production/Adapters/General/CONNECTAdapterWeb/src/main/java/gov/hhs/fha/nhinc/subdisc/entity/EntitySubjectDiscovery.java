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
package gov.hhs.fha.nhinc.subdisc.entity;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 *
 * @author Sai Valluripalli
 */
@WebService(serviceName = "EntitySubjectDiscovery", portName = "EntitySubjectDiscoveryPortSoap", endpointInterface = "gov.hhs.fha.nhinc.entitysubjectdiscovery.EntitySubjectDiscoveryPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:entitysubjectdiscovery", wsdlLocation = "WEB-INF/wsdl/EntitySubjectDiscovery/EntitySubjectDiscovery.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class EntitySubjectDiscovery {

    public org.hl7.v3.MCCIIN000002UV01 pixConsumerPRPAIN201301UV(org.hl7.v3.PIXConsumerPRPAIN201301UVRequestType pixConsumerPRPAIN201301UVRequest) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.hl7.v3.MCCIIN000002UV01 pixConsumerPRPAIN201302UV(org.hl7.v3.PIXConsumerPRPAIN201302UVRequestType pixConsumerPRPAIN201302UVRequest) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.hl7.v3.MCCIIN000002UV01 pixConsumerPRPAIN201304UV(org.hl7.v3.PIXConsumerPRPAIN201304UVRequestType pixConsumerPRPAIN201304UVRequest) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.hl7.v3.PIXConsumerPRPAIN201309UVResponseType pixConsumerPRPAIN201309UV(org.hl7.v3.PIXConsumerPRPAIN201309UVRequestType pixConsumerPRPAIN201309UVRequest) {
        return new EntitySubjectDiscoveryImpl().pixConsumerPRPAIN201309UV(pixConsumerPRPAIN201309UVRequest);
    }

}
