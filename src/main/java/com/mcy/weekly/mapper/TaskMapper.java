package com.mcy.weekly.mapper;

import com.mcy.weekly.pojo.PageParams;
import com.mcy.weekly.pojo.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskMapper {

    int insertTask(Task task);//添加周报

    List<Task> getAllTasks(PageParams pageParams);//获取全部根据时间先后

    List<Task> getAllTasksByUserId(@Param("userId") int userId,@Param("pageParams")  PageParams pageParams);//获取全部根据时间先后

    List<Task> getTasks(@Param("startDate") Date startDate, @Param("endDate")Date endDate,
                        @Param("userId") int userId);//根据时间获取某个时间段周报 包括starDate和endDate

    int deleteByIdByUserId(@Param("id")int id, @Param("userId")int userId);//根据周报id删除

    int update(Task task);//动态sql更新心要修改的task内容;

    Task getTaskByIdByUserId(@Param("id") int id, @Param("userId") int userId);
}
