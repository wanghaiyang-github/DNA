package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LabUserDao;
import com.bazl.hslims.manager.dao.LabUserRoleDao;
import com.bazl.hslims.manager.model.po.LabRole;
import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.model.po.LabUserRole;
import com.bazl.hslims.manager.services.common.LabUserService;
import com.bazl.hslims.utils.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
@Service
public class LabUserServiceImpl implements LabUserService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    LabUserDao labUserDao;
    @Autowired
    LabUserRoleDao labUserRoleDao;

    public LabUser selectById(Integer labUserId) {
        return labUserDao.selectById(labUserId);
    }

    public int deleteById(Integer labUserId) {
        try {
            return labUserDao.deleteById(labUserId);
        }catch(Exception ex){
            logger.error("删除用户错误！", ex);
            return 0;
        }
    }


    public int add(LabUser labUser) {
        try {
            labUser.setLoginPassword(EncryptUtils.encryptMD5(labUser.getLoginPassword()));
            labUserDao.insert(labUser);

            List<LabRole> userRoleList = labUser.getLabRoleList();
            List<LabUserRole> labUserRoleList = new ArrayList<LabUserRole>();
            LabUserRole lur = null;
            for(LabRole lr : userRoleList){
                lur = new LabUserRole();
                lur.setRoleId(lr.getId());
                lur.setUserId(labUser.getId());
                labUserRoleList.add(lur);

                labUserRoleDao.insert(lur);
            }

            return 1;
        }catch(Exception ex){
            logger.error("新增用户错误！", ex);
            return 0;
        }
    }

    public int update(LabUser labUser) {
        try {
            LabUser user = labUserDao.selectById(labUser.getId());
            if (!user.getLoginPassword().equals(labUser.getLoginPassword()))
                labUser.setLoginPassword(EncryptUtils.encryptMD5(labUser.getLoginPassword()));

            labUserDao.update(labUser);

            labUserRoleDao.deleteByUserId(labUser.getId());
            List<LabRole> userRoleList = labUser.getLabRoleList();
            List<LabUserRole> labUserRoleList = new ArrayList<LabUserRole>();
            LabUserRole lur = null;
            for(LabRole lr : userRoleList){
                lur = new LabUserRole();
                lur.setRoleId(lr.getId());
                lur.setUserId(labUser.getId());
                labUserRoleList.add(lur);

                labUserRoleDao.insert(lur);
            }

            return 1;
        }catch(Exception ex){
            logger.error("更新用户错误！", ex);
            return 0;
        }
    }


    public int updatePassword(LabUser labUser) {
        try {
            labUserDao.update(labUser);
            return 1;
        }catch(Exception ex){
            logger.error("更新用户密码错误！", ex);
            return 0;
        }
    }

    public List<LabUser> selectAll() {
        return labUserDao.selectList(null);
    }

}
