
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
     * ��ȡjdId���Ե�ֵ��
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
     * ����jdId���Ե�ֵ��
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
     * ��ȡwzlb���Ե�ֵ��
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
     * ����wzlb���Ե�ֵ��
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
