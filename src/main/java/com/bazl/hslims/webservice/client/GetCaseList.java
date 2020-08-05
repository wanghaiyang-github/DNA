
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
     * 获取kNo属性的值。
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
     * 设置kNo属性的值。
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
     * 获取sceneDetail属性的值。
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
     * 设置sceneDetail属性的值。
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
     * 获取kUser属性的值。
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
     * 设置kUser属性的值。
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
     * 获取beginTime属性的值。
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
     * 设置beginTime属性的值。
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
     * 获取endTime属性的值。
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
     * 设置endTime属性的值。
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
     * 获取begin属性的值。
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
     * 设置begin属性的值。
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
     * 获取end属性的值。
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
     * 设置end属性的值。
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
