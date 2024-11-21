package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.Logs;
import pers.jhshop.user.model.req.LogsCreateReq;
import pers.jhshop.user.model.req.LogsQueryReq;
import pers.jhshop.user.model.req.LogsUpdateReq;
import pers.jhshop.user.model.vo.LogsVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户行为日志表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface ILogsService extends IService<Logs> {

    void createBiz(LogsCreateReq createReq);

    void updateBiz(LogsUpdateReq updateReq);

    LogsVO getByIdBiz(Long id);

    Page<LogsVO> pageBiz(LogsQueryReq queryReq);

    Page<Logs> page(LogsQueryReq queryReq);

    List<Logs> listByQueryReq(LogsQueryReq queryReq);

    Map<Long, Logs> getIdEntityMap(List<Long> ids);

    Logs getOneByQueryReq(LogsQueryReq queryReq);

}
