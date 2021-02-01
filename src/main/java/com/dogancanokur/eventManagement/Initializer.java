package com.dogancanokur.eventManagement;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dogancanokur.eventManagement.model.Event;
import com.dogancanokur.eventManagement.model.Group;
import com.dogancanokur.eventManagement.repository.GroupRepository;

@Component
class Initializer implements CommandLineRunner {
	private final GroupRepository repository;

	public Initializer(GroupRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of("Istanbul Event", "Yalova Event", "Ordu Event", "Bursa Event")
				.forEach(name -> repository.save(new Group(name)));

		Group group = repository.findByName("Istanbul Event");
		Event event = Event.builder().title("Fenerbahce Signature").description("Signature Ceremony")
				.date(Instant.parse("2021-01-21T16:00:00.000Z")).build();

		group.setEvents(Collections.singleton(event));
		repository.save(group);

		repository.findAll().forEach(System.out::println);

	}

}
