package org.kurilov.tasklist.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.kurilov.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Mapper
public interface TaskRepository {


    @Results({
            @Result(property = "id", column = "task_id", id = true),
            @Result(property = "title", column = "task_title"),
            @Result(property = "description", column = "task_description"),
            @Result(property = "status", column = "task_status"),
            @Result(property = "expirationDate", column = "task_expiration_date", typeHandler = LocalDateTimeTypeHandler.class)
    })
    @Select("""
            SELECT id              as task_id,
                   title           as task_title,
                   description     as task_description,
                   status          as task_status,
                   expiration_date as task_expiration_date
            FROM tasks
            WHERE id = #{id}
            """)
    Optional<Task> findById(@Param("id") Long id);

    @Results({
            @Result(property = "id", column = "task_id", id = true),
            @Result(property = "title", column = "task_title"),
            @Result(property = "description", column = "task_description"),
            @Result(property = "status", column = "task_status"),
            @Result(property = "expirationDate", column = "task_expiration_date", typeHandler = LocalDateTimeTypeHandler.class)
    })
    @Select("""
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.status          as task_status,
                   t.expiration_date as task_expiration_date
            FROM tasks t
            JOIN users_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = #{userId}
            """)
    List<Task> findAllByUserId(@Param("userId") Long userId);

    @Insert("""
            INSERT INTO users_tasks (user_id, task_id) VALUES (#{userId}, #{taskId});
            """)
    void assignToUserById(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Update("""
            UPDATE tasks SET title = #{title},
                             description = #{description},
                             status = #{status},
                             expiration_date = #{expirationDate}
                         WHERE id = #{id};
            """)
    void update(Task task);

    @Insert("""
            INSERT INTO tasks (title, description, status, expiration_date)
            VALUES (#{title}, #{description}, #{status}, #{expirationDate});
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Task task);

    @Delete("""
            DELETE FROM tasks WHERE id = #{id};
            """)
    long delete(Long id);
}
