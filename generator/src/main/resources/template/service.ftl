package ${basePackage}.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import ${basePackage}.dal.mapper.${modelNameUpperCamel}Mapper;
import ${basePackage}.dal.model.${modelNameUpperCamel};
import ${basePackage}.dto.${modelNameUpperCamel}AddDto;
import ${basePackage}.dto.${modelNameUpperCamel}UpdateDto;
import ${basePackage}.dto.${modelNameUpperCamel}DeleteDto;
import ${basePackage}.common.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: ${author}
 * Date: ${date}
 * Desc:
 */
@Service
public class ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    /**
     * 分页获取 ${modelNameLowerCamel} 列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<${modelNameUpperCamel}> getList(int pageSize,int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(${modelNameUpperCamel}.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Mapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public ${modelNameUpperCamel} getDetailById(Long id){
        Example example = new Example(${modelNameUpperCamel}.class);
        return ${modelNameLowerCamel}Mapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param ${modelNameLowerCamel}AddDto
    */
    public void add${modelNameUpperCamel}(${modelNameUpperCamel}AddDto ${modelNameLowerCamel}AddDto){
        ${modelNameUpperCamel} ${modelNameLowerCamel} = new ${modelNameUpperCamel}();
        BeanCopyUtil.copyProperties(${modelNameLowerCamel}AddDto,${modelNameLowerCamel});
        ${modelNameLowerCamel}Mapper.insertSelective(${modelNameLowerCamel});
    }
    /**
    * 更新
    * @param ${modelNameLowerCamel}UpdateDto
    */
    public void update${modelNameUpperCamel}(${modelNameUpperCamel}UpdateDto ${modelNameLowerCamel}UpdateDto){
        ${modelNameUpperCamel} ${modelNameLowerCamel} = new ${modelNameUpperCamel}();
        BeanCopyUtil.copyProperties(${modelNameLowerCamel}UpdateDto,${modelNameLowerCamel});

        Example example = new Example(${modelNameUpperCamel}.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        criteria.andEqualTo("id",${modelNameLowerCamel}UpdateDto.getId());
        ${modelNameLowerCamel}Mapper.updateByExample(${modelNameLowerCamel},example);
    }

    /**
    * 逻辑删除
    * @param id
    */
    public void delete${modelNameUpperCamel}(Long id){
        ${modelNameUpperCamel} ${modelNameLowerCamel} = new ${modelNameUpperCamel}();
        ${modelNameLowerCamel}.setIsDeleted(true);
        ${modelNameLowerCamel}.setId(id);
        ${modelNameLowerCamel}Mapper.updateByPrimaryKeySelective(${modelNameLowerCamel});
    }
}
