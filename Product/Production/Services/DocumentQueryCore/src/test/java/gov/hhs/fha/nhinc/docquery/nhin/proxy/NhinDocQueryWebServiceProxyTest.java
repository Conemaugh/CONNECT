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
package gov.hhs.fha.nhinc.docquery.nhin.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.hhs.fha.nhinc.aspect.NwhinInvocationEvent;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.docquery.aspect.AdhocQueryRequestDescriptionBuilder;
import gov.hhs.fha.nhinc.docquery.aspect.AdhocQueryResponseDescriptionBuilder;
import ihe.iti.xds_b._2007.RespondingGatewayQueryPortType;

import java.lang.reflect.Method;

import javax.xml.ws.Service;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Neil Webb
 */
@RunWith(JMock.class)
public class NhinDocQueryWebServiceProxyTest {
    Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    final Service mockService = context.mock(Service.class);
    final RespondingGatewayQueryPortType mockPort = context.mock(RespondingGatewayQueryPortType.class);

    @Test
    public void hasBeginOutboundProcessingEvent() throws Exception {
        Class<NhinDocQueryProxyWebServiceSecuredImpl> clazz = NhinDocQueryProxyWebServiceSecuredImpl.class;
        Method method = clazz.getMethod("respondingGatewayCrossGatewayQuery", AdhocQueryRequest.class,
                AssertionType.class, NhinTargetSystemType.class);
        NwhinInvocationEvent annotation = method.getAnnotation(NwhinInvocationEvent.class);
        assertNotNull(annotation);
        assertEquals(AdhocQueryRequestDescriptionBuilder.class, annotation.beforeBuilder());
        assertEquals(AdhocQueryResponseDescriptionBuilder.class, annotation.afterReturningBuilder());
        assertEquals("Document Query", annotation.serviceType());
        assertEquals("", annotation.version());
    }
}