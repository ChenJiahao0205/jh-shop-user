package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.LogsCreateReq;
import pers.jhshop.user.model.req.LogsQueryReq;
import pers.jhshop.user.model.req.LogsUpdateReq;
import pers.jhshop.user.model.vo.LogsVO;
import pers.jhshop.user.service.ILogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户行为日志表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "logs")
@RequiredArgsConstructor
public class LogsController {
    private final ILogsService logsService;

    @PostMapping("create")
    public ResultBo create(@RequestBody LogsCreateReq createReq) {
        logsService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody LogsUpdateReq updateReq) {
        logsService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<LogsVO> getById(Long id) {
        LogsVO vo = logsService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<LogsVO>> page(@RequestBody LogsQueryReq queryReq) {
        Page page = logsService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

