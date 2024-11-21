package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.User;
import pers.jhshop.user.model.req.UserCreateReq;
import pers.jhshop.user.model.req.UserQueryReq;
import pers.jhshop.user.model.req.UserUpdateReq;
import pers.jhshop.user.model.vo.UserVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface IUserService extends IService<User> {

    void createBiz(UserCreateReq createReq);

    void updateBiz(UserUpdateReq updateReq);

    UserVO getByIdBiz(Long id);

    Page<UserVO> pageBiz(UserQueryReq queryReq);

    Page<User> page(UserQueryReq queryReq);

    List<User> listByQueryReq(UserQueryReq queryReq);

    Map<Long, User> getIdEntityMap(List<Long> ids);

    User getOneByQueryReq(UserQueryReq queryReq);

}
