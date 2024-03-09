package nodo.erp.Hr.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Position;
import nodo.erp.Hr.Repository.Position_Repository;

@RequiredArgsConstructor
@Service
public class Position_Service {
	private final Position_Repository position_Repository;
	
	public List<Position> getList() {
		return this.position_Repository.findAll();
	}
	
	 public Page<Position> getList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.position_Repository.findAll(pageable);
	    }
	 
	 
	 public void create(Integer positioncode, String positionname) {
		 Position q = new Position();
			q.setPositioncode(positioncode);
			q.setPositionname(positionname);
			
			this.position_Repository.save(q);
		}
	 
	 public Position getFindById(Integer id) {  
	        Optional<Position> getFindById = this.position_Repository.findById(id);
	        if (getFindById.isPresent()) {
	            return getFindById.get();
	        } else {
	            throw new DataNotFoundException("question not found");
	        }
	    }

	public void modify(Position position,Integer positioncode, String positionname) {
		position.setPositioncode(positioncode);
		position.setPositionname(positionname);
			
			this.position_Repository.save(position);
	}
	 
	public void delete(Position position) {
        this.position_Repository.delete(position);
    }
	
}

