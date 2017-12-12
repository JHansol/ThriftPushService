package com.naver.npns.server.repository;

import com.naver.npns.server.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "messages", path = "messages")
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Long> {
    Page<MessageEntity> findByUserUUID(@Param("uuid") String userUUID, Pageable pageable);
}
