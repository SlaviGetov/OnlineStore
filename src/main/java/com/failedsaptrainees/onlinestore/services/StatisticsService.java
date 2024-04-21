package com.failedsaptrainees.onlinestore.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StatisticsService {

    public BigDecimal getIncomeBetweenTwoDates(LocalDateTime fromDate, LocalDateTime toDate);

}
