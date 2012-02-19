package de.tud.cs.st.vespucci.sadclient.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaction")
public class Transaction {
    
    @XmlAttribute private String transactionResource;
    private String transactionId;

    
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
}
