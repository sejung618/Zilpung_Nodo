$(document).ready(function(){		// html 문서의 로딩이 다 끝나면 함수 실행

    var url=window.location.href;	// 현재 접속중인 페이지의 URL 값 가져오기 (url이라는 변수에 저장)
    
    // 기본사항 관리
    	// 품목 관리: 품목그룹 조회
	    if(url.includes('basic/itemgroup/list')){		// 괄호 안의 String이 발견되면 True 반환
	        $("#BS_ItemManage").attr("class", "collapse show");		// 아코디언 메뉴 열기
			$("#BS_ItemManage div").children("a:eq(0)").attr("class", "collapse-item active");	// 선택 메뉴 텍스트 변화
			$("#BS_ItemManage_a").attr("aria-expanded", "true");		// 화살표 방향(▽)
			$("#BS_ItemManage_a").attr("class", "nav-link");
	    }
    	// 품목 관리: 품목그룹 등록
	    if(url.includes('basic/itemgroup/create')){
	        $("#BS_ItemManage").attr("class", "collapse show");
			$("#BS_ItemManage div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#BS_ItemManage_a").attr("aria-expanded", "true");
			$("#BS_ItemManage_a").attr("class", "nav-link");
	    }
	    // 거래처 관리: 거래처 관리
	    if(url.includes('account/list')){
	        $("#BS_AccManage").attr("class", "collapse show");
			$("#BS_AccManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#BS_AccManage_a").attr("aria-expanded", "true");
			$("#BS_AccManage_a").attr("class", "nav-link");
	    }
     	// 창고 관리: 서브 메뉴 1
	    if(url.includes('basic/storage/list')){
	        $("#BS_StorageMange").attr("class", "collapse show");
			$("#BS_StorageMange div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#BS_StorageMange_a").attr("aria-expanded", "true");
			$("#BS_StorageMange_a").attr("class", "nav-link");
	    }
     	// 창고 관리: 서브 메뉴 2
	    if(url.includes('basic/storage/create')){
	        $("#BS_StorageMange").attr("class", "collapse show");
			$("#BS_StorageMange div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#BS_StorageMange_a").attr("aria-expanded", "true");
			$("#BS_StorageMange_a").attr("class", "nav-link");
	    }
	 
	// 인사 관리
		// 사원 관리: 사원 현황
		if(url.includes('Hr/list')){
	        $("#HR_EmployeeManage").attr("class", "collapse show");
			$("#HR_EmployeeManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#HR_EmployeeManage_a").attr("aria-expanded", "true");
			$("#HR_EmployeeManage_a").attr("class", "nav-link");
	    }
	    // 사원 관리: 인사 발령
/*		if(url.includes('')){
	        $("#HR_EmployeeManage").attr("class", "collapse show");
			$("#HR_EmployeeManage div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#HR_EmployeeManage_a").attr("aria-expanded", "true");
			$("#HR_EmployeeManage_a").attr("class", "nav-link");
	    }*/
	    // 사원 관리: 부서 관리
		if(url.includes('dep/list')){
	        $("#HR_EmployeeManage").attr("class", "collapse show");
			$("#HR_EmployeeManage div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#HR_EmployeeManage_a").attr("aria-expanded", "true");
			$("#HR_EmployeeManage_a").attr("class", "nav-link");
	    }
		// 근태 관리: 출/퇴근 현황
		if(url.includes('Attendance/list')){
	        $("#HR_CommuteManage").attr("class", "collapse show");
			$("#HR_CommuteManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#HR_CommuteManage_a").attr("aria-expanded", "true");
			$("#HR_CommuteManage_a").attr("class", "nav-link");
	    }
		// 휴가 관리: 휴가 현황
		if(url.includes('vacation/list')){
	        $("#HR_BreakManage").attr("class", "collapse show");
			$("#HR_BreakManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#HR_BreakManage_a").attr("aria-expanded", "true");
			$("#HR_BreakManage_a").attr("class", "nav-link");
	    }

	// 생산 관리
		// BOM(소요량): BOM 입력
		if(url.includes('BOM/create')){
	        $("#PD_BOM").attr("class", "collapse show");
			$("#PD_BOM div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#PD_BOM_a").attr("aria-expanded", "true");
			$("#PD_BOM_a").attr("class", "nav-link");
	    }
	    // BOM(소요량): BOM 조회
		if(url.includes('BOM/list')){
	        $("#PD_BOM").attr("class", "collapse show");
			$("#PD_BOM div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#PD_BOM_a").attr("aria-expanded", "true");
			$("#PD_BOM_a").attr("class", "nav-link");
	    }
	    // BOM(소요량): 소요량 계산
		if(url.includes('BOM/calculate')){
	        $("#PD_BOM").attr("class", "collapse show");
			$("#PD_BOM div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#PD_BOM_a").attr("aria-expanded", "true");
			$("#PD_BOM_a").attr("class", "nav-link");
	    }
	    // 작업지시서: 작업지시서 조회
		if(url.includes('workorder/list')){
			$("#PD_WorkOrder").attr("class", "collapse show");
			$("#PD_WorkOrder div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#PD_WorkOrder_a").attr("aria-expanded", "true");
			$("#PD_WorkOrder_a").attr("class", "nav-link");
	    }
	    // 작업지시서: 작업지시서 입력
	    if(url.includes('workorder/create')){
			$("#PD_WorkOrder").attr("class", "collapse show");
			$("#PD_WorkOrder div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#PD_WorkOrder_a").attr("aria-expanded", "true");
			$("#PD_WorkOrder_a").attr("class", "nav-link");
	    }
	    // 생산불출·입고: 생산불출 조회
		if(url.includes('pdrelease/list')){
			$("#PD_PdMovement").attr("class", "collapse show");
			$("#PD_PdMovement div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#PD_PdMovement_a").attr("aria-expanded", "true");
			$("#PD_PdMovement_a").attr("class", "nav-link");
	    }
	    // 생산불출·입고: 생산불출 입력
		if(url.includes('pdrelease/create')){
			$("#PD_PdMovement").attr("class", "collapse show");
			$("#PD_PdMovement div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#PD_PdMovement_a").attr("aria-expanded", "true");
			$("#PD_PdMovement_a").attr("class", "nav-link");
	    }
	    // 생산불출·입고: 생산입고 조회
	    if(url.includes('pdstock/list')){
			$("#PD_PdMovement").attr("class", "collapse show");
			$("#PD_PdMovement div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#PD_PdMovement_a").attr("aria-expanded", "true");
			$("#PD_PdMovement_a").attr("class", "nav-link");
	    }
	    // 생산불출·입고: 생산입고 입력
	    if(url.includes('pdstock/create')){
			$("#PD_PdMovement").attr("class", "collapse show");
			$("#PD_PdMovement div").children("a:eq(3)").attr("class", "collapse-item active");
			$("#PD_PdMovement_a").attr("aria-expanded", "true");
			$("#PD_PdMovement_a").attr("class", "nav-link");
	    }
	    
	
	// 재고·유통
		// 재고조정: 재고조정 등록
		if(url.includes('inventory/create')){
	        $("#MM_Inventory").attr("class", "collapse show");
			$("#MM_Inventory div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#MM_Inventory_a").attr("aria-expanded", "true");
			$("#MM_Inventory_a").attr("class", "nav-link");
	    }
	    // 재고조정: 재고조정 조회
		if(url.includes('inventory/list')){
	        $("#MM_Inventory").attr("class", "collapse show");
			$("#MM_Inventory div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#MM_Inventory_a").attr("aria-expanded", "true");
			$("#MM_Inventory_a").attr("class", "nav-link");
	    }
		// 입·출고관리: 입고처리
		if(url.includes('warehousing/create')){
	        $("#MM_Movement").attr("class", "collapse show");
			$("#MM_Movement div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#MM_Movement_a").attr("aria-expanded", "true");
			$("#MM_Movement_a").attr("class", "nav-link");
	    }
	    // 입·출고관리: 입고현황
		if(url.includes('warehousing/list')){
	        $("#MM_Movement").attr("class", "collapse show");
			$("#MM_Movement div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#MM_Movement_a").attr("aria-expanded", "true");
			$("#MM_Movement_a").attr("class", "nav-link");
	    }
	    // 입·출고관리: 출고처리
		if(url.includes('shipping/create')){
	        $("#MM_Movement").attr("class", "collapse show");
			$("#MM_Movement div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#MM_Movement_a").attr("aria-expanded", "true");
			$("#MM_Movement_a").attr("class", "nav-link");
	    }
	    // 입·출고관리: 출고현황
		if(url.includes('shipping/list')){
	        $("#MM_Movement").attr("class", "collapse show");
			$("#MM_Movement div").children("a:eq(3)").attr("class", "collapse-item active");
			$("#MM_Movement_a").attr("aria-expanded", "true");
			$("#MM_Movement_a").attr("class", "nav-link");
	    }
	    
	// 영업 관리
		// 구매 관리: 구매
/*		if(url.includes('')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    } */
	    // 구매 관리: 구매 신청
		if(url.includes('purchase/create')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    }
	    // 구매 관리: 구매 현황
		if(url.includes('purchase/list')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    }
	    // 구매 관리: 발주 신청
/*		if(url.includes('')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(3)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    } */
	    // 구매 관리: 발주 현황
/*		if(url.includes('')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(4)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    } */
	    // 구매 관리: 미입고 현황
/*		if(url.includes('')){
	        $("#SD_PurchaseManage").attr("class", "collapse show");
			$("#SD_PurchaseManage div").children("a:eq(5)").attr("class", "collapse-item active");
			$("#SD_PurchaseManage_a").attr("aria-expanded", "true");
			$("#SD_PurchaseManage_a").attr("class", "nav-link");
	    } */
	    // 판매 관리: 판매
/*		if(url.includes('')){
	        $("#SD_SaleManage").attr("class", "collapse show");
			$("#SD_SaleManage div").children("a:eq(0)").attr("class", "collapse-item active");
			$("#SD_SaleManage_a").attr("aria-expanded", "true");
			$("#SD_SaleManage_a").attr("class", "nav-link");
	    } */
	    // 판매 관리: 판매 입력
/*		if(url.includes('')){
	        $("#SD_SaleManage").attr("class", "collapse show");
			$("#SD_SaleManage div").children("a:eq(1)").attr("class", "collapse-item active");
			$("#SD_SaleManage_a").attr("aria-expanded", "true");
			$("#SD_SaleManage_a").attr("class", "nav-link");
	    } */
	    // 판매 관리: 판매 현황
/*		if(url.includes('')){
	        $("#SD_SaleManage").attr("class", "collapse show");
			$("#SD_SaleManage div").children("a:eq(2)").attr("class", "collapse-item active");
			$("#SD_SaleManage_a").attr("aria-expanded", "true");
			$("#SD_SaleManage_a").attr("class", "nav-link");
	    } */
	    // 판매 관리: 예약 신청
/*		if(url.includes('')){
	        $("#SD_SaleManage").attr("class", "collapse show");
			$("#SD_SaleManage div").children("a:eq(3)").attr("class", "collapse-item active");
			$("#SD_SaleManage_a").attr("aria-expanded", "true");
			$("#SD_SaleManage_a").attr("class", "nav-link");
	    } */
	    // 판매 관리: 예약 현황
/*		if(url.includes('')){
	        $("#SD_SaleManage").attr("class", "collapse show");
			$("#SD_SaleManage div").children("a:eq(4)").attr("class", "collapse-item active");
			$("#SD_SaleManage_a").attr("aria-expanded", "true");
			$("#SD_SaleManage_a").attr("class", "nav-link");
	    } */
		
});