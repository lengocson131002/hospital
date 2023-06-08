package com.hospital.booking.utils;

import com.hospital.booking.models.Slot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SlotUtils {
    private static final int MORNING_SLOTS = 12;

    private static final int AFTERNOON_SLOTS = 12;

    private static final int MINUTES_PER_SLOT = 20;

    private static final int BREAK_TIME_MINUTES = 0;

    public static List<Slot> getDailySlots() {
        Calendar morningCalendar = Calendar.getInstance();
        morningCalendar.set(Calendar.HOUR_OF_DAY, 7);
        morningCalendar.set(Calendar.MINUTE, 0);
        List<Slot> slots = new ArrayList<>();

        int numberOfSlots = 1;

        while (numberOfSlots <= MORNING_SLOTS) {
            String startTime = String.format("%02d:%02d", morningCalendar.get(Calendar.HOUR_OF_DAY), morningCalendar.get(Calendar.MINUTE));
            Slot slot = new Slot();
            slot.setNumber(numberOfSlots);
            slot.setStartTime(startTime);
            slot.setStartTimeCalendar((Calendar) morningCalendar.clone());

            morningCalendar.add(Calendar.MINUTE, MINUTES_PER_SLOT);
            String endTime = String.format("%02d:%02d", morningCalendar.get(Calendar.HOUR_OF_DAY), morningCalendar.get(Calendar.MINUTE));

            slot.setEndTime(endTime);
            slot.setEndTimeCalendar((Calendar) morningCalendar.clone());

            slots.add(slot);

            morningCalendar.add(Calendar.MINUTE, BREAK_TIME_MINUTES);
            numberOfSlots++;
        }

        Calendar afternoonCalendar = Calendar.getInstance();
        afternoonCalendar.set(Calendar.HOUR_OF_DAY, 13);
        afternoonCalendar.set(Calendar.MINUTE, 0);


        while (numberOfSlots <= MORNING_SLOTS + AFTERNOON_SLOTS) {
            String startTime = String.format("%02d:%02d", afternoonCalendar.get(Calendar.HOUR_OF_DAY), afternoonCalendar.get(Calendar.MINUTE));

            Slot slot = new Slot();
            slot.setNumber(numberOfSlots);
            slot.setStartTime(startTime);
            slot.setStartTimeCalendar((Calendar) afternoonCalendar.clone());

            afternoonCalendar.add(Calendar.MINUTE, MINUTES_PER_SLOT);
            String endTime = String.format("%02d:%02d", afternoonCalendar.get(Calendar.HOUR_OF_DAY), afternoonCalendar.get(Calendar.MINUTE));

            slot.setEndTime(endTime);
            slot.setEndTimeCalendar((Calendar) afternoonCalendar.clone());

            slots.add(slot);

            afternoonCalendar.add(Calendar.MINUTE, BREAK_TIME_MINUTES);

            numberOfSlots++;
        }

        return slots;
    }

    public static Slot getSlot(int slot) {
        List<Slot> slots = getDailySlots();
        return slot > 0 && slot <= slots.size() ? slots.get(slot - 1) : null;
    }

}
