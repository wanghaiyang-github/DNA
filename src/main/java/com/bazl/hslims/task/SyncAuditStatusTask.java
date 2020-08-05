package com.bazl.hslims.task;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.RedisCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/12.
 */
public class SyncAuditStatusTask implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LimsSampleGeneService limsSampleGeneService;
    @Autowired
    RedisCacheUtil redisCacheUtil;

    @Override
    public void run() {

        log.info("开始检索并同步数据....");

        doSync();
        log.info("完成同步数据....");

        Constants.SYNC_DNA36_TASK_STARTED = false;
    }

    private boolean doSync() {
        try {
            syncByCaseId();
        } catch(Exception e) {
            log.error("向redis同步数据完成错误！", e);
            return false;
        }
        log.info("向redis同步数据完成！");
        return true;
    }

    private boolean syncByCaseId() {

        try {
            List<LimsSampleGene> limsSampleGenes = limsSampleGeneService.selectAuditList();
            //从redis中取得gene信息
            Map<Object, Object> sampleGeneMap = redisCacheUtil.hgetAll("sample_gene");
            for (LimsSampleGene sampleGene:limsSampleGenes) {
                //向redis插入数据
                redisCacheUtil.hset("sample_gene",String.valueOf(sampleGene.getSampleId()), sampleGene.getGeneInfo());
            }
        } catch(Exception ex) {
            log.error("向redis同步数据完成错误！", ex);
            return false;
        }
        return false;
    }


}
