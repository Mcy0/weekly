package com.mcy.weekly.service.impl;

import com.mcy.weekly.mapper.MeetingMapper;
import com.mcy.weekly.pojo.Meeting;
import com.mcy.weekly.pojo.PageParams;
import com.mcy.weekly.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingMapper meetingMapper = null;

    @Override
    public List<Meeting> getMeetings(PageParams pageParams) {
        return meetingMapper.getMeetings(pageParams);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int deleteMeeting(int id) {
        return meetingMapper.deleteMeeting(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertMeeting(Meeting meeting) {
        return meetingMapper.insertMeeting(meeting);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updateMeeting(Meeting meeting) {
        return meetingMapper.updateMeeting(meeting);
    }

    @Override
    public Meeting getMeetingById(Integer id) {
        return meetingMapper.getMeetingById(id);
    }

    @Override
    public Meeting getMeetingByDate(Date date) {
        return meetingMapper.getMeetingByDate(date);
    }
}
