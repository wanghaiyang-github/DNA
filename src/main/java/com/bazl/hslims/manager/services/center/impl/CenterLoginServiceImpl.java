package com.bazl.hslims.manager.services.center.impl;

import com.bazl.hslims.manager.dao.LabRolePermissionDao;
import com.bazl.hslims.manager.dao.LabUserDao;
import com.bazl.hslims.manager.dao.LabUserRoleDao;
import com.bazl.hslims.manager.model.po.LabRolePermission;
import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.model.po.LabUserRole;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import com.bazl.hslims.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2016/12/28.
 */
@Service
public class CenterLoginServiceImpl implements CenterLoginService {

    @Autowired
    LabUserDao labUserDao;

    @Autowired
    LabUserRoleDao labUserRoleDao;

    @Autowired
    LabRolePermissionDao labRolePermissionDao;


    public LabUser selectByLoginName(String loginName) {
        return labUserDao.selectByLoginName(loginName);
    }

    public Set<String> selectRoleIdsByUserId(Integer userId) {
        Set<String> result = null;

        List<LabUserRole> list = labUserRoleDao.selectListByUserId(userId);
        if(ListUtils.isNotNullAndEmptyList(list)) {
            result = new HashSet<String>();
            for (LabUserRole lur : list) {
                result.add(lur.getUserId().toString());
            }
        }

        return result;
    }

    public Set<String> selectPermissionIdsByUserId(Integer userId) {
        Set<String> result = null;

        List<LabRolePermission> list = labRolePermissionDao.selectListByUserId(userId);
        if(ListUtils.isNotNullAndEmptyList(list)) {
            result = new HashSet<String>();
            for (LabRolePermission lur : list) {
                result.add(lur.getPermissionId().toString());
            }
        }

        return result;
    }

}
