package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.file.FileTypeUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.service.ISysFileInfoService;

/**
 * 文件管理 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/file")
public class SysFileController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    private String prefix = "system/file";

    @Autowired
    private ISysFileInfoService sysFileInfoService;

    @RequiresPermissions("system:file:view")
    @GetMapping()
    public String file()
    {
        return prefix + "/file";
    }

    /**
     * 查询文件信息列表
     */
    @RequiresPermissions("system:file:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysFileInfo sysFileInfo)
    {
        startPage();
        List<SysFileInfo> list = sysFileInfoService.selectFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 上传文件弹窗
     */
    @GetMapping("/upload")
    public String upload()
    {
        return prefix + "/upload";
    }

    /**
     * 文件上传处理（支持多文件）
     */
    @RequiresPermissions("system:file:upload")
    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadSave(@RequestParam("file") MultipartFile[] files,
                                  @RequestParam(value = "category", required = false) String category,
                                  @RequestParam(value = "remark", required = false) String remark)
    {
        try
        {
            for (MultipartFile file : files)
            {
                // 上传文件并获取存储路径（如 /profile/upload/2026/04/19/xxx.pdf）
                String filePath = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);
                // 构建文件信息
                SysFileInfo sysFileInfo = new SysFileInfo();
                sysFileInfo.setFileName(file.getOriginalFilename());
                sysFileInfo.setFilePath(filePath);
                sysFileInfo.setFileSize(file.getSize());
                sysFileInfo.setFileType(FileTypeUtils.getFileType(file.getOriginalFilename()));
                sysFileInfo.setCategory(category);
                sysFileInfo.setRemark(remark);
                sysFileInfo.setCreateBy(ShiroUtils.getLoginName());
                sysFileInfoService.insertFileInfo(sysFileInfo);
            }
            return success();
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 文件下载
     */
    @RequiresPermissions("system:file:download")
    @GetMapping("/download/{fileId}")
    public void download(@PathVariable("fileId") Long fileId, HttpServletResponse response)
    {
        try
        {
            SysFileInfo sysFileInfo = sysFileInfoService.selectFileInfoById(fileId);
            // filePath 格式为 /profile/upload/2026/04/19/xxx.pdf，需要去掉 /profile 前缀后拼接实际物理路径
            String downloadPath = RuoYiConfig.getProfile() + FileUtils.stripPrefix(sysFileInfo.getFilePath());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, sysFileInfo.getFileName());
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 删除文件
     */
    @RequiresPermissions("system:file:remove")
    @Log(title = "文件管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        Long[] fileIds = Convert.toLongArray(ids);
        return toAjax(sysFileInfoService.deleteFileInfoByIds(fileIds));
    }

    /**
     * 导出文件信息
     */
    @RequiresPermissions("system:file:export")
    @Log(title = "文件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysFileInfo sysFileInfo)
    {
        List<SysFileInfo> list = sysFileInfoService.selectFileInfoList(sysFileInfo);
        ExcelUtil<SysFileInfo> util = new ExcelUtil<SysFileInfo>(SysFileInfo.class);
        return util.exportExcel(list, "文件数据");
    }
}
