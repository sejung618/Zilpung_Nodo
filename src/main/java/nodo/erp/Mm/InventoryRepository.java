package nodo.erp.Mm;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	Inventory findByINQuantity(Integer INQuantity);
	List<Inventory> findByININameLike(String inname);
	Page<Inventory> findAll(Pageable pageable);
	Page<Inventory> findAll(Specification<Inventory> spec, Pageable pageable);
}

