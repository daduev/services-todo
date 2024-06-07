package org.home.dev.db.repository;

import org.home.dev.db.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TodoRepo extends JpaRepository<Todo, Long>  {

    @Query("""
        select t 
        from TodoGroup tg
        inner join tg.todos t
        inner join tg.user u
        where u.id = :userId
        and tg.id = :todoGroupId
        and (:hideCompleted is null or t.done = false)
        """)
    List<Todo> findAllByUserIdAndTodoGroupId(Long userId, Long todoGroupId,  Boolean hideCompleted);

}
