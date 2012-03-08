/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a transaction message passed between client and server.
 * 
 * @author Mateusz Parzonka
 *
 */
@XmlRootElement(name = "transaction")
public class Transaction {
    
    private String transactionResource;
    private String transactionId;
    private String resourceId; 
    private String transactionUrl;

    public Transaction() {
	super();
    }
	    
    public Transaction(String transactionResource, String transactionId, String resourceId, String transactionUrl) {
	super();
	this.transactionResource = transactionResource;
	this.transactionId = transactionId;
	this.resourceId = resourceId;
	this.transactionUrl = transactionUrl;
    }
    
    public String getTransactionResource() {
        return transactionResource;
    }
    public void setTransactionResource(String transactionResource) {
        this.transactionResource = transactionResource;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    public String getTransactionUrl() {
        return transactionUrl;
    }
    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Transaction [transactionResource=");
	builder.append(transactionResource);
	builder.append(", transactionId=");
	builder.append(transactionId);
	builder.append(", resourceId=");
	builder.append(resourceId);
	builder.append(", transactionUrl=");
	builder.append(transactionUrl);
	builder.append("]");
	return builder.toString();
    }
    
}
