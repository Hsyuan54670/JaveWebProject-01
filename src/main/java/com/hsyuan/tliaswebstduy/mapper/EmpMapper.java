package com.hsyuan.tliaswebstduy.mapper;

import com.hsyuan.tliaswebstduy.pojo.Emp;
import com.hsyuan.tliaswebstduy.pojo.EmpQueryParam;
import com.hsyuan.tliaswebstduy.pojo.JobOption;
import com.hsyuan.tliaswebstduy.pojo.LoginInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

//    @Select("select count(*) from emp")
//    public long countEmp();
//
//    @Select("select emp.*,dept.name deptName from emp left join dept on emp.dept_id = dept.id " +
//            "order by emp.update_time desc " +
//            "limit  #{pageIndex},#{pageSize} ")
//    public List<Emp> findByPage(Integer pageIndex, Integer pageSize);
//    @Select("select emp.*,dept.name deptName from emp left join dept on emp.dept_id = dept.id " +
//            "order by emp.update_time desc ")
//    List<Emp> findByPageHelper();

//    @Select("select emp.*,dept.name deptName from emp left join dept on emp.dept_id = dept.id " +
//            "where emp.name like concat('%',#{name},'%') and emp.gender=#{gender} and emp.entry_date between #{begin} and #{end} " +
//            "order by emp.update_time desc ")
    List<Emp> findByPick(EmpQueryParam empQueryParam);

    // 插入员工时,需要将数据库自动生成的主键值设置到对象的id属性中
    //配置在xml中
    //@Options(useGeneratedKeys = true , keyProperty = "id")  //useGeneratedKeys:是否使用数据库自动生成的主键,keyProperty:将数据库自动生成的主键值设置到对象的哪个属性中
    void insert(Emp emp);

    //批量删除员工
    void deleteBatch(List<Integer> ids);

    //根据id查询员工
    Emp findEmpById(Integer id);

    void update(Emp emp);

    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();

    @MapKey("gender")
    List<Map<String, Object>> countEmpGenderData();

    List<Emp> findAllJob1();


    Emp selectByUserNameAndPassword(Emp emp);

    void updatePassword(Emp emp);
}
