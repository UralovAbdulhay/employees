package com.example.demo.rabbit;


import com.example.demo.entity.Attendance;
import com.example.demo.entity.types.AttendanceType;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.requests.AttendanceRequest;
import com.example.demo.service.impl.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.demo.rabbit.Urls.*;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final AttendanceServiceImpl attendanceService;
    private final RabbitSender rabbitSender;


    @RabbitListener(queues = ATTENDANCE_SAVE + FROM)
    public void listenerSave(AttendanceRequest request) {
        System.out.println(ATTENDANCE_SAVE + " = " + request);

        Attendance saved = attendanceService.save(request);

        if (request.getType() == AttendanceType.LEFT) {
            throw new BadRequest("tugadi");
        }

        rabbitSender.sendObject(attendanceService.convertToPayload(saved), TOPIC_EXCHANGE, ATTENDANCE_SAVE);
    }




}
