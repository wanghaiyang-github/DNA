package com.bazl.hslims.task;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.SystemUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
public class SyncDelegationFeedbackTask implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LimsCaseInfoService limsCaseInfoService;
    @Autowired
    private LimsConsignmentService limsConsignmentService;
    @Autowired
    private LimsSampleInfoService limsSampleInfoService;
    @Autowired
    private LimsSampleGeneService limsSampleGeneService;
    @Autowired
    private QueueSampleService queueSampleService;
    @Autowired
    private QueueDetailService queueDetailService;

    @Override
    public void run() {

        log.info("开始检索有现堪号无委托号的数据....");

        doSync();

        log.info("完成同步数据....");

    }

    private boolean doSync() {
        String xkAdress = SystemUtil.getSystemConfig().getProperty("xkAdress");
        String result = "";

        LimsConsignment limsConsignment = new LimsConsignment();
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        //查询19年有现堪号，没有委托号或A号案件
        List<LimsCaseInfo> limsCaseInfoList = limsCaseInfoService.selectNotConsignmentNoList(limsCaseInfo);

        for (LimsCaseInfo caseInfo : limsCaseInfoList) {
            try {
                HttpClient client = HttpClients.createDefault();
                // 要调用的接口方法
                String url = xkAdress + caseInfo.getCaseXkNo().trim();
                HttpPost post = new HttpPost(url);
                try {
                    StringEntity s = new StringEntity("");
                    s.setContentEncoding("UTF-8");
                    s.setContentType("application/json");
                    post.setEntity(s);
                    post.addHeader("content-type", "text/xml");
                    HttpResponse res = client.execute(post);
                    result = EntityUtils.toString(res.getEntity());
                    System.out.println(result);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Document document = DocumentHelper.parseText(result);
                Element root = document.getRootElement();
                Element caseElement = root.element("CASE");
                //委托编号
                Element caseNoAttr = caseElement.element("WTBH");
                if (caseNoAttr != null) {
                    //更新委托编号
                    List<LimsConsignment> limsConsignmentList = limsConsignmentService.selectListByCaseId(caseInfo.getId());
                    for (LimsConsignment consignment : limsConsignmentList) {
                        if (consignment.getAdditionalFlag().equals("0")) {
                            limsConsignment.setId(consignment.getId());
                            limsConsignment.setConsignmentNo(caseNoAttr.getTextTrim());
                            limsConsignmentService.updateByConsignmentNo(limsConsignment);
                        }
                    }
                }
                //现堪A号
                Element xkAnoAttr = caseElement.element("CASE_NO");
                if (xkAnoAttr != null) {
                    //更新A号
                    limsCaseInfo.setId(caseInfo.getId());
                    limsCaseInfo.setCaseXkAno(xkAnoAttr.getTextTrim());
                    limsCaseInfoService.updateCaseXkAno(limsCaseInfo);
                }
                //检材
                Element bioEvidenceListElement = root.element("BIO_EVIDENCE_LIST");
                List bioEvidenceElementList = bioEvidenceListElement.elements("BIO_EVIDENCE");
                for (int i = 0; i < bioEvidenceElementList.size(); i++) {
                    LimsSampleInfo sampleInfo = new LimsSampleInfo();
                    LimsSampleInfo sample = new LimsSampleInfo();
                    Element bioEvidenceElement = (Element) bioEvidenceElementList.get(i);

                    //检材名称
                    Element evidenceNameAttr = bioEvidenceElement.element("EVIDENCE_NAME");
                    if (evidenceNameAttr != null) {
                        sampleInfo.setSampleName(evidenceNameAttr.getTextTrim());
                    }
                    //物证编号
                    Element wnoAttr = bioEvidenceElement.element("W_NO");
                    if (wnoAttr != null) {
                        List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(caseInfo.getId());
                        for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                            if (sampleInfo.getSampleName().equals(limsSampleInfo.getSampleName())) {
                                sample.setId(limsSampleInfo.getId());
                                sample.setEvidenceNo(wnoAttr.getTextTrim());
                                limsSampleInfoService.updateByEvidenceNo(sample);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.info("目前系统没有对应的勘验信息" + e);
            }
            //插入队列
            List<LimsConsignment> limsConsignments = limsConsignmentService.selectListByCaseId(caseInfo.getId());
            entrustFeedback(limsConsignments);

        }
        return true;
    }

    public void entrustFeedback(List<LimsConsignment> limsConsignmentList) {
        QueueSample queueSample = new QueueSample();
        QueueDetail queueDetail = new QueueDetail();

        for (LimsConsignment limsConsignment : limsConsignmentList) {

            /**
             * 插入案件已委托队列
             */
            queueSample = new QueueSample();
            queueSample.setCaseId(limsConsignment.getCaseId());
            queueSample.setConsignmentId(limsConsignment.getId());
            queueSample.setStatus("0");
            queueSample.setCreateDatetime(new Date());
            queueSample.setOperateDatetime(new Date());
            queueSample.setOperatePerson("管理员");
            queueSample.setQueueType(Constants.CASE_INFO_COMMISSIONED);//已委托
            try {
                queueSampleService.insert(queueSample);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入已委托队列错误！", e);
            }


            if (limsConsignment.getStatus().equals("02") || limsConsignment.getStatus().equals("03")) {
                /**
                 * 插入案件已受理队列
                 */
                queueSample = new QueueSample();
                queueSample.setCaseId(limsConsignment.getCaseId());
                queueSample.setConsignmentId(limsConsignment.getId());
                queueSample.setStatus("0");
                queueSample.setCreateDatetime(new Date());
                queueSample.setOperateDatetime(new Date());
                queueSample.setOperatePerson("管理员");
                queueSample.setQueueType(Constants.CASE_INFO_ACCEPTED);//案件已受理
                try {
                    queueSampleService.insert(queueSample);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入案件已受理队列错误！", e);
                }

                /**
                 * 插入物证已受理队列
                 */
                queueSample = new QueueSample();
                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(limsConsignment.getCaseId());
                for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                    if (limsSampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != limsSampleInfo.getEvidenceNo()) {
                        queueSample.setCaseId(limsConsignment.getCaseId());
                        queueSample.setConsignmentId(limsConsignment.getId());
                        queueSample.setStatus("0");
                        queueSample.setCreateDatetime(new Date());
                        queueSample.setOperateDatetime(new Date());
                        queueSample.setOperatePerson("管理员");
                        queueSample.setQueueType(Constants.CASE_INFO_IDENTIFIED);//物证已受理
                        try {
                            queueSampleService.insert(queueSample);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("插入物证已受理队列错误1！", e);
                        }
                    }
                }

                limsSampleInfoList = limsSampleInfoService.selectListByCaseId(limsConsignment.getCaseId());
                for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                    if (limsSampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != limsSampleInfo.getEvidenceNo()) {
                        queueDetail = new QueueDetail();
                        queueDetail.setQueueId(queueSample.getId());
                        queueDetail.setCaseId(limsConsignment.getCaseId());
                        queueDetail.setSampleId(limsSampleInfo.getEvidenceNo());
                        queueDetail.setStatus("0");
                        queueDetail.setCreatePerson("管理员");
                        queueDetail.setCreateDatetime(new Date());

                        try {
                            queueDetailService.insert(queueDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("插入物证已受理队列错误2！", e);
                        }
                    }
                }

                /**
                 * 插入物证已检出队列
                 */
                List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectListByCaseId(limsConsignment.getCaseId());
                if (limsSampleGeneList.size() > 0) {
                    for (LimsSampleGene limsSampleGene : limsSampleGeneList) {
                        LimsSampleInfo limsSampleInfo = limsSampleInfoService.selectById(limsSampleGene.getSampleId());
                        if (limsSampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != limsSampleInfo.getEvidenceNo()) {
                            if (limsSampleGene.getAuditStatus().equals("1")) {
                                queueSample = new QueueSample();
                                queueSample.setCaseId(limsConsignment.getCaseId());
                                queueSample.setConsignmentId(limsConsignment.getId());
                                queueSample.setStatus("0");
                                queueSample.setCreateDatetime(new Date());
                                queueSample.setOperateDatetime(new Date());
                                queueSample.setOperatePerson("管理员");
                                queueSample.setQueueType(Constants.CASE_INFO_DETECTION);//物证已检出
                                try {
                                    queueSampleService.insert(queueSample);
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error("插入物证已检出队列错误！", e);
                                }
                            }
                        }
                    }

                    for (LimsSampleGene limsSampleGene : limsSampleGeneList) {
                        if (limsSampleGene.getAuditStatus().equals("1")) {
                            LimsSampleInfo limsSampleInfo = limsSampleInfoService.selectById(limsSampleGene.getSampleId());
                            if (limsSampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != limsSampleInfo.getEvidenceNo()) {
                                queueDetail = new QueueDetail();
                                queueDetail.setQueueId(queueSample.getId());
                                queueDetail.setCaseId(limsConsignment.getCaseId());
                                queueDetail.setSampleId(limsSampleInfo.getEvidenceNo());
                                queueDetail.setStatus("0");
                                queueDetail.setCreatePerson("管理员");
                                queueDetail.setCreateDatetime(new Date());
                                queueDetailService.insert(queueDetail);
                            }

                        }

                    }

                }
            }
        }
    }
}