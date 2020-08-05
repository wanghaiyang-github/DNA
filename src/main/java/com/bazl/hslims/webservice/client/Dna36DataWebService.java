package com.bazl.hslims.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.11
 * 2017-05-11T15:08:53.199+08:00
 * Generated source version: 3.0.11
 * 
 */
@WebService(targetNamespace = "http://webservice.lims.bazl.com/", name = "Dna36DataWebService")
@XmlSeeAlso({ObjectFactory.class})
public interface Dna36DataWebService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectCasePersonnelSampleBySampleLabNo", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SelectCasePersonnelSampleBySampleLabNo")
    @WebMethod
    @ResponseWrapper(localName = "selectCasePersonnelSampleBySampleLabNoResponse", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SelectCasePersonnelSampleBySampleLabNoResponse")
    public java.util.List<CasePersonnelSample> selectCasePersonnelSampleBySampleLabNo(
            @WebParam(name = "arg0", targetNamespace = "")
            String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectSceneEvidenceBySampleLabNo", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SelectSceneEvidenceBySampleLabNo")
    @WebMethod
    @ResponseWrapper(localName = "selectSceneEvidenceBySampleLabNoResponse", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SelectSceneEvidenceBySampleLabNoResponse")
    public java.util.List<SceneEvidence> selectSceneEvidenceBySampleLabNo(
            @WebParam(name = "arg0", targetNamespace = "")
            String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "submitLawCaseRegInfos", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SubmitLawCaseRegInfos")
    @WebMethod
    @ResponseWrapper(localName = "submitLawCaseRegInfosResponse", targetNamespace = "http://webservice.lims.bazl.com/", className = "com.bazl.hslims.webservice.client.SubmitLawCaseRegInfosResponse")
    public String submitLawCaseRegInfos(
            @WebParam(name = "arg0", targetNamespace = "")
            String arg0
    );
}
