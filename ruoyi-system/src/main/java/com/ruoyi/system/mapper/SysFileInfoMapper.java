package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysFileInfo;

/**
 * 文件信息 数据层
 * 
 * @author ruoyi
 */
public interface SysFileInfoMapper
{
    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<SysFileInfo> selectFileInfoList(SysFileInfo sysFileInfo);

    /**
     * 通过文件ID查询文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
    public SysFileInfo selectFileInfoById(Long fileId);

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int insertFileInfo(SysFileInfo sysFileInfo);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    public int deleteFileInfoById(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileInfoByIds(Long[] fileIds);
}
