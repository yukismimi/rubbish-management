package com.yukismimi.rubbishmanagement.rubbsh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RubbishRepository extends JpaRepository<Rubbish, Long> {

    Optional<Rubbish> findByName(String name);

    List<Rubbish> findAllByName(String name);

    @Query(value = "select t from Rubbish t where t.name like %?1%")
    List<Rubbish> findAllByNameLike(String name);

    List<Rubbish> findAllByType(int type);
}
