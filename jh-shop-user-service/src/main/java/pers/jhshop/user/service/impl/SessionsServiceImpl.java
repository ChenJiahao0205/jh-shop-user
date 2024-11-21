package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.SessionsCreateReq;
import pers.jhshop.user.model.req.SessionsQueryReq;
import pers.jhshop.user.model.req.SessionsUpdateReq;
import pers.jhshop.user.model.vo.SessionsVO;
import pers.jhshop.user.model.entity.Sessions;
import pers.jhshop.user.mapper.SessionsMapper;
import pers.jhshop.user.service.ISessionsService;
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
 * 用户会话表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionsServiceImpl extends ServiceImpl<SessionsMapper, Sessions> implements ISessionsService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(SessionsCreateReq createReq) {
        if (Objects.isNull(createReq.getRoleId())) {
            throw new ServiceException("用户角色ID，外键关联roles表不能为空");
        }



        Sessions entity = new Sessions();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(SessionsUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Sessions entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户会话表不存在");
        }

        // 2.重复性判断
        Sessions entityToUpdate = new Sessions();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public SessionsVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Sessions entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户会话表不存在");
        }

        SessionsVO vo = new SessionsVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<SessionsVO> pageBiz(SessionsQueryReq queryReq) {
        Page<Sessions> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Sessions> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<SessionsVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Sessions> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<SessionsVO> vos = records.stream().map(record -> {
            SessionsVO vo = new SessionsVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Sessions> page(SessionsQueryReq queryReq) {
        Page<Sessions> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Sessions> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Sessions> listByQueryReq(SessionsQueryReq queryReq) {
        LambdaQueryWrapper<Sessions> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Sessions> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Sessions> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Sessions> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Sessions::getId, ids);
        List<Sessions> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Sessions::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Sessions getOneByQueryReq(SessionsQueryReq queryReq) {
        LambdaQueryWrapper<Sessions> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Sessions> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Sessions> getLambdaQueryWrapper(SessionsQueryReq queryReq) {
        LambdaQueryWrapper<Sessions> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Sessions::getId, queryReq.getId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getUsername()), Sessions::getUsername, queryReq.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getUsernameLike()), Sessions::getUsername, queryReq.getUsernameLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPasswordHash()), Sessions::getPasswordHash, queryReq.getPasswordHash());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPasswordHashLike()), Sessions::getPasswordHash, queryReq.getPasswordHashLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getEmail()), Sessions::getEmail, queryReq.getEmail());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getEmailLike()), Sessions::getEmail, queryReq.getEmailLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPhone()), Sessions::getPhone, queryReq.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPhoneLike()), Sessions::getPhone, queryReq.getPhoneLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getStatus()), Sessions::getStatus, queryReq.getStatus());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Sessions::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), Sessions::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getLastLoginAt()), Sessions::getLastLoginAt, queryReq.getLastLoginAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getRoleId()), Sessions::getRoleId, queryReq.getRoleId());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Sessions::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
