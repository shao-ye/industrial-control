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
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.service.IIndFactoryAreaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 厂区管理Controller
 * 
 * @author ruoyi
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/industry/factoryArea")
public class IndFactoryAreaController extends BaseController
{
    @Autowired
    private IIndFactoryAreaService indFactoryAreaService;

    /**
     * 查询厂区列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:list')")
    @GetMapping("/list")
    public TableDataInfo list(IndFactoryArea indFactoryArea)
    {
        startPage();
        List<IndFactoryArea> list = indFactoryAreaService.selectIndFactoryAreaList(indFactoryArea);
        return getDataTable(list);
    }

    /**
     * 查询当前用户厂区列表
     * @return 厂区集合
     */
    @GetMapping("/user")
    public AjaxResult getFactoryAreaByUser()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return AjaxResult.success(indFactoryAreaService.selectIndFactoryAreaListByUserId(user.getUserId()));
    }

    /**
     * 查询当前全部厂区列表
     * @return 厂区集合
     */
    @GetMapping("/all")
    public AjaxResult getFactoryAreaAll()
    {
        return AjaxResult.success(indFactoryAreaService.selectIndFactoryAreaListAll());
    }

    /**
     * 查询当前全部厂号列表
     * @return 厂号集合
     */
    @GetMapping("/factory")
    public AjaxResult getFactoryAll()
    {
        return AjaxResult.success(indFactoryAreaService.selectFactoryAll());
    }

    /**
     * 导出厂区列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:export')")
    @Log(title = "厂区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IndFactoryArea indFactoryArea)
    {
        List<IndFactoryArea> list = indFactoryAreaService.selectIndFactoryAreaList(indFactoryArea);
        ExcelUtil<IndFactoryArea> util = new ExcelUtil<IndFactoryArea>(IndFactoryArea.class);
        util.exportExcel(response, list, "厂区数据");
    }

    /**
     * 获取厂区详细信息
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(indFactoryAreaService.selectIndFactoryAreaById(id));
    }

    /**
     * 新增厂区
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:add')")
    @Log(title = "厂区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndFactoryArea indFactoryArea)
    {
        return toAjax(indFactoryAreaService.insertIndFactoryArea(indFactoryArea));
    }

    /**
     * 修改厂区
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:edit')")
    @Log(title = "厂区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IndFactoryArea indFactoryArea)
    {
        return toAjax(indFactoryAreaService.updateIndFactoryArea(indFactoryArea));
    }

    /**
     * 删除厂区
     */
//    @PreAuthorize("@ss.hasPermi('industry:factoryArea:remove')")
    @Log(title = "厂区", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(indFactoryAreaService.deleteIndFactoryAreaByIds(ids));
    }
}
