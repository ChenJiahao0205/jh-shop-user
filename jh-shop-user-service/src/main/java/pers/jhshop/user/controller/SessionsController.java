package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.SessionsCreateReq;
import pers.jhshop.user.model.req.SessionsQueryReq;
import pers.jhshop.user.model.req.SessionsUpdateReq;
import pers.jhshop.user.model.vo.SessionsVO;
import pers.jhshop.user.service.ISessionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户会话表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "sessions")
@RequiredArgsConstructor
public class SessionsController {
    private final ISessionsService sessionsService;

    @PostMapping("create")
    public ResultBo create(@RequestBody SessionsCreateReq createReq) {
        sessionsService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody SessionsUpdateReq updateReq) {
        sessionsService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<SessionsVO> getById(Long id) {
        SessionsVO vo = sessionsService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<SessionsVO>> page(@RequestBody SessionsQueryReq queryReq) {
        Page page = sessionsService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

