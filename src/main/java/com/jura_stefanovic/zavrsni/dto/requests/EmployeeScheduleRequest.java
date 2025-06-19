package com.jura_stefanovic.zavrsni.dto.requests;

import com.jura_stefanovic.zavrsni.constants.Day;

import java.time.LocalDateTime;

public class EmployeeScheduleRequest {
    private Day day;
    private LocalDateTime start;
    private LocalDateTime end;

    // Getters and setters
    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
