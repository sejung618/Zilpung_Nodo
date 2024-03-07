package nodo.erp.Hr.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nodo.erp.Hr.Entity.Event;
import nodo.erp.Hr.Repository.Event_Repository;

@Service
public class Event_Service {
	
	private final Event_Repository eventRepository;

    @Autowired
    public Event_Service(Event_Repository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
    	return eventRepository.findById(id);
    }
}
