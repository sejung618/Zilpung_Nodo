package nodo.erp.Hr.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nodo.erp.Hr.Entity.Event;
import nodo.erp.Hr.Entity.VacationApply;
import nodo.erp.Hr.Service.Event_Service;
import nodo.erp.Hr.Service.Vacation_Service;

@RestController
@RequestMapping("/api/events")
public class Event_Controller {

	private final Event_Service eventService;
	private final Vacation_Service vacation_Service;
	


	@Autowired
	public Event_Controller(Event_Service eventService,Vacation_Service vacation_Service) {
		this.eventService = eventService;
		this.vacation_Service = vacation_Service;
		
	}
	
	@GetMapping("/calendar")
	public List<VacationApply> getAllVaEvents() {
		return vacation_Service.getList();
	}

	@GetMapping
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	}

	@PostMapping
	public Event addEvent(@RequestBody Event event) {
		return eventService.addEvent(event);
	}

	@PutMapping("/{eventId}")
	public Event updateEvent(@PathVariable("eventId") Long eventId, @RequestBody Event event) {
		// Ensure the event ID matches the path variable
		if (!eventId.equals(event.getId())) {
			throw new IllegalArgumentException("Event ID in path must match the ID in the request body");
		}

		// Additional validation to check if the event ID is valid
		Optional<Event> existingEvent = eventService.getEventById(eventId);
		if (!existingEvent.isPresent()) {
			throw new IllegalArgumentException("Event with ID " + eventId + " not found");
		}

		return eventService.updateEvent(event);
	}

	@DeleteMapping("/{eventId}")
	public void deleteEvent(@PathVariable("eventId") Long eventId) {
		eventService.deleteEvent(eventId);
	}

}
