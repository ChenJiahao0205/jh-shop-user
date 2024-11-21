package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.PaymentCreateReq;
import pers.jhshop.user.model.req.PaymentQueryReq;
import pers.jhshop.user.model.req.PaymentUpdateReq;
import pers.jhshop.user.model.vo.PaymentVO;
import pers.jhshop.user.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户支付表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;

    @PostMapping("create")
    public ResultBo create(@RequestBody PaymentCreateReq createReq) {
        paymentService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody PaymentUpdateReq updateReq) {
        paymentService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<PaymentVO> getById(Long id) {
        PaymentVO vo = paymentService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<PaymentVO>> page(@RequestBody PaymentQueryReq queryReq) {
        Page page = paymentService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

