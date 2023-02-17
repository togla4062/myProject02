package project.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.BoardNoticeEntity;

@Repository
public interface BoardNoticeRepository extends JpaRepository<BoardNoticeEntity, Long>{

	Page<BoardNoticeEntity> findByTitleContaining(String search, Pageable page);

	Page<BoardNoticeEntity> findByContentContaining(String search, Pageable page);

	Page<BoardNoticeEntity> findByRegistNo_nameContaining(String search, Pageable page);

}
