package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.AddressesCreateReq;
import pers.jhshop.user.model.req.AddressesQueryReq;
import pers.jhshop.user.model.req.AddressesUpdateReq;
import pers.jhshop.user.model.vo.AddressesVO;
import pers.jhshop.user.model.entity.Addresses;
import pers.jhshop.user.mapper.AddressesMapper;
import pers.jhshop.user.service.IAddressesService;
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
 * 用户地址表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressesServiceImpl extends ServiceImpl<AddressesMapper, Addresses> implements IAddressesService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(AddressesCreateReq createReq) {
        if (Objects.isNull(createReq.getUserId())) {
            throw new ServiceException("用户ID，外键关联users表不能为空");
        }



        Addresses entity = new Addresses();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(AddressesUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Addresses entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户地址表不存在");
        }

        // 2.重复性判断
        Addresses entityToUpdate = new Addresses();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public AddressesVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Addresses entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户地址表不存在");
        }

        AddressesVO vo = new AddressesVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<AddressesVO> pageBiz(AddressesQueryReq queryReq) {
        Page<Addresses> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Addresses> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<AddressesVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Addresses> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<AddressesVO> vos = records.stream().map(record -> {
            AddressesVO vo = new AddressesVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Addresses> page(AddressesQueryReq queryReq) {
        Page<Addresses> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Addresses> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Addresses> listByQueryReq(AddressesQueryReq queryReq) {
        LambdaQueryWrapper<Addresses> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Addresses> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Addresses> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Addresses> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Addresses::getId, ids);
        List<Addresses> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Addresses::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Addresses getOneByQueryReq(AddressesQueryReq queryReq) {
        LambdaQueryWrapper<Addresses> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Addresses> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Addresses> getLambdaQueryWrapper(AddressesQueryReq queryReq) {
        LambdaQueryWrapper<Addresses> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Addresses::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getUserId()), Addresses::getUserId, queryReq.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getAddress()), Addresses::getAddress, queryReq.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getAddressLike()), Addresses::getAddress, queryReq.getAddressLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getIsDefault()), Addresses::getIsDefault, queryReq.getIsDefault());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Addresses::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), Addresses::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Addresses::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
