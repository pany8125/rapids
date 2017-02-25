package com.rapids.core.repo;

import com.rapids.core.domain.KnowledgePack;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface KnowledgePackRepo extends CrudRepository<KnowledgePack, Long> {
}
