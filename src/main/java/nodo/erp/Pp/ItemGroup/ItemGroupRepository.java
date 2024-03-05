package nodo.erp.Pp.ItemGroup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemGroupRepository extends JpaRepository<ItemGroup, Integer> {
	Optional<ItemGroup> findByIgId(Integer IgId);
}
	