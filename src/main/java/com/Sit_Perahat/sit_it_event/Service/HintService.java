package com.Sit_Perahat.sit_it_event.Service;

import com.Sit_Perahat.sit_it_event.Entity.Hints;

import java.util.List;

public interface HintService {




    List<Hints> findHints(String studentId);
}
