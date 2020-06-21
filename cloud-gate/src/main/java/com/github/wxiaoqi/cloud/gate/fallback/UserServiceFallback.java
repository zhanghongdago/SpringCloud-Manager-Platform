package com.github.wxiaoqi.cloud.gate.fallback;

import com.github.wxiaoqi.cloud.dto.vo.authority.PermissionInfo;
import com.github.wxiaoqi.cloud.gate.feign.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ace
 * @create 2018/3/7.
 */
@Service
@Slf4j
public class UserServiceFallback implements IUserService {
    @Override
    public List<PermissionInfo> getPermissionByUsername() {
        log.error("调用{}异常{}","getPermissionByUsername");
        return null;
    }

    @Override
    public List<PermissionInfo> getAllPermissionInfo() {
        log.error("调用{}异常","getPermissionByUsername");
        return null;
    }
}
