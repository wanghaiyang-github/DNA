
package com.bazl.hslims.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="xmlString" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isbase64" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xmlString",
    "isbase64"
})
@XmlRootElement(name = "queryData")
public class QueryData {

    @XmlElement(required = true, nillable = true)
    protected String xmlString;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isbase64;

    /**
     * ��ȡxmlString���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlString() {
        return xmlString;
    }

    /**
     * ����xmlString���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlString(String value) {
        this.xmlString = value;
    }

    /**
     * ��ȡisbase64���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsbase64() {
        return isbase64;
    }

    /**
     * ����isbase64���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsbase64(Boolean value) {
        this.isbase64 = value;
    }

}
