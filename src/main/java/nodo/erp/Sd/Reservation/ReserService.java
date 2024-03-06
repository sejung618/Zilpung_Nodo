package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class ReserService {

	private final ReserRepository RR;
	
	@PersistenceContext
	private EntityManager entity;
	
	public List<Reservation> getList() {
		return RR.findAll();
	}
	
	public Reservation getReservation(Integer id) {
		Optional<Reservation> reser = this.RR.findById(id);
		if(reser.isPresent()) {
			return reser.get();
		} else {
			throw new DataNotFoundException("Reservation not found");
		}
	}
	
	private String generateNextRV_Num() {
	    jakarta.persistence.Query query = entity.createQuery(
	            "SELECT MAX(CAST(SUBSTRING(i.rvnum, 5) AS int)) "
	                    + "FROM YourEntity i WHERE SUBSTRING(i.rvnum, 1, 3) = 'RES'");
	    Integer maxNum = (Integer) query.getSingleResult();
	    int nextNumber = (maxNum == null) ? 1 : maxNum + 1;
	    String nextRV_Num = String.format("RES-%04d", nextNumber);

	    return nextRV_Num;
	}

	public void create(String rvnum, LocalDateTime rvdate, String rvitem, String rvicode, Integer rvcount, 
	        Integer rvprice, Integer rvcp, Integer rvvat, Integer rvsum, String rvpick, LocalDateTime rvptime) {
	    
	    Reservation res = new Reservation();

	    String nextRV_Num = generateNextRV_Num();

	    res.setRvnum(nextRV_Num);
	    res.setRvdate(LocalDateTime.now());
	    res.setRvitem(rvitem);
	    res.setRvicode(rvicode);
	    res.setRvcount(rvcount);
	    res.setRvprice(rvprice);
	    res.setRvcp(rvcp);
	    res.setRvvat(rvvat);
	    res.setRvsum(rvsum);
	    res.setRvpick("X"); // 신청 시 X 고정
	    res.setRvptime(null); // 신청 시 null 고정

	    this.RR.save(res);
	}
	
	
	// 예약 정보 수정 메소드
	public void update(Reservation reser, String rvpick, LocalDateTime rvptime) {
		
		Reservation res = this.RR.findById(reser.getId()).orElse(null);
		
		res.setRvpick(rvpick);
		res.setRvptime(LocalDateTime.now());
		
		this.RR.save(res);
	}
	
	// 예약 정보 삭제 메소드
	public void delete(Integer id) {
		Reservation res = new Reservation();
		res.setId(id);
		this.RR.delete(res);
	}
	
	
	//예약 정보 검색 메소드 추가예정
	
}
