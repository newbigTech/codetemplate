package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;
import com.newbig.codetemplate.dal.mapper.UserMapper;
import com.newbig.codetemplate.dal.model.User;
import com.newbig.codetemplate.dto.UserAddDto;
import com.newbig.codetemplate.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-01-22 20:48:10
 * Desc:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页获取 user 列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageInfo<User> getList(int pageSize,int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        List<User> list = userMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public User getDetailById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param userAddDto
    */
    public void addUser(UserAddDto userAddDto){
        User user = new User();
        BeanCopyUtil.copyProperties(userAddDto,user);
        userMapper.insertSelective(user);
    }
    /**
    * 更新
    * @param userUpdateDto
    */
    public void updateUser(UserUpdateDto userUpdateDto){
        User user = new User();
        BeanCopyUtil.copyProperties(userUpdateDto,user);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        criteria.andEqualTo("id",userUpdateDto.getId());
        userMapper.updateByExample(user,example);
    }

    /**
    * 逻辑删除
    * @param id
    */
    public void deleteUser(Integer id){
        User user = new User();
        user.setIsDeleted(true);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
