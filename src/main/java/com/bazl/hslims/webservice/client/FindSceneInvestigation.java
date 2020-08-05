
package com.bazl.hslims.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="investigation_no" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="evi_type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "investigationNo",
    "eviType"
})
@XmlRootElement(name = "findSceneInvestigation")
public class FindSceneInvestigation {

    @XmlElement(name = "investigation_no", required = true, nillable = true)
    protected String investigationNo;
    @XmlElement(name = "evi_type", required = true, nillable = true)
    protected String eviType;

    /**
     * 获取investigationNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvestigationNo() {
        return investigationNo;
    }

    /**
     * 设置investigationNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvestigationNo(String value) {
        this.investigationNo = value;
    }

    /**
     * 获取eviType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEviType() {
        return eviType;
    }

    /**
     * 设置eviType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEviType(String value) {
        this.eviType = value;
    }

}
