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
package gov.hhs.fha.nhinc.hiem.processor.entity;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import java.util.Map;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import javax.xml.ws.BindingProvider;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;

/**
 * Helper class used to send a notify message to a remote gateway.
 * 
 * @author Neil Webb
 */
public class NotificationMessageSender
{
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(NotificationMessageSender.class);

    /**
     * Send a notify message to a remote gateway.
     *
     * @param notifyMessage Notify message to be sent
     * @param assertion Assertion to be used when sending the message.
     * @param endpointAddress URL to the remote gateway
     */
    public void sendNotify(NotificationMessageHolderType notifyMessage, AssertionType assertion, String endpointAddress)
    {
        if(assertion == null)
        {
            log.warn("NotificationMessageSender - The assertion was null for the entity notify message");
        }
        else
        {
            log.warn("NotificationMessageSender - The assertion was not null for the entity notify message");
        }
        SamlTokenCreator tokenCreator = new SamlTokenCreator();
        Map requestContext = tokenCreator.CreateRequestContext(assertion, endpointAddress, NhincConstants.NOTIFY_ACTION);
        try
        { // Call Web Service Operation
            org.oasis_open.docs.wsn.bw_2.NotificationConsumerService service = new org.oasis_open.docs.wsn.bw_2.NotificationConsumerService();
            org.oasis_open.docs.wsn.bw_2.NotificationConsumer port = service.getNotificationConsumerPort();

            ((BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
            ((BindingProvider) port).getRequestContext().putAll(requestContext);
            
            org.oasis_open.docs.wsn.b_2.Notify notify = new org.oasis_open.docs.wsn.b_2.Notify();
            //loadNotify(notifyMessage, rawNotifyXml);
            notify.getNotificationMessage().add(notifyMessage);
            
            port.notify(notify);
        }
        catch (Exception ex)
        {
            log.error("Error sending Notify: " + ex.getMessage(), ex);
        }
    }

//    /**
//     * Send a notify message to a remote gateway.
//     *
//     * @param notifyMessage Notify message to be sent
//     * @param assertion Assertion to be used when sending the message.
//     * @param endpointAddress URL to the remote gateway
//     * @param rawNotifyXml Raw SOAP notify message received from on the entity interface.
//     */
//    @SuppressWarnings("unchecked")
//    public void sendNotify(NotificationMessageHolderType notifyMessage, AssertionType assertion, String endpointAddress, String rawNotifyXml)
//    {
//        if(assertion == null)
//        {
//            log.warn("NotificationMessageSender - The assertion was null for the entity notify message");
//        }
//        else
//        {
//            log.warn("NotificationMessageSender - The assertion was not null for the entity notify message");
//        }
//        SamlTokenCreator tokenCreator = new SamlTokenCreator();
//        Map requestContext = tokenCreator.CreateRequestContext(assertion, endpointAddress, NhincConstants.NOTIFY_ACTION);
//        try
//        { // Call Web Service Operation
//            org.oasis_open.docs.wsn.bw_2.NotificationConsumerService service = new org.oasis_open.docs.wsn.bw_2.NotificationConsumerService();
//            org.oasis_open.docs.wsn.bw_2.NotificationConsumer port = service.getNotificationConsumerPort();
//
//            ((BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
//            ((BindingProvider) port).getRequestContext().putAll(requestContext);
//
//            org.oasis_open.docs.wsn.b_2.Notify notify = new org.oasis_open.docs.wsn.b_2.Notify();
//            loadNotify(notifyMessage, rawNotifyXml);
//            notify.getNotificationMessage().add(notifyMessage);
//
//            port.notify(notify);
//        }
//        catch (Exception ex)
//        {
//            log.error("Error sending Notify: " + ex.getMessage(), ex);
//        }
//    }
//
//    /**
//     * Reload the notify message body content into the notify message. This is
//     * necessary due to a JAXB parsing error that causes the message to fail
//     * when sent to a remote gateway.
//     *
//     * @param input The notify message being sent to a remote gateway
//     * @param notifyXml Raw notify message containing the notify message body.
//     */
//    private void loadNotify(NotificationMessageHolderType input, String notifyXml)
//    {
//        try
//        {
//            javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
//            javax.xml.xpath.XPath xpath = factory.newXPath();
//            InputSource inputSource = new InputSource(new ByteArrayInputStream(notifyXml.getBytes()));
//            log.debug("About to perform notify message payload xpath query");
//
//            Node msgNode = (Node) xpath.evaluate("//*[local-name()='Notify']/*[local-name()='NotificationMessage']/*[local-name()='Message']", inputSource, XPathConstants.NODE);
//            if((msgNode != null) && (msgNode.getFirstChild() != null))
//            {
//                log.debug("Message node was not null - type: " + msgNode.getClass().getName());
//                if((input != null) && (input.getMessage() != null))
//                {
//                    NodeList nodes = msgNode.getChildNodes();
//                    if((nodes != null) && (nodes.getLength() > 0))
//                    {
//                        for(int i = 0; i < nodes.getLength(); i++)
//                        {
//                            Node childNode = nodes.item(i);
//                            if(childNode != null)
//                            {
//                                log.debug("Node name: " + childNode.getLocalName());
//                                if(childNode instanceof Element)
//                                {
//                                    input.getMessage().setAny(childNode);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                else
//                {
//                    log.debug("Notify message holder type or message was null");
//                }
//            }
//            else
//            {
//                log.debug("Message node or first child was null");
//            }
//        }
//        catch (XPathExpressionException ex)
//        {
//            log.error("XPathExpressionException exception encountered loading the notify message body: " + ex.getMessage(), ex);
//        }
//    }

}
