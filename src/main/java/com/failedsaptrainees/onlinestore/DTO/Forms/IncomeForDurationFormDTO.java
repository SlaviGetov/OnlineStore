package com.failedsaptrainees.onlinestore.DTO.Forms;


import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class IncomeForDurationFormDTO {

    @NotNull(message = "A start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fromDate;

    @NotNull(message = "An end date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime toDate;

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }
}
