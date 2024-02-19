package nodo.erp.Hr;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface Dep_Repository extends JpaRepository<Hr_Dto_Dep, Integer> {
	Optional<Hr_Dto_Dep> findByDepname(String depcode);
	
}
