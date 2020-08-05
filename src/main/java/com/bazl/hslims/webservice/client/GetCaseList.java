
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
 *         &lt;element name="k_no" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="scene_detail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="k_user" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="begin_time" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="end_time" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="begin" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "kNo",
    "sceneDetail",
    "kUser",
    "beginTime",
    "endTime",
    "begin",
    "end"
})
@XmlRootElement(name = "GetCaseList")
public class GetCaseList {

    @XmlElement(name = "k_no", required = true, nillable = true)
    protected String kNo;
    @XmlElement(name = "scene_detail", required = true, nillable = true)
    protected String sceneDetail;
    @XmlElement(name = "k_user", required = true, nillable = true)
    protected String kUser;
    @XmlElement(name = "begin_time", required = true, nillable = true)
    protected String beginTime;
    @XmlElement(name = "end_time", required = true, nillable = true)
    protected String endTime;
    @XmlElement(required = true, nillable = true)
    protected String begin;
    @XmlElement(required = true, nillable = true)
    protected String end;

    /**
     * ��ȡkNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKNo() {
        return kNo;
    }

    /**
     * ����kNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKNo(String value) {
        this.kNo = value;
    }

    /**
     * ��ȡsceneDetail���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSceneDetail() {
        return sceneDetail;
    }

    /**
     * ����sceneDetail���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSceneDetail(String value) {
        this.sceneDetail = value;
    }

    /**
     * ��ȡkUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUser() {
        return kUser;
    }

    /**
     * ����kUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUser(String value) {
        this.kUser = value;
    }

    /**
     * ��ȡbeginTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * ����beginTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeginTime(String value) {
        this.beginTime = value;
    }

    /**
     * ��ȡendTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * ����endTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * ��ȡbegin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegin() {
        return begin;
    }

    /**
     * ����begin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegin(String value) {
        this.begin = value;
    }

    /**
     * ��ȡend���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * ����end���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

}
