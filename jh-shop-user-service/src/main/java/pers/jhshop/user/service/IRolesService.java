package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.Roles;
import pers.jhshop.user.model.req.RolesCreateReq;
import pers.jhshop.user.model.req.RolesQueryReq;
import pers.jhshop.user.model.req.RolesUpdateReq;
import pers.jhshop.user.model.vo.RolesVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface IRolesService extends IService<Roles> {

    void createBiz(RolesCreateReq createReq);

    void updateBiz(RolesUpdateReq updateReq);

    RolesVO getByIdBiz(Long id);

    Page<RolesVO> pageBiz(RolesQueryReq queryReq);

    Page<Roles> page(RolesQueryReq queryReq);

    List<Roles> listByQueryReq(RolesQueryReq queryReq);

    Map<Long, Roles> getIdEntityMap(List<Long> ids);

    Roles getOneByQueryReq(RolesQueryReq queryReq);

}
