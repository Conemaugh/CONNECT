/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.async;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JHOPPESC
 */
public class AsyncMessageIdCreator {

    public Map CreateRequestContextForMessageId(AssertionType assertion) {
        Map requestContext = new HashMap();

        // Set the value for the message id property in the request context
        if (assertion != null &&
                NullChecker.isNotNullish(assertion.getAsyncMessageId())) {
            requestContext.put(NhincConstants.ASYNC_MESSAGE_ID_PROP, assertion.getAsyncMessageId());
        }

        // set the value for the message type property in the request context
        requestContext.put(NhincConstants.ASYNC_MSG_TYPE_PROP, NhincConstants.ASYNC_REQUEST_MSG_TYPE_VAL);

        return requestContext;
    }

    public Map CreateRequestContextForRelatesTo(AssertionType assertion) {
        Map requestContext = new HashMap();

        if (assertion != null &&
                NullChecker.isNotNullish(assertion.getAsyncMessageId())) {
            requestContext.put(NhincConstants.ASYNC_RELATES_TO_PROP, assertion.getAsyncMessageId());
        }

        // set the value for the message type property in the request context
        requestContext.put(NhincConstants.ASYNC_MSG_TYPE_PROP, NhincConstants.ASYNC_RESPONSE_MSG_TYPE_VAL);

        return requestContext;
    }
}