
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
     * ��ȡinvestigationNo���Ե�ֵ��
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
     * ����investigationNo���Ե�ֵ��
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
     * ��ȡeviType���Ե�ֵ��
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
     * ����eviType���Ե�ֵ��
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
