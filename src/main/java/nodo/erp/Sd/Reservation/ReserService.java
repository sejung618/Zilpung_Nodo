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
	            "SELECT MAX(CAST(SUBSTRING(i.RV_Num, 5) AS int)) "
	                    + "FROM YourEntity i WHERE SUBSTRING(i.RV_Num, 1, 3) = 'RES'");
	    Integer maxNum = (Integer) query.getSingleResult();
	    int nextNumber = (maxNum == null) ? 1 : maxNum + 1;
	    String nextRV_Num = String.format("RES-%04d", nextNumber);

	    return nextRV_Num;
	}

	public void create(String RV_Num, LocalDateTime RV_Date, String RV_Item, String RV_Icode, Integer RV_Count, 
	        Integer RV_Price, Integer RV_CP, Integer RV_VAT, Integer RV_Sum, String RV_Pick, LocalDateTime RV_PTime) {
	    
	    Reservation res = new Reservation();

	    String nextRV_Num = generateNextRV_Num();

	    res.setRV_Num(nextRV_Num);
	    res.setRV_Date(LocalDateTime.now());
	    res.setRV_Item(RV_Item);
	    res.setRV_Icode(RV_Icode);
	    res.setRV_Count(RV_Count);
	    res.setRV_Price(RV_Price);
	    res.setRV_CP(RV_CP);
	    res.setRV_VAT(RV_VAT);
	    res.setRV_Sum(RV_Sum);
	    res.setRV_Pick("X"); // 신청 시 X 고정
	    res.setRV_PTime(null); // 신청 시 null 고정

	    this.RR.save(res);
	}
	
	
	// 예약 정보 수정 메소드
	public void update(Reservation reser, String RV_Pick, LocalDateTime RV_PTime) {
		
		Reservation res = this.RR.findById(reser.getId()).orElse(null);
		
		res.setRV_Pick(RV_Pick);
		res.setRV_PTime(LocalDateTime.now());
		
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
