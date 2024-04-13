package com.failedsaptrainees.onlinestore.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface StatisticsService {

    public Double getIncomeBetweenTwoDates(LocalDateTime fromDate, LocalDateTime toDate);

}
