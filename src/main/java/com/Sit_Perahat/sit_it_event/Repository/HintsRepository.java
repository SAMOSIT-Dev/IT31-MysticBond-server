package com.Sit_Perahat.sit_it_event.Repository;

import com.Sit_Perahat.sit_it_event.Entity.Hints;
import com.Sit_Perahat.sit_it_event.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HintsRepository extends JpaRepository<Hints, Integer> {

    List<Hints> findHintsByUsers_StudentId(String usersStudentId);
}
