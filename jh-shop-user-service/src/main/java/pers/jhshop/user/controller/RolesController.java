package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.RolesCreateReq;
import pers.jhshop.user.model.req.RolesQueryReq;
import pers.jhshop.user.model.req.RolesUpdateReq;
import pers.jhshop.user.model.vo.RolesVO;
import pers.jhshop.user.service.IRolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "roles")
@RequiredArgsConstructor
public class RolesController {
    private final IRolesService rolesService;

    @PostMapping("create")
    public ResultBo create(@RequestBody RolesCreateReq createReq) {
        rolesService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody RolesUpdateReq updateReq) {
        rolesService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<RolesVO> getById(Long id) {
        RolesVO vo = rolesService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<RolesVO>> page(@RequestBody RolesQueryReq queryReq) {
        Page page = rolesService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

