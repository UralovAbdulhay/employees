package com.example.demo.controller.endpoint;

import com.example.demo.base.BaseEndpoint;
import com.example.demo.payload.requests.AttendanceRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.demo.base.BaseURL.*;

@RequestMapping(ATTENDANCE)
public interface AttendanceEndpoint extends BaseEndpoint<AttendanceRequest> {


}
