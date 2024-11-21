package pers.jhshop.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.user.model.entity.Payment;
import pers.jhshop.user.model.req.PaymentCreateReq;
import pers.jhshop.user.model.req.PaymentQueryReq;
import pers.jhshop.user.model.req.PaymentUpdateReq;
import pers.jhshop.user.model.vo.PaymentVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 用户支付表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
public interface IPaymentService extends IService<Payment> {

    void createBiz(PaymentCreateReq createReq);

    void updateBiz(PaymentUpdateReq updateReq);

    PaymentVO getByIdBiz(Long id);

    Page<PaymentVO> pageBiz(PaymentQueryReq queryReq);

    Page<Payment> page(PaymentQueryReq queryReq);

    List<Payment> listByQueryReq(PaymentQueryReq queryReq);

    Map<Long, Payment> getIdEntityMap(List<Long> ids);

    Payment getOneByQueryReq(PaymentQueryReq queryReq);

}
