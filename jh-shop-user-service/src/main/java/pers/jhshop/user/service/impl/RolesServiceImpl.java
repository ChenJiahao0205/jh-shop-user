package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.RolesCreateReq;
import pers.jhshop.user.model.req.RolesQueryReq;
import pers.jhshop.user.model.req.RolesUpdateReq;
import pers.jhshop.user.model.vo.RolesVO;
import pers.jhshop.user.model.entity.Roles;
import pers.jhshop.user.mapper.RolesMapper;
import pers.jhshop.user.service.IRolesService;
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
 * 用户角色表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(RolesCreateReq createReq) {


        Roles entity = new Roles();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(RolesUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Roles entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户角色表不存在");
        }

        // 2.重复性判断
        Roles entityToUpdate = new Roles();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public RolesVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Roles entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户角色表不存在");
        }

        RolesVO vo = new RolesVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<RolesVO> pageBiz(RolesQueryReq queryReq) {
        Page<Roles> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Roles> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<RolesVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Roles> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<RolesVO> vos = records.stream().map(record -> {
            RolesVO vo = new RolesVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Roles> page(RolesQueryReq queryReq) {
        Page<Roles> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Roles> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Roles> listByQueryReq(RolesQueryReq queryReq) {
        LambdaQueryWrapper<Roles> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Roles> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Roles> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Roles> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Roles::getId, ids);
        List<Roles> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Roles::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Roles getOneByQueryReq(RolesQueryReq queryReq) {
        LambdaQueryWrapper<Roles> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Roles> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Roles> getLambdaQueryWrapper(RolesQueryReq queryReq) {
        LambdaQueryWrapper<Roles> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Roles::getId, queryReq.getId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getRoleName()), Roles::getRoleName, queryReq.getRoleName());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getRoleNameLike()), Roles::getRoleName, queryReq.getRoleNameLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Roles::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Roles::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Roles::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
