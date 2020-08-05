package com.bazl.hslims.web.controller.center.archive;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/1/11.
 */
@RequestMapping("/center/6")
@Controller
public class ArchiveManageController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsIdentifyBookService limsIdentifyBookService;
    @Autowired
    LimsExtractRecordService limsExtractRecordService;
    @Autowired
    LimsPcrRecordService limsPcrRecordService;
    @Autowired
    LimsSyRecordService limsSyRecordService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    CaseInnerMatchedService caseInnerMatchedService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;

    @RequestMapping("/01.html")
    public ModelAndView into(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {

        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
            LimsIdentifyBook limsIdentifyBook;
            for (LimsCaseInfoVO lciVO : caseInfoList) {
                limsIdentifyBook = limsIdentifyBookService.selectByCaseId(lciVO.getEntity().getId());

                if (limsIdentifyBook != null) {
                    lciVO.setIdentifyBookStatusName(limsIdentifyBook.getStatusName());
                } else {
                    lciVO.setIdentifyBookStatusName(null);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/archivemanage/list");

        return modelAndView;
    }

    @RequestMapping("/print.html")
    public ModelAndView print(HttpServletRequest request, Integer caseId, Integer consignmentId) {

        ModelAndView modelAndView = new ModelAndView();

        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsExtractRecordList)) {
            LimsExtractRecord limsExtractRecord = limsExtractRecordList.get(0);
            modelAndView.addObject("extractRecordId", limsExtractRecord.getId());
        }

        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsPcrRecordList)) {
            LimsPcrRecord limsPcrRecord = limsPcrRecordList.get(0);
            modelAndView.addObject("pcrRecordId", limsPcrRecord.getId());
        }

        List<LimsSyRecord> limsSyRecordList = limsSyRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsSyRecordList)) {
            LimsSyRecord limsSyRecord = limsSyRecordList.get(0);
            modelAndView.addObject("syRecordId", limsSyRecord.getId());
        }

        LimsIdentifyBook identifyBook = limsIdentifyBookService.selectByCaseId(caseId);
        if (identifyBook != null) {
            if (StringUtils.isNotBlank(identifyBook.getFilePath()) || StringUtils.isNotBlank(identifyBook.getUploadPath())) {
                modelAndView.addObject("identifyBookPath", "identifyBookPath");
            }
        }

        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("consignmentId", consignmentId);
        modelAndView.setViewName("center/archivemanage/printList");

        return modelAndView;
    }

    @RequestMapping("/fileCatalog.html")
    public void generateFileCatalog(HttpServletRequest request, HttpServletResponse response, Integer caseId) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        Calendar now = Calendar.getInstance();
        params.put("currentYear", String.format("%tY", new Date()));
        params.put("currentMonth", now.get(Calendar.MONTH) + 1);
        params.put("currentDay", now.get(Calendar.DAY_OF_MONTH));

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("fileCatalog.ftl", "UTF-8");

            String filename = "-鉴定文书目录" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }

    }

    @RequestMapping("/identifyCover.html")
    public void generateIdentifyCover(HttpServletRequest request, HttpServletResponse response, Integer caseId) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("identifyCover.ftl", "UTF-8");

            String filename = "-鉴定文书封面" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }

    }

    @RequestMapping("/identifyApprovalFrom.html")
    public void generateIdentifyApprovalFrom(HttpServletRequest request, HttpServletResponse response, Integer caseId) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        if (ListUtils.isNotNullAndEmptyList(consignmentList)) {
            LimsConsignment consignment = consignmentList.get(0);

            params.put("delegateOrg", consignment.getDelegateOrgDesc());
        } else {
            params.put("delegateOrg", caseInfo.getCreatePerson());
        }

        String caseNo = caseInfo.getCaseNo();

        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                if (caseNo.split("-").length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }

        Calendar now = Calendar.getInstance();
        params.put("currentYear", String.format("%tY", new Date()));
        params.put("currentMonth", now.get(Calendar.MONTH) + 1);
        params.put("currentDay", now.get(Calendar.DAY_OF_MONTH));

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("identifyApprovalFrom.ftl", "UTF-8");

            String filename = "-鉴定文书审批表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }

    }

    @RequestMapping(value = "/allCheckPrintManage.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveRecord(HttpServletRequest request, HttpServletResponse response, @RequestBody AllCheckPrint allCheckPrint) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(allCheckPrint.getCaseId());

        Map<String, Object> result = new HashMap<String, Object>();

        String[] print = null;
        String writeFilePath = null;
        if (StringUtils.isNotBlank(allCheckPrint.getPrintManage())) {
            print = allCheckPrint.getPrintManage().split(",");

            List<File> fileList = new ArrayList<File>();

            String filePath = null;
            List<LimsSyRecord> recordList = null;
            for (int i = 0; i < print.length; i++) {

                if (print[i].equals("1")) {
                    filePath = generateAndDownFileCatalog(request, allCheckPrint.getCaseId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("2")) {
//                    filePath = createGenerateAndDownload(request, allCheckPrint.getConsignmentId());
                    filePath = generateAndDownIdentifyCover(request, allCheckPrint.getCaseId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("3")) {
//                    filePath = createSampleExchangeDoc(request, allCheckPrint.getConsignmentId());
                    filePath = generateAndDownIdentifyApprovalFrom(request, allCheckPrint.getCaseId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("4")) {
                    filePath = generateAndDownloadExtractDoc(request, allCheckPrint.getExtractRecordId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("5")) {
                    filePath = generateAndDownloadPcrDoc(request, allCheckPrint.getPcrRecordId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("6")) {
                    recordList = limsSyRecordService.selectListByCaseId(allCheckPrint.getCaseId());
                    if (ListUtils.isNotNullAndEmptyList(recordList)) {
                        for (int m = 0; m < recordList.size(); m++) {
                            filePath = downloadSytable(response, recordList.get(m).getId());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (StringUtils.isNotBlank(filePath)) {
                                fileList.add(new File(filePath));
                            }
                        }
                    }
                } else if (print[i].equals("7")) {
                    recordList = limsSyRecordService.selectListByCaseId(allCheckPrint.getCaseId());
                    if (ListUtils.isNotNullAndEmptyList(recordList)) {
                        for (int n = 0; n < recordList.size(); n++) {
                            filePath = generateAndDownloadSyDoc(request, recordList.get(n).getId());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (StringUtils.isNotBlank(filePath)) {
                                fileList.add(new File(filePath));
                            }
                        }
                    }
                } else if (print[i].equals("8")) {
                    filePath = generateReportIdentifyBook(request, allCheckPrint.getCaseId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("9")) {

                } else if (print[i].equals("10")) {
                    filePath = createReceiveFrom(request, caseInfo);
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                } else if (print[i].equals("11")) {
                    filePath = createDelegateDoc(request, allCheckPrint.getConsignmentId());
                    if (StringUtils.isNotBlank(filePath)) {
                        fileList.add(new File(filePath));
                    }
                }
            }

            String zipName = SystemUtil.getSystemConfig().getProperty("ZipName") + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
            try {
                writeFilePath = makePathFile(writeFilePath) + zipName;
                File zipFile = new File(writeFilePath);
                if (!zipFile.exists()) {
                    zipFile.createNewFile();
                }

                //创建文件输出流
                fos = new FileOutputStream(zipFile);
                /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                 * 所以这里我们把输出流转换一下*/
                zos = new ZipOutputStream(fos);
                /**这个方法接受的就是一个所要打包文件的集合，
                 * 还有一个ZipOutputStream*/
                zipFiles(fileList, zos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭流
                try {
                    if (null != zos) zos.close();
                    if (null != fos) fos.close();

                    for (int i = 0; i < fileList.size(); i++) {
                        File file = (File) fileList.get(i);
                        if (file.exists())
                            file.delete();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        result.put("success", writeFilePath);

        return result;
    }

    @RequestMapping("/downLoadZip.html")
    private void downLoadZip(HttpServletResponse response, String zipFilePath) {
        try {
            File file = new File(zipFilePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(zipFilePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (IOException e) {
            logger.error("文件下载出错", e);
        }
    }

    private String generateAndDownFileCatalog(HttpServletRequest request, Integer caseId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        Calendar now = Calendar.getInstance();
        params.put("currentYear", String.format("%tY", new Date()));
        params.put("currentMonth", now.get(Calendar.MONTH) + 1);
        params.put("currentDay", now.get(Calendar.DAY_OF_MONTH));

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("fileCatalog.ftl", "UTF-8");

            String filename = "-鉴定文书目录" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String generateAndDownIdentifyCover(HttpServletRequest request, Integer caseId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("identifyCover.ftl", "UTF-8");

            String filename = "-鉴定文书封面" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String generateAndDownIdentifyApprovalFrom(HttpServletRequest request, Integer caseId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        if (ListUtils.isNotNullAndEmptyList(consignmentList)) {
            LimsConsignment consignment = consignmentList.get(0);

            params.put("delegateOrg", consignment.getDelegateOrgDesc());
        } else {
            params.put("delegateOrg", caseInfo.getCreatePerson());
        }

        String caseNo = caseInfo.getCaseNo();

        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                if (caseNo.split("-").length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }

        Calendar now = Calendar.getInstance();
        params.put("currentYear", String.format("%tY", new Date()));
        params.put("currentMonth", now.get(Calendar.MONTH) + 1);
        params.put("currentDay", now.get(Calendar.DAY_OF_MONTH));

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("identifyApprovalFrom.ftl", "UTF-8");

            String filename = "-鉴定文书审批表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String createDelegateDoc(HttpServletRequest request, Integer consignmentId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("caseXkNo", StringUtils.isBlank(caseInfo.getCaseXkNo()) ? "" : caseInfo.getCaseXkNo());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        params.put("caseBrief", StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief());
        params.put("delegateDate", DateUtils.dateToString(consignment.getDelegateDatetime(), "yyyy年MM月dd日"));
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator1Position", StringUtils.isBlank(consignment.getDelegator1Position()) ? "" : consignment.getDelegator1Position());
        params.put("delegator1Cname", StringUtils.isBlank(consignment.getDelegator1Cname()) ? "" : consignment.getDelegator1Cname());
        params.put("delegator1Cno", StringUtils.isBlank(consignment.getDelegator1Cno()) ? "" : consignment.getDelegator1Cno());
        params.put("delegator1Phone", StringUtils.isBlank(consignment.getDelegator1Phone()) ? "" : consignment.getDelegator1Phone());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("delegator2Position", StringUtils.isBlank(consignment.getDelegator2Position()) ? "" : consignment.getDelegator2Position());
        params.put("delegator2Cname", StringUtils.isBlank(consignment.getDelegator2Cname()) ? "" : consignment.getDelegator2Cname());
        params.put("delegator2Cno", StringUtils.isBlank(consignment.getDelegator2Cno()) ? "" : consignment.getDelegator2Cno());
        params.put("delegator2Phone", StringUtils.isBlank(consignment.getDelegator2Phone()) ? "" : consignment.getDelegator2Phone());
        params.put("postalAddress", StringUtils.isBlank(consignment.getPostalAddress()) ? "" : consignment.getPostalAddress());
        params.put("postNo", StringUtils.isBlank(consignment.getPostNo()) ? "" : consignment.getPostNo());
        params.put("faxNo", StringUtils.isBlank(consignment.getFaxNo()) ? "" : consignment.getFaxNo());
        params.put("identifyKernelName", StringUtils.isBlank(consignment.getIdentifyKernelName()) ? "" : consignment.getIdentifyKernelName());
        params.put("identifyRequirement", StringUtils.isBlank(consignment.getIdentifyRequirement()) ? "" : consignment.getIdentifyRequirement());
        params.put("identifyRequirementOther", StringUtils.isBlank(consignment.getIdentifyRequirementOther()) ? null : consignment.getIdentifyRequirementOther());
        params.put("preIdentifyDesc", StringUtils.isBlank(consignment.getPreIdentifyDesc()) ? "" : consignment.getPreIdentifyDesc());
        params.put("reidentifyReason", StringUtils.isBlank(consignment.getReidentifyReason()) ? "" : consignment.getReidentifyReason());

        boolean bloodFlag = false;
        boolean seminalFlag = false;
        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            sampleInfo.setSamplePacking(StringUtils.isBlank(sampleInfo.getSamplePacking()) ? "" : sampleInfo.getSamplePacking());
            sampleInfo.setSampleProperties(StringUtils.isBlank(sampleInfo.getSampleProperties()) ? "" : sampleInfo.getSampleProperties());
            sampleInfo.setOtherPropertiesDesc(StringUtils.isBlank(sampleInfo.getOtherPropertiesDesc()) ? null : sampleInfo.getOtherPropertiesDesc());
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                bloodFlag = true;
            }
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                seminalFlag = true;
            }
            sampleInfo.setEnterCount(1);
        }

        if (bloodFlag) {
            params.put("bloodFlag", "blood");
        } else {
            params.put("bloodFlag", null);
        }

        if (seminalFlag) {
            params.put("seminalFlag", "seminal");
        } else {
            params.put("seminalFlag", null);
        }
        List<LimsSampleInfo> sampleInfoList1 = new ArrayList<>();
        if (sampleInfoList.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                sampleInfoList1.add(sampleInfoList.get(i));
            }
        } else {
            sampleInfoList1 = sampleInfoList;
        }
        if (sampleInfoList1.size() < 9) {
            int num = 9 - sampleInfoList.size();
            LimsSampleInfo tmpSample = null;
            for (int i = 0; i < num; i++) {
                tmpSample = new LimsSampleInfo();
                tmpSample.setSampleNo("");
                tmpSample.setSampleName("");
                tmpSample.setSamplePacking("");
                tmpSample.setSamplePropertiesName("");
                tmpSample.setEnterCount(null);
                sampleInfoList1.add(tmpSample);
            }
        }

        params.put("sampleList", sampleInfoList);

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("delegateInfo.ftl", "UTF-8");

            String filename = "-委托表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String createGenerateAndDownload(HttpServletRequest request, Integer consignmentId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(consignmentId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }
        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        params.put("caseBrief", StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief());
        params.put("acceptDate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日"));
        params.put("acceptor", StringUtils.isBlank(consignment.getAcceptor()) ? "" : consignment.getAcceptor());
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator1Position", StringUtils.isBlank(consignment.getDelegator1Position()) ? "" : consignment.getDelegator1Position());
        params.put("delegator1Cname", StringUtils.isBlank(consignment.getDelegator1Cname()) ? "" : consignment.getDelegator1Cname());
        params.put("delegator1Cno", StringUtils.isBlank(consignment.getDelegator1Cno()) ? "" : consignment.getDelegator1Cno());
        params.put("delegator1Phone", StringUtils.isBlank(consignment.getDelegator1Phone()) ? "" : consignment.getDelegator1Phone());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("delegator2Position", StringUtils.isBlank(consignment.getDelegator2Position()) ? "" : consignment.getDelegator2Position());
        params.put("delegator2Cname", StringUtils.isBlank(consignment.getDelegator2Cname()) ? "" : consignment.getDelegator2Cname());
        params.put("delegator2Cno", StringUtils.isBlank(consignment.getDelegator2Cno()) ? "" : consignment.getDelegator2Cno());
        params.put("delegator2Phone", StringUtils.isBlank(consignment.getDelegator2Phone()) ? "" : consignment.getDelegator2Phone());
        params.put("postalAddress", StringUtils.isBlank(consignment.getPostalAddress()) ? "" : consignment.getPostalAddress());
        params.put("postNo", StringUtils.isBlank(consignment.getPostNo()) ? "" : consignment.getPostNo());
        params.put("faxNo", StringUtils.isBlank(consignment.getFaxNo()) ? "" : consignment.getFaxNo());
        params.put("identifyKernelName", StringUtils.isBlank(consignment.getIdentifyKernelName()) ? "" : consignment.getIdentifyKernelName());
        params.put("identifyRequirement", StringUtils.isBlank(consignment.getIdentifyRequirement()) ? "" : consignment.getIdentifyRequirement());
        params.put("identifyRequirementOther", StringUtils.isBlank(consignment.getIdentifyRequirementOther()) ? null : consignment.getIdentifyRequirementOther());
        params.put("preIdentifyDesc", StringUtils.isBlank(consignment.getPreIdentifyDesc()) ? "" : consignment.getPreIdentifyDesc());
        params.put("reidentifyReason", StringUtils.isBlank(consignment.getReidentifyReason()) ? "" : consignment.getReidentifyReason());

        boolean bloodFlag = false;
        boolean seminalFlag = false;
        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            sampleInfo.setSamplePacking(StringUtils.isBlank(sampleInfo.getSamplePacking()) ? "" : sampleInfo.getSamplePacking());
            sampleInfo.setSampleProperties(StringUtils.isBlank(sampleInfo.getSampleProperties()) ? "" : sampleInfo.getSampleProperties());
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                bloodFlag = true;
            }
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                seminalFlag = true;
            }
        }

        for (LimsPersonInfo personInfo : personInfoList) {
            personInfo.setPersonName(StringUtils.isBlank(personInfo.getPersonName()) ? "" : personInfo.getPersonName());
            personInfo.setPersonGenderName(StringUtils.isBlank(personInfo.getPersonGenderName()) ? "" : personInfo.getPersonGenderName());
            personInfo.setPersonIdcardNo(StringUtils.isBlank(personInfo.getPersonIdcardNo()) ? "" : personInfo.getPersonIdcardNo());
        }

        if (bloodFlag) {
            params.put("bloodFlag", "blood");
        } else {
            params.put("bloodFlag", null);
        }

        if (seminalFlag) {
            params.put("seminalFlag", "seminal");
        } else {
            params.put("seminalFlag", null);
        }

        if (sampleInfoList.size() < 11) {
            int num = 11 - sampleInfoList.size();
            LimsSampleInfo tmpSample = null;
            for (int i = 0; i < num; i++) {
                tmpSample = new LimsSampleInfo();
                tmpSample.setSampleName("");
                tmpSample.setSampleNo("");
                tmpSample.setSamplePacking("");
                tmpSample.setSamplePropertiesName("");
                sampleInfoList.add(tmpSample);
            }
        }

        params.put("sampleList", sampleInfoList);
        params.put("personList", personInfoList);

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("acceptInfo.ftl", "UTF-8");

            String filename = "-鉴定事项确认书" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String createSampleExchangeDoc(HttpServletRequest request, Integer consignmentId) {
        String filePathName = null;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());

        LimsSampleInfo firstSample = sampleInfoList.get(0);
        LimsSampleInfo lastSample = sampleInfoList.get(sampleInfoList.size() - 1);

        List<String> sampleNoList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                sampleNoList.add(firstSample.getSampleNo() + " — " + lastSample.getSampleNo());
            } else {
                sampleNoList.add("");
            }
        }

        params.put("sampleNoList", sampleNoList);


        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("sampleExchangeRecord.ftl", "UTF-8");

            String filename = "-检材及样本流转记录" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String generateAndDownloadExtractDoc(HttpServletRequest request, Integer extractRecordId) {
        String filePathName = null;

        LimsExtractRecord limsExtractRecord = null;

        if (extractRecordId != null && extractRecordId != 0) {
            limsExtractRecord = limsExtractRecordService.selectById(extractRecordId);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        List<LimsExtractRecordSample> sampleInfoList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());
        if (sampleInfoList.size() <= 12) {
            int num = 12 - sampleInfoList.size();
            for (int i = 0; i < num; i++) {
                LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                tmp.setSampleNo("");
                tmp.setExtractPsa("");
                tmp.setExtractHb("");
                tmp.setExtractMethod("");
                tmp.setExtractLiFlag(Constants.FLAG_FALSE);
                tmp.setExtractYuFlag(Constants.FLAG_FALSE);
                tmp.setExtractZhenFlag(Constants.FLAG_FALSE);
                tmp.setExtractJiaoFlag(Constants.FLAG_FALSE);
                sampleInfoList.add(tmp);
            }

            params.put("extractRecordSampleList1", sampleInfoList);
        } else {
            int sampleLength = sampleInfoList.size();
            int loopCount = (sampleLength % 12 == 0) ? (sampleLength / 12) : (sampleLength / 12 + 1);
            int rows = 12;
            int startIdx = 0;
            List<LimsExtractRecordSample> samplePageList = null;
            for (int i = 1; i <= loopCount; i++) {
                if (rows >= sampleLength) {
                    rows = sampleLength;
                }

                samplePageList = new ArrayList<>();
                samplePageList.addAll(sampleInfoList.subList(startIdx, rows));
                if (i == loopCount && rows < loopCount * 12) {
                    int num = 12 - samplePageList.size();
                    for (int j = 0; j < num; j++) {
                        LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                        tmp.setSampleNo("");
                        tmp.setExtractPsa("");
                        tmp.setExtractHb("");
                        tmp.setExtractMethod("");
                        tmp.setExtractYuFlag(Constants.FLAG_FALSE);
                        tmp.setExtractLiFlag(Constants.FLAG_FALSE);
                        tmp.setExtractZhenFlag(Constants.FLAG_FALSE);
                        tmp.setExtractJiaoFlag(Constants.FLAG_FALSE);
                        samplePageList.add(tmp);
                    }
                }

                params.put("extractRecordSampleList" + i, samplePageList);

                startIdx += 12;
                rows += 12;
            }
        }

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsExtractRecord.getCaseId());
        try {
            String caseNo = caseInfo.getCaseNo();
            if (caseNo == null || caseNo == "") {
                params.put("no", "");
                params.put("year", "");
            } else {
                if (caseNo.contains("-")) {
                    if (caseNo.split("-").length > 2) {
                        params.put("year", (caseNo.split("-")[1]));
                        params.put("no", (caseNo.split("-")[2]));
                    } else {
                        params.put("no", (caseNo.split("-")[1]));
                        params.put("year", (caseNo.split("-")[0]));
                    }
                } else {
                    params.put("no", caseNo.substring(4, caseNo.length()));
                    params.put("year", caseNo.substring(0, 4));
                }
            }
        } catch (Exception ex) {
            logger.error("获取提取任务号错误", ex);
            params.put("no", "");
            params.put("year", "");
        }

        params.put("extractDatetime", limsExtractRecord.getExtractDatetime() == null ? "" : DateUtils.dateToString(limsExtractRecord.getExtractDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("extractPerson",
                (StringUtils.isBlank(limsExtractRecord.getExtractPersonName1()) ? "" : limsExtractRecord.getExtractPersonName1())
                        + "  " + (StringUtils.isBlank(limsExtractRecord.getExtractPersonName2()) ? "" : limsExtractRecord.getExtractPersonName2())
        );

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("extractRecord.ftl", "UTF-8");

            String filename = "-提取记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String generateAndDownloadPcrDoc(HttpServletRequest request, Integer pcrRecordId) {
        String filePathName = null;

        LimsPcrRecord limsPcrRecord = null;

        if (pcrRecordId != null && pcrRecordId != 0) {
            limsPcrRecord = limsPcrRecordService.selectById(pcrRecordId);
        }

        List<LimsPcrRecordSample> sampleInfoList = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsPcrRecord.getCaseId());
        String caseNo = caseInfo.getCaseNo();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            if (caseNo == null || caseNo == "") {
                params.put("no", "");
                params.put("year", "");
            } else {
                if (caseNo.contains("-")) {
                    if (caseNo.split("-").length > 2) {
                        params.put("no", (caseNo.split("-")[2]));
                        params.put("year", (caseNo.split("-")[1]));
                    } else {
                        params.put("no", (caseNo.split("-")[1]));
                        params.put("year", (caseNo.split("-")[0]));
                    }
                } else {
                    params.put("year", caseNo.substring(0, 4));
                    params.put("no", caseNo.substring(4, caseNo.length()));
                }
            }
        } catch (Exception ex) {
            logger.error("获取扩增任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        params.put("pcrProgram", "其他");
        List<DictItem> pcrProgramList = null;
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrProgram())) {
            pcrProgramList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_PROGRAM_NO);
            for (DictItem di : pcrProgramList) {
                if (di.getDictCode().equals(limsPcrRecord.getPcrProgram())) {
                    params.put("pcrProgram", di.getDictName());
                    break;
                }
            }
        }
        params.put("pcrInstrument", "其他");
//        List<DictItem> pcrInstrumentList = null;
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrInstrument())) {
//            pcrInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_INSTRUMENT);
            List<EquipmentNameInfo> pcrInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_KZY);
            for (EquipmentNameInfo ei : pcrInstrumentList) {
                if (ei.getEquipmentNo().equals(limsPcrRecord.getPcrInstrument())) {
                    params.put("pcrInstrument", ei.getEquipmentName());
                    break;
                }
            }
        }

        params.put("pcrReagent", "其他");
        List<DictItem> pcrReagentKitList = null;
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrReagent())) {
            pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
            for (DictItem di : pcrReagentKitList) {
                if (di.getDictCode().equals(limsPcrRecord.getPcrReagent())) {
                    params.put("pcrReagent", di.getDictName());
                    break;
                }
            }
        }

        params.put("pcrSystem", "其他");
        List<DictItem> pcrSystemList = null;
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrSystem())) {
            pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
            for (DictItem di : pcrSystemList) {
                if (di.getDictCode().equals(limsPcrRecord.getPcrSystem())) {
                    params.put("pcrSystem", di.getDictName());
                    break;
                }
            }
        }

        params.put("pcrStartDatetime", limsPcrRecord.getPcrStartDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrStartDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrEndDatetime", limsPcrRecord.getPcrEndDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrEndDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrPerson", ((StringUtils.isBlank(limsPcrRecord.getPcrPersonName1()) ? "" : limsPcrRecord.getPcrPersonName1()))
                + "  " + (StringUtils.isBlank(limsPcrRecord.getPcrPersonName2()) ? "" : limsPcrRecord.getPcrPersonName2())
        );

        if (sampleInfoList.size() <= 18) {
            int num = 18 - sampleInfoList.size();
            for (int i = 0; i < num; i++) {
                LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                tmp.setSampleNo("");
                tmp.setBufferUl("");
                tmp.setPrimerUl("");
                tmp.setTemplateUl("");
                tmp.setDdwUl("");
                sampleInfoList.add(tmp);
            }

            params.put("pcrRecordSampleList1", sampleInfoList);
        } else {
            int sampleLength = sampleInfoList.size();
            int loopCount = (sampleLength % 18 == 0) ? (sampleLength / 18) : (sampleLength / 18 + 1);
            int rows = 18;
            int startIdx = 0;
            List<LimsPcrRecordSample> samplePageList = null;
            for (int i = 1; i <= loopCount; i++) {
                if (rows >= sampleLength) {
                    rows = sampleLength;
                }

                samplePageList = new ArrayList<>();
                samplePageList.addAll(sampleInfoList.subList(startIdx, rows));
                if (i == loopCount && rows < loopCount * 12) {
                    int num = 18 - samplePageList.size();
                    for (int j = 0; j < num; j++) {
                        LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                        tmp.setSampleNo("");
                        tmp.setBufferUl("");
                        tmp.setPrimerUl("");
                        tmp.setDdwUl("");
                        tmp.setTemplateUl("");
                        samplePageList.add(tmp);
                    }
                }

                params.put("pcrRecordSampleList" + i, samplePageList);

                startIdx += 18;
                rows += 18;
            }
        }

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("pcrRecord.ftl", "UTF-8");

            String filename = "-扩增记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String downloadSytable(HttpServletResponse response, Integer syRecordId) {
        String filePathName = null;

        LimsSyRecord limsSyRecord = null;

        if (syRecordId != null && syRecordId != 0) {
            limsSyRecord = limsSyRecordService.selectById(syRecordId);
        }

        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());

        StringBuffer buffer = new StringBuffer();
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            String filename = "-上样表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".txt";

            buffer.append("Container Name" + "\t" + "Plate ID" + "\t" + "Description" + "\t" + "ContainerType" + "\t" + "AppType" + "\t" + "Owner" + "\t" + "Operator" + "\t" + "PlateSealing" + "\t" + "SchedulingPref" + "\n");
            buffer.append((StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + (StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + "" + "\t" + "96-Well" + "\t" + "Regular" + "\t" + "1" + "\t" + "1" + "\t" + "Septa" + "\t" + "1234" + "\n");
            buffer.append("AppServer" + "\t" + "AppInstance" + "\n");
            buffer.append("GeneMapper" + "\t" + "GeneMapper_Generic_Instance" + "\n");

            buffer.append("Well" + "\t" + "Sample Name" + "\t" + "Comment" + "\t" + "Size Standard" + "\t" + "Snp Set" + "\t" + "User-Defined 3" + "\t"
                    + "User-Defined 2" + "\t" + "User-Defined 1" + "\t" + "Panel" + "\t" + "Study" + "\t" + "Sample Type" + "\t" + "Analysis Method"
                    + "\t" + "Results Group 1" + "\t" + "Instrument Protocol 1" + "\n");

            for (String positionStr : Constants.SYTABLE_POSTION_ARR_VER) {
                for (LimsSyRecordSample boardSampleMap : sampleInfoList) {
                    if (positionStr.equals(boardSampleMap.getSamplePosition())) {
                        buffer.append(boardSampleMap.getSamplePosition() + "\t"
                                + boardSampleMap.getSampleNo() + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "DNA_Results_Group" + "\t"
                                + "DNATyper-G5-Run" + "\n");
                        break;
                    }
                }
            }
            fileWriter = new FileWriter(getGeneratePath(caseInfo, filename));
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(buffer.toString());

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("生成上样表错误！", ex);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (Exception ex) {
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (Exception ex) {
                }
            }
        }

        return filePathName;
    }

    private String generateAndDownloadSyDoc(HttpServletRequest request, Integer syRecordId) {
        String filePathName = null;

        LimsSyRecord limsSyRecord = null;

        if (syRecordId != null && syRecordId != 0) {
            limsSyRecord = limsSyRecordService.selectById(syRecordId);
        }

        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        try {
            if (caseNo == null || caseNo == "") {
                params.put("year", "");
                params.put("no", "");
            } else {
                if (caseNo.contains("-")) {
                    if (caseNo.split("-").length > 2) {
                        params.put("no", (caseNo.split("-")[2]));
                        params.put("year", (caseNo.split("-")[1]));
                    } else {
                        params.put("year", (caseNo.split("-")[0]));
                        params.put("no", (caseNo.split("-")[1]));
                    }
                } else {
                    params.put("year", caseNo.substring(0, 4));
                    params.put("no", caseNo.substring(4, caseNo.length()));
                }
            }
        } catch (Exception ex) {
            logger.error("获取上样任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }


        params.put("chanwuFlag", "");
        List<DictItem> chanwuList = null;
        if (StringUtils.isNotBlank(limsSyRecord.getChanwuFlag())) {
            chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
            for (DictItem di : chanwuList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("chanwuFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("jiaxiananFlag", "");
        List<DictItem> jiaxiananList = null;
        if (StringUtils.isNotBlank(limsSyRecord.getJiaxiananFlag())) {
            jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
            for (DictItem di : jiaxiananList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("jiaxiananFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoUlFlag", "");
        List<DictItem> neibiaoulList = null;
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoUlFlag())) {
            neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
            for (DictItem di : neibiaoulList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoUlFlag())) {
                    params.put("neibiaoUlFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoFlag", "");
        List<DictItem> neibiaoList = null;
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoFlag())) {
            neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
            for (DictItem di : neibiaoList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoFlag())) {
                    params.put("neibiaoFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("elecInstrument", "其他");
//        List<DictItem> elecInstrumentList = null;
        if (StringUtils.isNotBlank(limsSyRecord.getElecInstrument())) {
//            elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
            List<EquipmentNameInfo> elecInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_DYY);
            for (EquipmentNameInfo ei : elecInstrumentList) {
                if (ei.getEquipmentNo().equals(limsSyRecord.getElecInstrument())) {
                    params.put("elecInstrument", ei.getEquipmentName());
                    break;
                }
            }
        }

        params.put("startDateteime", limsSyRecord.getStartDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getStartDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("endDatetime", limsSyRecord.getEndDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getEndDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("operatePersonName",
                (StringUtils.isBlank(limsSyRecord.getOperatePersonName1()) ? "" : limsSyRecord.getOperatePersonName1())
                        + "、" + (StringUtils.isBlank(limsSyRecord.getOperatePersonName2()) ? "" : limsSyRecord.getOperatePersonName2())
        );

        for (String pos : Constants.SYTABLE_POSTION_ARR) {
            params.put(pos, "");
            for (LimsSyRecordSample lsrs : sampleInfoList) {
                if (lsrs != null) {
                    if (StringUtils.isNotBlank(lsrs.getSamplePosition())
                            && pos.equals(lsrs.getSamplePosition().trim().toUpperCase())) {
                        params.put(pos, lsrs.getSampleNo());
                        break;
                    }
                }
            }
        }

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("syRecord.ftl", "UTF-8");

            String filename = "-上样记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private String generateReportIdentifyBook(HttpServletRequest request, Integer caseId) {
        String filePathName = null;

        LimsIdentifyBook identifyBook = limsIdentifyBookService.selectByCaseId(caseId);

        if (StringUtils.isNotBlank(identifyBook.getFilePath()) && StringUtils.isNotBlank(identifyBook.getUploadPath())) {
            File uploadFile = new File(identifyBook.getUploadPath());
            File downFile = new File(identifyBook.getFilePath());
            long uploadDate = uploadFile.lastModified();
            long downDate = downFile.lastModified();

            if (downDate > uploadDate) {
                filePathName = identifyBook.getFilePath();
            } else if (uploadDate > downDate) {
                filePathName = identifyBook.getUploadPath();
            } else {
                filePathName = identifyBook.getUploadPath();
            }
        } else if (StringUtils.isNotBlank(identifyBook.getFilePath()) && StringUtils.isBlank(identifyBook.getUploadPath())) {
            filePathName = identifyBook.getFilePath();
        } else if (StringUtils.isBlank(identifyBook.getFilePath()) && StringUtils.isNotBlank(identifyBook.getUploadPath())) {
            filePathName = identifyBook.getUploadPath();
        }

        return filePathName;
    }

    private String getNumberOfSample(String sampleNo) {
        String newSampleNo = null;
        if (sampleNo.contains("-")) {
            newSampleNo = sampleNo.substring(sampleNo.lastIndexOf("-") + 1);
        } else {
            newSampleNo = sampleNo;
        }

        return newSampleNo;
    }

    private String createReceiveFrom(HttpServletRequest request, LimsCaseInfo caseInfo) {
        String filePathName = null;

        Map<String, Object> params = new HashMap<String, Object>();
        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("printReceive.ftl", "UTF-8");

            String filename = "-领取单" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        }

        return filePathName;

    }

    @RequestMapping("/downloadSampleExcel.html")
    public void downloadSampleExcel(HttpServletRequest request, HttpServletResponse response, Integer caseId) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);
        List<LimsSampleInfo> sampleInfos = limsSampleInfoService.selectListByCaseId(caseInfo.getId());

        Map<String, Object> params = new HashMap<String, Object>();
        String templateFilePath = request.getServletContext().getRealPath("templates/sampleExcel.xls");
        HSSFWorkbook workbook = null;
        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 1;
            int idx = 0;
            for (LimsSampleInfo sample : sampleInfos) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(sample.getSampleNo());

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(caseInfo.getCaseName());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getDelegateOrgDesc());
                    }
                }

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(sample.getSampleName());

                //受理人
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getAcceptor());
                    }
                }

                //送检人
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getDelegator1() + "、" + consignment.getDelegator2());
                    }
                }


                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(sampleInfos.size());


                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        cell.setCellValue(formatter.format(consignment.getAcceptDatetime()));
                    }
                }


                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(consignment.getCaseId());
                        cell.setCellValue(limsCaseInfo.getCaseNo());
                    }
                }


                idx++;
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + URLEncoder.encode("_条形码列表.xls", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            logger.error("导出Excel错误！", ex);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ex) {
                }
            }
        }

    }

    //下载补送的检材条码
    @RequestMapping("/repairDownloadSampleExcel.html")
    public void repairDownloadSampleExcel(HttpServletRequest request, HttpServletResponse response, Integer caseId) {
        List<LimsSampleInfo> sampleInfos = new ArrayList<>();
                LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);

        for (LimsConsignment consignment : consignmentList) {
            if(consignment.getAdditionalFlag().equals("1")){
                sampleInfos = limsSampleInfoService.selectListByConsignmentId(consignment.getId());
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        String templateFilePath = request.getServletContext().getRealPath("templates/sampleExcel.xls");
        HSSFWorkbook workbook = null;
        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 1;
            int idx = 0;
            for (LimsSampleInfo sample : sampleInfos) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(sample.getSampleNo());

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(caseInfo.getCaseName());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getDelegateOrgDesc());
                    }
                }

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(sample.getSampleName());

                //受理人
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getAcceptor());
                    }
                }

                //送检人
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        cell.setCellValue(consignment.getDelegator1() + "、" + consignment.getDelegator2());
                    }
                }


                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(sampleInfos.size());


                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        cell.setCellValue(formatter.format(consignment.getAcceptDatetime()));
                    }
                }


                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                for (LimsConsignment consignment : consignmentList) {
                    if (sample.getConsignmentId().equals(consignment.getId())) {
                        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(consignment.getCaseId());
                        cell.setCellValue(limsCaseInfo.getCaseNo());
                    }
                }


                idx++;
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + URLEncoder.encode("_条形码列表.xls", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            logger.error("导出Excel错误！", ex);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ex) {
                }
            }
        }

    }

    private String getGeneratePath(LimsCaseInfo caseInfo, String filename) {
        String writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
        writeFilePath = makePathFile(writeFilePath);

        String writeFileName = (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + filename;

        String writeFile = "";
        if (writeFilePath.endsWith("\\") || writeFilePath.endsWith("/")) {
            writeFile += writeFilePath + writeFileName;
        } else {
            writeFile = writeFilePath + System.getProperty("file.separator") + writeFileName;
        }
        return writeFile;
    }

    /**
     * 验证目录是否存在，如果不存在，则创建对应目录。
     *
     * @param filePathName
     */
    private static String makePathFile(String filePathName) {
        File pathFile = new File(filePathName);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        return pathFile + "\\\\";
    }


    /**
     * 把接受的全部文件打成压缩包
     */
    public static void zipFiles(List files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())) {
            caseInfoVO.getEntity().setCaseName(null);
        } else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())) {
            caseInfoVO.getEntity().setCaseXkNo(null);
        } else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseStatus())) {
            caseInfoVO.getEntity().setCaseStatus(null);
        } else {
            caseInfoVO.getEntity().setCaseStatus(caseInfoVO.getEntity().getCaseStatus());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseType())) {
            caseInfoVO.getEntity().setCaseType(null);
        } else {
            caseInfoVO.getEntity().setCaseType(caseInfoVO.getEntity().getCaseType());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())) {
            caseInfoVO.getEntity().setCaseProperty(null);
        } else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (caseInfoVO.getAcceptDateStart() == null) {
            caseInfoVO.setAcceptDateStart(null);
        } else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getAcceptDateEnd() == null) {
            caseInfoVO.setAcceptDateEnd(null);
        } else {
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        if (caseInfoVO.getDelegateDatetimeStart() == null) {
            caseInfoVO.setDelegateDatetimeStart(null);
        } else {
            caseInfoVO.setDelegateDatetimeStart(caseInfoVO.getDelegateDatetimeStart());
        }

        if (caseInfoVO.getDelegateDatetimeEnd() == null) {
            caseInfoVO.setDelegateDatetimeEnd(null);
        } else {
            oldTime = sdf.format(caseInfoVO.getDelegateDatetimeEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setDelegateDatetimeEnd(newTime);
        }

        return caseInfoVO;
    }

}
