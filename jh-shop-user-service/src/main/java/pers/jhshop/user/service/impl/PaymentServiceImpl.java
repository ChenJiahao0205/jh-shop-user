package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.PaymentCreateReq;
import pers.jhshop.user.model.req.PaymentQueryReq;
import pers.jhshop.user.model.req.PaymentUpdateReq;
import pers.jhshop.user.model.vo.PaymentVO;
import pers.jhshop.user.model.entity.Payment;
import pers.jhshop.user.mapper.PaymentMapper;
import pers.jhshop.user.service.IPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.jhshop.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * <p>
 * 用户支付表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(PaymentCreateReq createReq) {
        if (Objects.isNull(createReq.getUserId())) {
            throw new ServiceException("用户ID，外键关联users表不能为空");
        }



        Payment entity = new Payment();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(PaymentUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Payment entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户支付表不存在");
        }

        // 2.重复性判断
        Payment entityToUpdate = new Payment();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public PaymentVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Payment entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户支付表不存在");
        }

        PaymentVO vo = new PaymentVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<PaymentVO> pageBiz(PaymentQueryReq queryReq) {
        Page<Payment> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Payment> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<PaymentVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Payment> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<PaymentVO> vos = records.stream().map(record -> {
            PaymentVO vo = new PaymentVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Payment> page(PaymentQueryReq queryReq) {
        Page<Payment> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Payment> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Payment> listByQueryReq(PaymentQueryReq queryReq) {
        LambdaQueryWrapper<Payment> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Payment> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Payment> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Payment> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Payment::getId, ids);
        List<Payment> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Payment::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Payment getOneByQueryReq(PaymentQueryReq queryReq) {
        LambdaQueryWrapper<Payment> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Payment> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Payment> getLambdaQueryWrapper(PaymentQueryReq queryReq) {
        LambdaQueryWrapper<Payment> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Payment::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getUserId()), Payment::getUserId, queryReq.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPaymentMethod()), Payment::getPaymentMethod, queryReq.getPaymentMethod());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPaymentMethodLike()), Payment::getPaymentMethod, queryReq.getPaymentMethodLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getAccountInfo()), Payment::getAccountInfo, queryReq.getAccountInfo());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getAccountInfoLike()), Payment::getAccountInfo, queryReq.getAccountInfoLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Payment::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), Payment::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Payment::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
