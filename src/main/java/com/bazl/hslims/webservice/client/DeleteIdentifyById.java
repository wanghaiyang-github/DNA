
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
 *         &lt;element name="jd_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="wzlb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "jdId",
    "wzlb"
})
@XmlRootElement(name = "deleteIdentifyById")
public class DeleteIdentifyById {

    @XmlElement(name = "jd_id", required = true, nillable = true)
    protected String jdId;
    @XmlElement(required = true, nillable = true)
    protected String wzlb;

    /**
     * 获取jdId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdId() {
        return jdId;
    }

    /**
     * 设置jdId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdId(String value) {
        this.jdId = value;
    }

    /**
     * 获取wzlb属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWzlb() {
        return wzlb;
    }

    /**
     * 设置wzlb属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWzlb(String value) {
        this.wzlb = value;
    }

}
