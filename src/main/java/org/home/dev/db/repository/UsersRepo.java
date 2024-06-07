package org.home.dev.db.repository;

import org.home.dev.db.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UsersRepo extends JpaRepository<Users, Long> {

    Users findOneByUserName(String userName);

    boolean existsByUserNameLikeIgnoreCase(String userName);

}
