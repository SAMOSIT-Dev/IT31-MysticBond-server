package com.Sit_Perahat.sit_it_event.Repository;


import com.Sit_Perahat.sit_it_event.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, String> {


    Optional<Users> findUsersByStudentId(String studentId);
}
