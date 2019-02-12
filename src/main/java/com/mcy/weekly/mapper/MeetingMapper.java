package com.mcy.weekly.mapper;

import com.mcy.weekly.pojo.Meeting;
import com.mcy.weekly.pojo.PageParams;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeetingMapper {
    List<Meeting> getMeetings(PageParams pageParams);
    int deleteMeeting(int id);
    int insertMeeting(Meeting meeting);
    int updateMeeting(Meeting meeting);

    Meeting getMeetingById(int id);

    Meeting getMeetingByDate(Date date);
}
