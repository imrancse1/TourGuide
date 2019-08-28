package com.example.mytourguide.PojoClasses;

public class EventModel {
    private String EventId;
    private String EventName;
    private String StartLocation;
    private String Destination;
    private String JourneyDate;
    private String Budget;
    private String PlanDate;
    private  long Time;

    public EventModel() {
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        this.Time = time;
    }

    public EventModel(String eventId, String eventName, String startLocation, String destination, String journeyDate, String budget, String planDate, long time) {
        EventId = eventId;
        EventName = eventName;
        StartLocation = startLocation;
        Destination = destination;
        JourneyDate = journeyDate;
        Budget = budget;
        PlanDate = planDate;
        Time=time;

    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(String startLocation) {
        StartLocation = startLocation;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getJourneyDate() {
        return JourneyDate;
    }

    public void setJourneyDate(String journeyDate) {
        JourneyDate = journeyDate;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String planDate) {
        PlanDate = planDate;
    }
}
