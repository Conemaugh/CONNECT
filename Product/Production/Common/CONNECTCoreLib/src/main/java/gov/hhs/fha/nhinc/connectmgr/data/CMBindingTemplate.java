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
package gov.hhs.fha.nhinc.connectmgr.data;

/**
 * @author westbergl
 * @version 1.0
 * @created 20-Oct-2008 12:06:46 PM
 */
public class CMBindingTemplate {

    private String bindingKey = "";
    private String endpointURL = "";
    private String wsdlURL = "";
    private String serviceVersion = "";

    /**
     * Default constructor.
     */
    public CMBindingTemplate() {
        clear();
    }

    /**
     * Clear the contents of this and set it to a default state.
     */
    public void clear() {
        endpointURL = "";
        wsdlURL = "";
        bindingKey = "";
        serviceVersion = "";
    }

    /**
     * Returns true of the contents of the object are the same as the one
     * passed in.
     * 
     * @param oCompare The object to compare.
     * @return TRUE if the contents are the same as the one passed in.
     */
    public boolean equals(CMBindingTemplate oCompare) {
        if ((!this.bindingKey.equals(oCompare.bindingKey)) ||
                (!this.endpointURL.equals(oCompare.endpointURL)) ||
                (!this.wsdlURL.equals(oCompare.wsdlURL)) ||
                (!this.serviceVersion.equals(oCompare.serviceVersion))) {
            return false;
        }

        // If we got here then everything is the same...
        //----------------------------------------------
        return true;
    }

    /**
     * Returns the binding key for this binding.
     * 
     * @return The binding key for this binding.
     */
    public String getBindingKey() {
        return bindingKey;
    }

    /**
     * Sets the binding key for this binding.
     * 
     * @param bindingKey The binding key for this binding.
     */
    public void setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
    }

    /**
     * Returns the end point URL for this binding.
     * 
     * @return The end point URL for this binding.
     */
    public String getEndpointURL() {
        return endpointURL;
    }

    /**
     * Sets the end point URL for this binding.
     * 
     * @param endpointURL The end point URL for this binding.
     */
    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Returns the URL for the WSDL for this binding.
     * 
     * @return The URL for the WSDL for this binding.
     */
    public String getWsdlURL() {
        return wsdlURL;
    }

    /**
     * Sets the URL for the WSDL for this binding.
     * 
     * @param wsdlURL The URL for the WSDL for this binding.
     */
    public void setWsdlURL(String wsdlURL) {
        this.wsdlURL = wsdlURL;
    }

    /**
     * Returns the Service Version for this binding.
     *
     * @return The URL for the WSDL for this binding.
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Sets the Service Version for this binding.
     *
     * @param wsdlURL The URL for the WSDL for this binding.
     */
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * Creates a deep copy of this object.
     *
     * @return A copy of this CMBindingTemplate object
     */
    public CMBindingTemplate createCopy() {
        CMBindingTemplate templateCopy = new CMBindingTemplate();
        templateCopy.setBindingKey(bindingKey);
        templateCopy.setEndpointURL(endpointURL);
        templateCopy.setServiceVersion(serviceVersion);
        templateCopy.setWsdlURL(wsdlURL);
        return templateCopy;
    }
}
