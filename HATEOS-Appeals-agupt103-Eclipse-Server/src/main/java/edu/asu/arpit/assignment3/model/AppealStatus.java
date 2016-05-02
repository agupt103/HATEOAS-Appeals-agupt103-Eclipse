package edu.asu.arpit.assignment3.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum AppealStatus {
    @XmlEnumValue(value="Approved")
    APPROVED,
    @XmlEnumValue(value="Pending")
    PENDING, 
    @XmlEnumValue(value="Rejected")
    REJECTED, 
    @XmlEnumValue(value="Deleted")
    DELETED, 
}
