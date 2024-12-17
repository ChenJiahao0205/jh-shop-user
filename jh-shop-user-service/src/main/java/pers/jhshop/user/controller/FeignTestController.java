package pers.jhshop.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.fapi.discount.dto.req.TestReq;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.service.FeignTestService;

/**
 * Feign接口调试Controller
 * @author ChenJiahao(五条)
 * @date 2024/12/12 21:58:34
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "feign")
@RequiredArgsConstructor
public class FeignTestController {

    private final FeignTestService feignTestService;

    @PostMapping("test")
    public ResultBo test(@RequestBody TestReq testReq) {
        return ResultBo.success(feignTestService.test(testReq));
    }

}
