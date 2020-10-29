package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Department;
import cn.wolfcode.rbac.mapper.DepartmentMapper;
import cn.wolfcode.rbac.query.PageResult;
import cn.wolfcode.rbac.query.QueryObject;
import cn.wolfcode.rbac.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void insertOrUpdate(Department department) {
        //判断insert还是update操作
        if (department.getId() == null) {
            //insert操作
            departmentMapper.insert(department);
        } else {
            //update操作
            departmentMapper.updateByPrimaryKey(department);
        }
    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Department getOne(Long id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        return department;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = departmentMapper.selectAll();
        return departments;
    }

    @Override
    public PageResult query(QueryObject qo) {
        //总条数
        int totalCount = departmentMapper.selectForCount();
        //带有分页效果的数据集
        PageResult pageResult;
        if (totalCount != 0) {
            List<Department> data = departmentMapper.selectForData(qo);
            //调用全参构造器
            pageResult = new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
        } else {
            //调用缺参构造器
            pageResult = new PageResult(qo.getCurrentPage(), qo.getPageSize());
        }
        return pageResult;
    }
}
