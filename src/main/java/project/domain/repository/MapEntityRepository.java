package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.domain.entity.MapEntity;

public interface MapEntityRepository extends JpaRepository<MapEntity, Long>{

}
