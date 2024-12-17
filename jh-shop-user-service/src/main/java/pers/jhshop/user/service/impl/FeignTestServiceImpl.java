package pers.jhshop.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.jhshop.fapi.discount.dto.TestDTO;
import pers.jhshop.fapi.discount.dto.req.TestReq;
import pers.jhshop.fapi.discount.service.TestApiService;
import pers.jhshop.user.service.FeignTestService;

/**
 * @author ChenJiahao(五条)
 * @date 2024/12/12 22:49:46
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeignTestServiceImpl implements FeignTestService {

    private final TestApiService testApiService;

    @Override
    public TestDTO test(TestReq testReq) {
        log.info(JSONObject.toJSONString(testReq));
        TestDTO testDTO = testApiService.testSome(testReq);
        log.info(JSONObject.toJSONString(testDTO));
        return testDTO;
    }
}
