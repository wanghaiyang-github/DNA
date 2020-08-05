
package com.bazl.hslims.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>casePersonnelSample complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="casePersonnelSample"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="caseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="caseLabNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="certificateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="certificateNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="collectAgencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="collectAgencyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="collectBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="collectDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="createDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="createUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deleteFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="district" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="examineUser1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="examineUser1Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fingerprintNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCardNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="initServerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isSupplied" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="labId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nativePlaceAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personnelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personnelNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personnelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="relationWithCase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="reserve8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="residenceAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sampleDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sampleLabNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="samplePackaging" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sampleStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sampleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="storeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="updateDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="updateUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "casePersonnelSample", propOrder = {
    "caseId",
    "caseLabNo",
    "certificateName",
    "certificateNo",
    "collectAgencyCode",
    "collectAgencyName",
    "collectBy",
    "collectDate",
    "createDatetime",
    "createUser",
    "deleteFlag",
    "district",
    "examineUser1",
    "examineUser1Id",
    "fingerprintNo",
    "gender",
    "id",
    "idCardNo",
    "initServerNo",
    "isSupplied",
    "labId",
    "nationality",
    "nativePlaceAddr",
    "personnelName",
    "personnelNo",
    "personnelType",
    "relationWithCase",
    "remark",
    "reserve8",
    "residenceAddr",
    "sampleDescription",
    "sampleLabNo",
    "samplePackaging",
    "sampleStatus",
    "sampleType",
    "storeFlag",
    "updateDatetime",
    "updateUser"
})
public class CasePersonnelSample {

    protected String caseId;
    protected String caseLabNo;
    protected String certificateName;
    protected String certificateNo;
    protected String collectAgencyCode;
    protected String collectAgencyName;
    protected String collectBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar collectDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDatetime;
    protected String createUser;
    protected String deleteFlag;
    protected String district;
    protected String examineUser1;
    protected String examineUser1Id;
    protected String fingerprintNo;
    protected String gender;
    protected String id;
    protected String idCardNo;
    protected String initServerNo;
    protected String isSupplied;
    protected String labId;
    protected String nationality;
    protected String nativePlaceAddr;
    protected String personnelName;
    protected String personnelNo;
    protected String personnelType;
    protected String relationWithCase;
    protected String remark;
    protected String reserve8;
    protected String residenceAddr;
    protected String sampleDescription;
    protected String sampleLabNo;
    protected String samplePackaging;
    protected String sampleStatus;
    protected String sampleType;
    protected String storeFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDatetime;
    protected String updateUser;

    /**
     * ��ȡcaseId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * ����caseId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseId(String value) {
        this.caseId = value;
    }

    /**
     * ��ȡcaseLabNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseLabNo() {
        return caseLabNo;
    }

    /**
     * ����caseLabNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseLabNo(String value) {
        this.caseLabNo = value;
    }

    /**
     * ��ȡcertificateName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * ����certificateName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateName(String value) {
        this.certificateName = value;
    }

    /**
     * ��ȡcertificateNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * ����certificateNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateNo(String value) {
        this.certificateNo = value;
    }

    /**
     * ��ȡcollectAgencyCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectAgencyCode() {
        return collectAgencyCode;
    }

    /**
     * ����collectAgencyCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectAgencyCode(String value) {
        this.collectAgencyCode = value;
    }

    /**
     * ��ȡcollectAgencyName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectAgencyName() {
        return collectAgencyName;
    }

    /**
     * ����collectAgencyName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectAgencyName(String value) {
        this.collectAgencyName = value;
    }

    /**
     * ��ȡcollectBy���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectBy() {
        return collectBy;
    }

    /**
     * ����collectBy���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectBy(String value) {
        this.collectBy = value;
    }

    /**
     * ��ȡcollectDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCollectDate() {
        return collectDate;
    }

    /**
     * ����collectDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCollectDate(XMLGregorianCalendar value) {
        this.collectDate = value;
    }

    /**
     * ��ȡcreateDatetime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDatetime() {
        return createDatetime;
    }

    /**
     * ����createDatetime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDatetime(XMLGregorianCalendar value) {
        this.createDatetime = value;
    }

    /**
     * ��ȡcreateUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * ����createUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    /**
     * ��ȡdeleteFlag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * ����deleteFlag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteFlag(String value) {
        this.deleteFlag = value;
    }

    /**
     * ��ȡdistrict���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * ����district���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * ��ȡexamineUser1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExamineUser1() {
        return examineUser1;
    }

    /**
     * ����examineUser1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExamineUser1(String value) {
        this.examineUser1 = value;
    }

    /**
     * ��ȡexamineUser1Id���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExamineUser1Id() {
        return examineUser1Id;
    }

    /**
     * ����examineUser1Id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExamineUser1Id(String value) {
        this.examineUser1Id = value;
    }

    /**
     * ��ȡfingerprintNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFingerprintNo() {
        return fingerprintNo;
    }

    /**
     * ����fingerprintNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFingerprintNo(String value) {
        this.fingerprintNo = value;
    }

    /**
     * ��ȡgender���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * ����gender���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * ��ȡidCardNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     * ����idCardNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCardNo(String value) {
        this.idCardNo = value;
    }

    /**
     * ��ȡinitServerNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitServerNo() {
        return initServerNo;
    }

    /**
     * ����initServerNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitServerNo(String value) {
        this.initServerNo = value;
    }

    /**
     * ��ȡisSupplied���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSupplied() {
        return isSupplied;
    }

    /**
     * ����isSupplied���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSupplied(String value) {
        this.isSupplied = value;
    }

    /**
     * ��ȡlabId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabId() {
        return labId;
    }

    /**
     * ����labId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabId(String value) {
        this.labId = value;
    }

    /**
     * ��ȡnationality���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * ����nationality���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * ��ȡnativePlaceAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNativePlaceAddr() {
        return nativePlaceAddr;
    }

    /**
     * ����nativePlaceAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNativePlaceAddr(String value) {
        this.nativePlaceAddr = value;
    }

    /**
     * ��ȡpersonnelName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonnelName() {
        return personnelName;
    }

    /**
     * ����personnelName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonnelName(String value) {
        this.personnelName = value;
    }

    /**
     * ��ȡpersonnelNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonnelNo() {
        return personnelNo;
    }

    /**
     * ����personnelNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonnelNo(String value) {
        this.personnelNo = value;
    }

    /**
     * ��ȡpersonnelType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonnelType() {
        return personnelType;
    }

    /**
     * ����personnelType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonnelType(String value) {
        this.personnelType = value;
    }

    /**
     * ��ȡrelationWithCase���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationWithCase() {
        return relationWithCase;
    }

    /**
     * ����relationWithCase���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationWithCase(String value) {
        this.relationWithCase = value;
    }

    /**
     * ��ȡremark���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * ����remark���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * ��ȡreserve8���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserve8() {
        return reserve8;
    }

    /**
     * ����reserve8���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserve8(String value) {
        this.reserve8 = value;
    }

    /**
     * ��ȡresidenceAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenceAddr() {
        return residenceAddr;
    }

    /**
     * ����residenceAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenceAddr(String value) {
        this.residenceAddr = value;
    }

    /**
     * ��ȡsampleDescription���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleDescription() {
        return sampleDescription;
    }

    /**
     * ����sampleDescription���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleDescription(String value) {
        this.sampleDescription = value;
    }

    /**
     * ��ȡsampleLabNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleLabNo() {
        return sampleLabNo;
    }

    /**
     * ����sampleLabNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleLabNo(String value) {
        this.sampleLabNo = value;
    }

    /**
     * ��ȡsamplePackaging���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSamplePackaging() {
        return samplePackaging;
    }

    /**
     * ����samplePackaging���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSamplePackaging(String value) {
        this.samplePackaging = value;
    }

    /**
     * ��ȡsampleStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleStatus() {
        return sampleStatus;
    }

    /**
     * ����sampleStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleStatus(String value) {
        this.sampleStatus = value;
    }

    /**
     * ��ȡsampleType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * ����sampleType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleType(String value) {
        this.sampleType = value;
    }

    /**
     * ��ȡstoreFlag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreFlag() {
        return storeFlag;
    }

    /**
     * ����storeFlag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreFlag(String value) {
        this.storeFlag = value;
    }

    /**
     * ��ȡupdateDatetime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * ����updateDatetime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDatetime(XMLGregorianCalendar value) {
        this.updateDatetime = value;
    }

    /**
     * ��ȡupdateUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * ����updateUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

}
