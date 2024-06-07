package org.home.dev.db.repository;

import org.home.dev.db.entity.Todo;
import org.home.dev.db.entity.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TodoGroupRepo extends JpaRepository<TodoGroup, Long> {

    TodoGroup findOneById(Long id);
    List<TodoGroup> findAllByUserId(Long id);
}
