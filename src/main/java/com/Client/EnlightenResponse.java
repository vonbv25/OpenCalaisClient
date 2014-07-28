
package com.Client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EnlightenResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "enlightenResult"
})
@XmlRootElement(name = "EnlightenResponse")
public class EnlightenResponse {

    @XmlElement(name = "EnlightenResult", required = true)
    protected String enlightenResult;

    /**
     * Gets the value of the enlightenResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnlightenResult() {
        return enlightenResult;
    }

    /**
     * Sets the value of the enlightenResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnlightenResult(String value) {
        this.enlightenResult = value;
    }

}