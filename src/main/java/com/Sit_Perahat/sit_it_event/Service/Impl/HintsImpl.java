package com.Sit_Perahat.sit_it_event.Service.Impl;

import com.Sit_Perahat.sit_it_event.Entity.Hints;
import com.Sit_Perahat.sit_it_event.Repository.HintsRepository;
import com.Sit_Perahat.sit_it_event.Service.HintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HintsImpl implements HintService {

    @Autowired
    private HintsRepository hintsRepository;


    @Override
    public List<Hints> findHints(String studentId) {
        List<Hints> hints = hintsRepository.findHintsByUsers_StudentId(studentId);
        if (hints.isEmpty()) {
            throw new RuntimeException();
        }
        return hints;
    }
}
