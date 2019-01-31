package com.crab.vo;

import com.crab.domain.Role;
import com.crab.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录信息
 */
@Data
public class LoginInfo implements Serializable {
    private User user;
    private List<Role> roles;
}
