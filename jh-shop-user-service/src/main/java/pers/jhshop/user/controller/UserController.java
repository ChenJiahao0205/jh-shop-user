package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.UserCreateReq;
import pers.jhshop.user.model.req.UserQueryReq;
import pers.jhshop.user.model.req.UserUpdateReq;
import pers.jhshop.user.model.vo.UserVO;
import pers.jhshop.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("create")
    public ResultBo create(@RequestBody UserCreateReq createReq) {
        userService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody UserUpdateReq updateReq) {
        userService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<UserVO> getById(Long id) {
        UserVO vo = userService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<UserVO>> page(@RequestBody UserQueryReq queryReq) {
        Page page = userService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

