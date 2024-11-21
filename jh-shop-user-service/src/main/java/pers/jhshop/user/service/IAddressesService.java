package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.Addresses;
import pers.jhshop.user.model.req.AddressesCreateReq;
import pers.jhshop.user.model.req.AddressesQueryReq;
import pers.jhshop.user.model.req.AddressesUpdateReq;
import pers.jhshop.user.model.vo.AddressesVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface IAddressesService extends IService<Addresses> {

    void createBiz(AddressesCreateReq createReq);

    void updateBiz(AddressesUpdateReq updateReq);

    AddressesVO getByIdBiz(Long id);

    Page<AddressesVO> pageBiz(AddressesQueryReq queryReq);

    Page<Addresses> page(AddressesQueryReq queryReq);

    List<Addresses> listByQueryReq(AddressesQueryReq queryReq);

    Map<Long, Addresses> getIdEntityMap(List<Long> ids);

    Addresses getOneByQueryReq(AddressesQueryReq queryReq);

}
