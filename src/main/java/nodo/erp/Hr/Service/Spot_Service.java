package nodo.erp.Hr.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Spot;
import nodo.erp.Hr.Repository.Spot_Repository;

@RequiredArgsConstructor
@Service
public class Spot_Service {
	private final Spot_Repository spot_Repository;
	
	public List<Spot> getList() {
		return this.spot_Repository.findAll();
	}
	
	 public Page<Spot> getList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.spot_Repository.findAll(pageable);
	    }
	 
	 
	 public void create(String spotcode, String spotname) {
		 Spot q = new Spot();
			q.setSpotcode(spotcode);
			q.setSpotname(spotname);
			
			this.spot_Repository.save(q);
		}
	 
	 public Spot getFindById(Integer id) {  
	        Optional<Spot> getFindById = this.spot_Repository.findById(id);
	        if (getFindById.isPresent()) {
	            return getFindById.get();
	        } else {
	            throw new DataNotFoundException("Spot not found");
	        }
	    }

	public void modify(Spot spot,String spotcode, String spotname) {
		spot.setSpotcode(spotcode);
		spot.setSpotname(spotname);
			
			this.spot_Repository.save(spot);
	}
	 
	public void delete(Spot spot) {
        this.spot_Repository.delete(spot);
    }
	
}
