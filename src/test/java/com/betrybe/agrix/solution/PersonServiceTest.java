package com.betrybe.agrix.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.exception.PersonNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.PersonRepository;
import com.betrybe.agrix.ebytr.staff.security.Role;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class PersonServiceTest {

  @Autowired
  PersonService personService;

  @MockBean
  PersonRepository personRepository;

  @Test
  public void testnewPerson() {
    // new person
    Person person = new Person();
    person.setUsername("luiz");
    person.setPassword("1234");
    person.setRole(Role.USER);

    // return person
    Person personToReturn = new Person();
    personToReturn.setId(123L);
    personToReturn.setUsername(person.getUsername());
    personToReturn.setPassword(person.getPassword());
    personToReturn.setRole(person.getRole());

    // mock de retorno
    Mockito.when(personRepository.save((any(Person.class))))
        .thenReturn(personToReturn);

    Person savedPerson = personService.create(person);

    // service foi chamada?
    Mockito.verify(personRepository).save(any(Person.class));

    // validação
    assertEquals(personToReturn.getId(), savedPerson.getId());
    assertEquals(personToReturn.getUsername(), savedPerson.getUsername());
    assertEquals(personToReturn.getPassword(), savedPerson.getPassword());
    assertEquals(personToReturn.getRole(), savedPerson.getRole());
  }

  @Test
  public void testGetPersonById() {
    // new person
    Person person = new Person();
    person.setId(123L);
    person.setUsername("luiz");
    person.setPassword("1234");
    person.setRole(Role.USER);

    // mock de retorno
    Mockito.when(personRepository.findById((eq(123L))))
        .thenReturn(Optional.of(person));

    Person returneddPerson = personService.getPersonById(123L);

    // service foi chamada?
    Mockito.verify(personRepository).findById(eq(123L));

    // validação
    assertEquals(returneddPerson.getId(), person.getId());
    assertEquals(returneddPerson.getUsername(), person.getUsername());
    assertEquals(returneddPerson.getPassword(), person.getPassword());
    assertEquals(returneddPerson.getRole(), person.getRole());
  }

  @Test
  public void testGetPersonNotFound() {
    Mockito.when(personRepository.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(123L));

    Mockito.verify(personRepository).findById(eq(123L));
  }

  @Test
  public void testGetPersonByUsername() {
    // new person
    Person person = new Person();
    person.setId(123L);
    person.setUsername("luiz");
    person.setPassword("1234");
    person.setRole(Role.USER);

    // mock de retorno
    Mockito.when(personRepository.findByUsername((eq("luiz"))))
        .thenReturn(Optional.of(person));

    Person returneddPerson = personService.getPersonByUsername("luiz");

    // service foi chamada?
    Mockito.verify(personRepository).findByUsername(eq("luiz"));

    // validação
    assertEquals(returneddPerson.getId(), person.getId());
    assertEquals(returneddPerson.getUsername(), person.getUsername());
    assertEquals(returneddPerson.getPassword(), person.getPassword());
    assertEquals(returneddPerson.getRole(), person.getRole());
  }

  @Test
  public void testGetPersonByUsernameNotFound() {
    Mockito.when(personRepository.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(PersonNotFoundException.class, () -> personService.getPersonByUsername("lui"));

    Mockito.verify(personRepository).findByUsername(eq("lui"));
  }

}
