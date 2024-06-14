package com.example.backend.repository;

import com.example.backend.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Flobby
 */

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Integer>, QuerydslPredicateExecutor {

    @Query("select e from #{#entityName} e where e.pkId = ?1 and e.deleteFlag = 0")
    Optional<T> findByPkId(Integer pkId);

    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.deleteFlag = 1 where e.pkId = ?1")
    void logicDelete(Integer pkId);

    @Modifying
    @Transactional
    default void logicDelete(T entity) {
        logicDelete(entity.getPkId());
    }
}