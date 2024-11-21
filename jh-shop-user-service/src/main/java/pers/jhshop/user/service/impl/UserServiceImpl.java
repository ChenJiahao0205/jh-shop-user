package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.UserCreateReq;
import pers.jhshop.user.model.req.UserQueryReq;
import pers.jhshop.user.model.req.UserUpdateReq;
import pers.jhshop.user.model.vo.UserVO;
import pers.jhshop.user.model.entity.User;
import pers.jhshop.user.mapper.UserMapper;
import pers.jhshop.user.service.IUserService;
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
 * 用户表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(UserCreateReq createReq) {
        if (Objects.isNull(createReq.getRoleId())) {
            throw new ServiceException("用户角色ID，外键关联roles表不能为空");
        }



        User entity = new User();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(UserUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        User entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户表不存在");
        }

        // 2.重复性判断
        User entityToUpdate = new User();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public UserVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        User entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户表不存在");
        }

        UserVO vo = new UserVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<UserVO> pageBiz(UserQueryReq queryReq) {
        Page<User> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<User> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<UserVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<User> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<UserVO> vos = records.stream().map(record -> {
            UserVO vo = new UserVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<User> page(UserQueryReq queryReq) {
        Page<User> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<User> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<User> listByQueryReq(UserQueryReq queryReq) {
        LambdaQueryWrapper<User> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<User> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, User> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getId, ids);
        List<User> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(User::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public User getOneByQueryReq(UserQueryReq queryReq) {
        LambdaQueryWrapper<User> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<User> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<User> getLambdaQueryWrapper(UserQueryReq queryReq) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), User::getId, queryReq.getId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getUsername()), User::getUsername, queryReq.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getUsernameLike()), User::getUsername, queryReq.getUsernameLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPasswordHash()), User::getPasswordHash, queryReq.getPasswordHash());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPasswordHashLike()), User::getPasswordHash, queryReq.getPasswordHashLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getEmail()), User::getEmail, queryReq.getEmail());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getEmailLike()), User::getEmail, queryReq.getEmailLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPhone()), User::getPhone, queryReq.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPhoneLike()), User::getPhone, queryReq.getPhoneLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getStatus()), User::getStatus, queryReq.getStatus());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), User::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), User::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getLastLoginAt()), User::getLastLoginAt, queryReq.getLastLoginAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getRoleId()), User::getRoleId, queryReq.getRoleId());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), User::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
