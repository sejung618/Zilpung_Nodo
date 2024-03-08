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
	
	private String generateRvNum(String ymd) {
	    jakarta.persistence.Query query = entity.createQuery(
	            "SELECT MAX(CAST(SUBSTRING(i.rvnum, 8) AS int)) "
	                    + "FROM Reservation i WHERE SUBSTRING(i.rvnum, 1, 6) = :ymd");
	    query.setParameter("ymd", ymd);
	    Integer maxNum = (Integer) query.getSingleResult();
	    int nextNumber = (maxNum == null) ? 1 : maxNum + 1;
	    String nextRV_Num = String.format("%s-%04d", ymd, nextNumber);

	    return nextRV_Num;
	}

	public void create(String rvnum, LocalDateTime rvdate, String rvitem, String rvicode, Integer rvcount, 
	        Integer rvprice, Integer rvcp, Integer rvvat, Integer rvsum, String rvpick, LocalDateTime rvptime) {
	    
	    Reservation res = new Reservation();

	    if (rvdate == null) {
	        // rvdate가 null인 경우에만 현재 시간으로 설정
	        res.setRvdate(LocalDateTime.now());
	    } else {
	        res.setRvdate(rvdate);
	    }

	    String yearMonthDay = String.format("%02d%02d%02d", res.getRvdate().getYear() % 100, res.getRvdate().getMonthValue(), res.getRvdate().getDayOfMonth());
	    String nextRV_Num = generateRvNum(yearMonthDay);
	    
	    res.setRvnum(nextRV_Num);
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
	    // 예약 정보를 데이터베이스에서 가져옴
	    Reservation res = this.RR.findById(reser.getId()).orElse(null);

	    // 예약 정보가 존재하는 경우에만 업데이트 수행
	    if (res != null) {
	        res.setRvpick(rvpick);

	        // "O"를 선택한 경우에만 현재 시간 설정
	        if ("O".equals(rvpick)) {
	            res.setRvptime(LocalDateTime.now());
	        } else {
	            // "O"가 아닌 경우에는 rvptime을 설정 (예: 사용자가 직접 입력한 시간)
	            res.setRvptime(rvptime);
	        }

	        // 업데이트된 예약 정보 저장
	        this.RR.save(res);
	    }
	}
	
	// 예약 정보 삭제 메소드
	public void delete(Integer id) {
		Reservation res = new Reservation();
		res.setId(id);
		this.RR.delete(res);
	}
	
	
	//예약 정보 검색 메소드 추가예정
	
}
