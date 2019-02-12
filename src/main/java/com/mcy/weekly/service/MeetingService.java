package com.mcy.weekly.service;

import com.mcy.weekly.pojo.Meeting;
import com.mcy.weekly.pojo.PageParams;

import java.util.Date;
import java.util.List;

public interface MeetingService {
    List<Meeting> getMeetings(PageParams pageParams);
    int deleteMeeting(int id);
    int insertMeeting(Meeting meeting);
    int updateMeeting(Meeting meeting);

    Meeting getMeetingById(Integer id);

    Meeting getMeetingByDate(Date date);
}
