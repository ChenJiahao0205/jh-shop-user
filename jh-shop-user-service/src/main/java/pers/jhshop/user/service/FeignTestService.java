package pers.jhshop.user.service;

import pers.jhshop.fapi.discount.dto.TestDTO;
import pers.jhshop.fapi.discount.dto.req.TestReq;

/**
 * @author ChenJiahao(五条)
 * @date 2024/12/12 22:49:34
 */
public interface FeignTestService {
    TestDTO test(TestReq testReq);
}
