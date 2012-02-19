package de.tud.cs.st.vespucci.sadclient.model;

import javax.xml.bind.annotation.XmlRootElement;

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
