//package nodo.erp.Pp.Bom;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//public class Bom {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer BomId;
//	
//	@Column(unique = true, length = 7)
//	private String BomCode;
//	
//	// 생산품목 리스트
//	@OneToMany(mappedBy = "bom", cascade = CascadeType.ALL)
//    private List<BomPd> BomPdList;
//	
//	// 
//	@ManyToMany
//    @JoinTable(name = "bom_pd",
//               joinColumns = @JoinColumn(name = "bom_id"),
//               inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private List<BomPd> products = new ArrayList<>();
//	
//	private LocalDateTime createDate;
//
//}
