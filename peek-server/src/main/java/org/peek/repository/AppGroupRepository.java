package org.peek.repository;

import org.peek.domain.AppGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository  extends JpaRepository<AppGroup, String> {

}
