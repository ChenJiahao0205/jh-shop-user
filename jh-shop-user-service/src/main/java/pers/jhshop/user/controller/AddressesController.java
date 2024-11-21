package pers.jhshop.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.user.consts.JhShopUserApiConstants;
import pers.jhshop.user.model.req.AddressesCreateReq;
import pers.jhshop.user.model.req.AddressesQueryReq;
import pers.jhshop.user.model.req.AddressesUpdateReq;
import pers.jhshop.user.model.vo.AddressesVO;
import pers.jhshop.user.service.IAddressesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户地址表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@RestController
@RequestMapping(JhShopUserApiConstants.API_USER + "addresses")
@RequiredArgsConstructor
public class AddressesController {
    private final IAddressesService addressesService;

    @PostMapping("create")
    public ResultBo create(@RequestBody AddressesCreateReq createReq) {
        addressesService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody AddressesUpdateReq updateReq) {
        addressesService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<AddressesVO> getById(Long id) {
        AddressesVO vo = addressesService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<AddressesVO>> page(@RequestBody AddressesQueryReq queryReq) {
        Page page = addressesService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

