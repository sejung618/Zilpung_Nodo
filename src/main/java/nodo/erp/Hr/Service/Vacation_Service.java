package nodo.erp.Hr.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.VacationApply;
import nodo.erp.Hr.Repository.Vaca_App_Reository;
import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RequiredArgsConstructor
@Service
public class Vacation_Service {

	private final Vaca_App_Reository vaca_App_Reository;

	public void create(Employee employee, LocalDate startdate, LocalDate enddate, String leavetype) {
		VacationApply q = new VacationApply();
		q.setEmployee(employee);
		q.setStartdate(startdate);
		q.setEnddate(enddate);
		q.setLeavetype(leavetype);
		List<LocalDate> holidays = getHolidaysBetween(startdate, enddate);
		long period = calculateWorkingDays(startdate, enddate, holidays);
		q.setPeriod(period);
		this.vaca_App_Reository.save(q);
	}

	public List<VacationApply> getList() {
		return this.vaca_App_Reository.findAll();
	}

	public Page<VacationApply> getList(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.vaca_App_Reository.findAll(pageable);
	}

	// 휴일을 제외한 근무일 수 계산
	private long calculateWorkingDays(LocalDate startdate, LocalDate enddate, List<LocalDate> holidays) {
		long days = ChronoUnit.DAYS.between(startdate, enddate) + 1; // 시작일과 종료일 포함
		long workingDays = days;

		for (LocalDate date = startdate; date.isBefore(enddate.plusDays(1)); date = date.plusDays(1)) {
			if (holidays.contains(date) || date.getDayOfWeek() == DayOfWeek.SATURDAY
					|| date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				workingDays--; // 휴일이거나 주말인 경우 근무일에서 제외
			}
		}

		return workingDays;
	}

	// 휴일 목록을 가져오는 예시 메서드
	private List<LocalDate> getHolidaysBetween(LocalDate startdate, LocalDate enddate) {
		List<LocalDate> holidays = new ArrayList<>();
		// 여기에 휴일을 계산하여 추가하는 로직을 구현합니다.
		// 예: 주말을 휴일로 처리하는 경우
		for (LocalDate date = startdate; date.isBefore(enddate.plusDays(1)); date = date.plusDays(1)) {
			if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				holidays.add(date);
			}
		}
		// holidays.add(LocalDate.of(2024, 3, 4));
		LocalDate currentDate = startdate;
		while (!currentDate.isAfter(enddate.plusMonths(1))) {
			try {
				// API 호출을 위한 URL 설정

				String urlString = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?"
						+ "serviceKey=mu0qncUi0U6yoO7DgzbUU00wwHqMKq8rq2vtRGyESGNXZ0QlR6XfEKjqCPySkZr4xeZyEk0nj%2F85VW6ujR3ngQ%3D%3D"
						+ "&solYear=" + currentDate.getYear() + "&solMonth="
						+ String.format("%02d", currentDate.getMonthValue());

				// URL 연결
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				// 응답 데이터를 XML로 파싱
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(connection.getInputStream());

				// 파싱하여 휴일 날짜 추출
				NodeList itemList = doc.getElementsByTagName("item");
				for (int i = 0; i < itemList.getLength(); i++) {
					Element item = (Element) itemList.item(i);
					String locDateStr = item.getElementsByTagName("locdate").item(0).getTextContent();
					LocalDate locDate = LocalDate.parse(locDateStr, DateTimeFormatter.BASIC_ISO_DATE);
					holidays.add(locDate);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			currentDate = currentDate.plusMonths(1);

		}
		return holidays;

	}

}
