package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.LimsSampleGeneDao;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.vo.LimsSampleGeneVO;
import com.bazl.hslims.manager.services.common.LimsSampleGeneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
@Service
public class LimsSampleGeneServiceImpl implements LimsSampleGeneService {
    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LimsSampleGeneDao limsSampleGeneDao;

    @Override
    @Transactional
    public int insert(LimsSampleGene limsSampleGene) {
        try {
            return limsSampleGeneDao.insert(limsSampleGene);
        } catch(Exception ex) {
            logger.error("LimsSampleGeneService.insert error!", ex);
            return 0;
        }
    }

    @Override
    public int update(LimsSampleGene limsSampleGene) {
        try{
            return limsSampleGeneDao.update(limsSampleGene);
        }catch (Exception e){
            logger.error("LimsSampleGeneService.update error!",e);
            return 0;
        }
    }

    @Override
    public List<LimsSampleGene> selectAuditList() {
        return limsSampleGeneDao.selectAuditList();
    }

    @Override
    public List<LimsSampleGene> selectListBySampleId(Integer sampleId) {
        return limsSampleGeneDao.selectListBySampleId(sampleId);
    }

    @Override
    public LimsSampleGene selectAuditStatusBySampleId(Integer sampleId) {
        return limsSampleGeneDao.selectAuditStatusBySampleId(sampleId);
    }

    @Override
    public List<LimsSampleGene> selectListByGeneId(Integer id) {
        return limsSampleGeneDao.selectListByGeneId(id);
    }

    @Override
    public List<LimsSampleGene> selectListGeneId(Integer id) {
        return limsSampleGeneDao.selectListGeneId(id);
    }

    @Override
    public List<LimsSampleGene> selectListByCaseId(Integer caseId) {
        return limsSampleGeneDao.selectListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleGene> selectMixListByCaseId(Integer caseId) {
        return limsSampleGeneDao.selectMixListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleGene> selectAuditedListByCaseId(Integer caseId) {
        return limsSampleGeneDao.selectAuditedListByCaseId(caseId);
    }

    @Override
    public LimsSampleGene selectAuditedBySampleId(Integer sampleId) {
        return limsSampleGeneDao.selectAuditedBySampleId(sampleId);
    }

    @Override
    public List<LimsSampleGene> selectListByCriminalPersonId(Integer sampleId) {
        return limsSampleGeneDao.selectListByCriminalPersonId(sampleId);
    }


    @Override
    public int selectVOCount(LimsSampleGeneVO query) {
        return limsSampleGeneDao.selectVOCount(query);
    }

    @Override
    public List<LimsSampleGeneVO> selectVOPaginationList(LimsSampleGeneVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsSampleGeneDao.selectVOPaginationList(query);
    }

    @Override
    public List<LimsSampleGene> selectGeneInfoList(LimsSampleGene sampleGene){
        return limsSampleGeneDao.selectGeneInfoList(sampleGene);
    }

    @Override
    public int updateBySampleId(Integer sampleId) {
        return limsSampleGeneDao.updateBySampleId(sampleId);
    }

    @Override
    public int updateById(LimsSampleGene limsSampleGene) {
        return limsSampleGeneDao.updateById(limsSampleGene);
    }

    @Override
    public List<LimsSampleGene> selectListByCriminalId(Integer sampleId) {
        return limsSampleGeneDao.selectListByCriminalId(sampleId);
    }


}
