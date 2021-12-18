package com.monthlyexpenses.server.config;

import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.repository.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitializationData implements ApplicationRunner {

    @Autowired
    private MonthRepository monthRepository;

    @Override
    public void run(ApplicationArguments args) {
        addMonthsIfNotExistsInTheDB();
    }

    private void addMonthsIfNotExistsInTheDB() {
        int numberOfMonths = 12;
        int[] existingMonths = new int[numberOfMonths];
        List<Month> monthList = monthRepository.findAll();

        for (Month month : monthList) {
            if (month.getMonthNumber() >= 0 && month.getMonthNumber() < numberOfMonths) {
                existingMonths[month.getMonthNumber()] = 1;
            }
        }

        for (int monthNumber = 0; monthNumber < numberOfMonths; monthNumber++) {
            if (existingMonths[monthNumber] == 0) {
                Month month = new Month();
                month.setMonthNumber(monthNumber);
                monthRepository.saveAndFlush(month);
            }
        }
    }
}
