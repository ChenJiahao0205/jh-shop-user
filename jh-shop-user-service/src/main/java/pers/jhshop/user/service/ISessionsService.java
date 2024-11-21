package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.Sessions;
import pers.jhshop.user.model.req.SessionsCreateReq;
import pers.jhshop.user.model.req.SessionsQueryReq;
import pers.jhshop.user.model.req.SessionsUpdateReq;
import pers.jhshop.user.model.vo.SessionsVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户会话表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface ISessionsService extends IService<Sessions> {

    void createBiz(SessionsCreateReq createReq);

    void updateBiz(SessionsUpdateReq updateReq);

    SessionsVO getByIdBiz(Long id);

    Page<SessionsVO> pageBiz(SessionsQueryReq queryReq);

    Page<Sessions> page(SessionsQueryReq queryReq);

    List<Sessions> listByQueryReq(SessionsQueryReq queryReq);

    Map<Long, Sessions> getIdEntityMap(List<Long> ids);

    Sessions getOneByQueryReq(SessionsQueryReq queryReq);

}
