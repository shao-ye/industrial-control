package com.ruoyi.industry.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.industry.domain.IndRefreshConfig;
import com.ruoyi.industry.service.IIndRefreshConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 刷新配置Controller
 * 
 * @author shao
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/industry/refreshConfig")
public class IndRefreshConfigController extends BaseController
{
    @Autowired
    private IIndRefreshConfigService indRefreshConfigService;

    /**
     * 查询刷新配置列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(IndRefreshConfig indRefreshConfig)
    {
        startPage();
        List<IndRefreshConfig> list = indRefreshConfigService.selectIndRefreshConfigList(indRefreshConfig);
        return getDataTable(list);
    }

    /**
     * 导出刷新配置列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:export')")
    @Log(title = "刷新配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IndRefreshConfig indRefreshConfig)
    {
        List<IndRefreshConfig> list = indRefreshConfigService.selectIndRefreshConfigList(indRefreshConfig);
        ExcelUtil<IndRefreshConfig> util = new ExcelUtil<IndRefreshConfig>(IndRefreshConfig.class);
        util.exportExcel(response, list, "刷新配置数据");
    }

    /**
     * 获取刷新配置详细信息
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(indRefreshConfigService.selectIndRefreshConfigById(id));
    }

    /**
     * 查询当前用户配置信息
     * @return 刷新配置
     */
    @GetMapping("/user")
    public AjaxResult userConfig()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        long userId = user.getUserId();
        return AjaxResult.success(indRefreshConfigService.selectIndRefreshConfigByUserId(userId));
    }

    /**
     * 新增刷新配置
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:add')")
    @Log(title = "刷新配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndRefreshConfig indRefreshConfig)
    {
        return toAjax(indRefreshConfigService.insertIndRefreshConfig(indRefreshConfig));
    }

    /**
     * 修改刷新配置
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:edit')")
    @Log(title = "刷新配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody IndRefreshConfig indRefreshConfig)
    {
        return toAjax(indRefreshConfigService.updateIndRefreshConfig(indRefreshConfig));
    }

    /**
     * 删除刷新配置
     */
//    @PreAuthorize("@ss.hasPermi('industry:refreshConfig:remove')")
    @Log(title = "刷新配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(indRefreshConfigService.deleteIndRefreshConfigByIds(ids));
    }
}
