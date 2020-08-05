package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.dao.LabRoleDao;
import com.bazl.hslims.manager.dao.LabRolePermissionDao;
import com.bazl.hslims.manager.model.po.LabRole;
import com.bazl.hslims.manager.model.po.LabRolePermission;
import com.bazl.hslims.manager.services.common.LabRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
@Service
public class LabRolePermissionServiceImpl implements LabRolePermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LabRolePermissionDao labRolePermissionDao;
    @Autowired
    LabRoleDao labRoleDao;

    public int add(LabRolePermission labRolePermission) {

        try {
            LabRole labRole = new LabRole();
            labRole.setRoleName(labRolePermission.getRoleName());
            labRole.setRoleLevel(0);

            labRoleDao.insert(labRole);

            List<LabRolePermission> labRolePermissionList = labRolePermission.getLabRolePermissionList();
            for (LabRolePermission lrp : labRolePermissionList) {
                labRolePermission.setRoleId(labRole.getId());
                labRolePermission.setPermissionId(lrp.getPermissionId());

                labRolePermissionDao.insert(labRolePermission);
            }

            return 1;
        }catch (Exception e) {
            logger.error("保存失败！", e);
            return 0;
        }
    }

    public int update(LabRolePermission labRolePermission) {

        try {
            labRolePermissionDao.deleteByRoleId(labRolePermission.getRoleId());
            List<LabRolePermission> labRolePermissionList = labRolePermission.getLabRolePermissionList();
            for (LabRolePermission lrp : labRolePermissionList) {
                labRolePermission.setPermissionId(lrp.getPermissionId());

                labRolePermissionDao.insert(labRolePermission);
            }

            return 1;
        }catch (Exception e) {
            logger.error("保存失败！", e);
            return 0;
        }
    }
}
