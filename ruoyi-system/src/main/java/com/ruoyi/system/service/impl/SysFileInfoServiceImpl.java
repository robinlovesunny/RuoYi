package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import com.ruoyi.system.service.ISysFileInfoService;

/**
 * 文件信息 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService
{
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息集合
     */
    @Override
    public List<SysFileInfo> selectFileInfoList(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.selectFileInfoList(sysFileInfo);
    }

    /**
     * 通过文件ID查询文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
    @Override
    public SysFileInfo selectFileInfoById(Long fileId)
    {
        return sysFileInfoMapper.selectFileInfoById(fileId);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.insertFileInfo(sysFileInfo);
    }

    /**
     * 删除文件信息
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    @Override
    public int deleteFileInfoById(Long fileId)
    {
        SysFileInfo sysFileInfo = sysFileInfoMapper.selectFileInfoById(fileId);
        int rows = sysFileInfoMapper.deleteFileInfoById(fileId);
        if (rows > 0 && sysFileInfo != null)
        {
            FileUtils.deleteFile(RuoYiConfig.getProfile() + FileUtils.stripPrefix(sysFileInfo.getFilePath()));
        }
        return rows;
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileInfoByIds(Long[] fileIds)
    {
        for (Long fileId : fileIds)
        {
            SysFileInfo sysFileInfo = sysFileInfoMapper.selectFileInfoById(fileId);
            if (sysFileInfo != null)
            {
                FileUtils.deleteFile(RuoYiConfig.getProfile() + FileUtils.stripPrefix(sysFileInfo.getFilePath()));
            }
        }
        return sysFileInfoMapper.deleteFileInfoByIds(fileIds);
    }
}
