package nodo.erp.Hr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Position;

public interface Position_Repository extends JpaRepository<Position, Integer>{
	Page<Position> findAll(Pageable pageable);
    Page<Position> findAll(Specification<Position> spec, Pageable pageable);
}
