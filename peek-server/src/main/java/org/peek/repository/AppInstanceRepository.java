package org.peek.repository;

import org.peek.domain.AppInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInstanceRepository  extends JpaRepository<AppInstance, String> {

}
