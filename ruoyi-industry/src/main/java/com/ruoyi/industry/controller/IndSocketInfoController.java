package com.ruoyi.industry.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.industry.domain.IndSocketInfo;
import com.ruoyi.industry.service.IIndSocketInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * socket管理Controller
 * 
 * @author shao
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/industry/socketInfo")
public class IndSocketInfoController extends BaseController
{
    @Autowired
    private IIndSocketInfoService indSocketInfoService;

    /**
     * 查询socket管理列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(IndSocketInfo indSocketInfo)
    {
        startPage();
        List<IndSocketInfo> list = indSocketInfoService.selectIndSocketInfoList(indSocketInfo);
        return getDataTable(list);
    }

    /**
     * 导出socket管理列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:export')")
    @Log(title = "socket管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IndSocketInfo indSocketInfo)
    {
        List<IndSocketInfo> list = indSocketInfoService.selectIndSocketInfoList(indSocketInfo);
        ExcelUtil<IndSocketInfo> util = new ExcelUtil<IndSocketInfo>(IndSocketInfo.class);
        util.exportExcel(response, list, "socket管理数据");
    }

    /**
     * 获取socket管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(indSocketInfoService.selectIndSocketInfoById(id));
    }

    /**
     * 新增socket管理
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:add')")
    @Log(title = "socket管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndSocketInfo indSocketInfo)
    {
        return toAjax(indSocketInfoService.insertIndSocketInfo(indSocketInfo));
    }

    /**
     * 修改socket管理
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:edit')")
    @Log(title = "socket管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IndSocketInfo indSocketInfo)
    {
        return toAjax(indSocketInfoService.updateIndSocketInfo(indSocketInfo));
    }

    /**
     * 删除socket管理
     */
//    @PreAuthorize("@ss.hasPermi('industry:socketInfo:remove')")
    @Log(title = "socket管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(indSocketInfoService.deleteIndSocketInfoByIds(ids));
    }
}
