package pers.jhshop.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.req.LogsCreateReq;
import pers.jhshop.user.model.req.LogsQueryReq;
import pers.jhshop.user.model.req.LogsUpdateReq;
import pers.jhshop.user.model.vo.LogsVO;
import pers.jhshop.user.model.entity.Logs;
import pers.jhshop.user.mapper.LogsMapper;
import pers.jhshop.user.service.ILogsService;
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
 * 用户行为日志表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(LogsCreateReq createReq) {
        if (Objects.isNull(createReq.getUserId())) {
            throw new ServiceException("用户ID，外键关联users表不能为空");
        }



        Logs entity = new Logs();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(LogsUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Logs entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户行为日志表不存在");
        }

        // 2.重复性判断
        Logs entityToUpdate = new Logs();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public LogsVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Logs entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("用户行为日志表不存在");
        }

        LogsVO vo = new LogsVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<LogsVO> pageBiz(LogsQueryReq queryReq) {
        Page<Logs> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Logs> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<LogsVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Logs> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<LogsVO> vos = records.stream().map(record -> {
            LogsVO vo = new LogsVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Logs> page(LogsQueryReq queryReq) {
        Page<Logs> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Logs> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Logs> listByQueryReq(LogsQueryReq queryReq) {
        LambdaQueryWrapper<Logs> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Logs> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Logs> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Logs> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Logs::getId, ids);
        List<Logs> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Logs::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Logs getOneByQueryReq(LogsQueryReq queryReq) {
        LambdaQueryWrapper<Logs> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Logs> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Logs> getLambdaQueryWrapper(LogsQueryReq queryReq) {
        LambdaQueryWrapper<Logs> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Logs::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getUserId()), Logs::getUserId, queryReq.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getActionType()), Logs::getActionType, queryReq.getActionType());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getActionTypeLike()), Logs::getActionType, queryReq.getActionTypeLike());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getActionDetail()), Logs::getActionDetail, queryReq.getActionDetail());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getActionDetailLike()), Logs::getActionDetail, queryReq.getActionDetailLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Logs::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Logs::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
