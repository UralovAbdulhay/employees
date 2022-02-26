package com.example.demo.rabbit;


import com.example.demo.entity.types.AttendanceType;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.service.impl.AttendanceServiceImpl;
import com.example.demo.service.impl.DepartmentServiceImpl;
import com.example.demo.service.impl.EmployeeServiceImpl;
import com.example.demo.service.impl.PositionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.demo.rabbit.Urls.ATTENDANCE_SAVE;
import static com.example.demo.rabbit.Urls.FROM;


@Component
@RequiredArgsConstructor
public class MessageListener {


    private final AttendanceServiceImpl attendanceService;
    private final DepartmentServiceImpl departmentService;
    private final PositionServiceImpl positionService;
    private final EmployeeServiceImpl employeeService;
    private final RabbitSender rabbitSender;


    @RabbitListener(queues = ATTENDANCE_SAVE + FROM)
    public void listenerSave(AttendanceRequest request) {

        if (request.getType() == AttendanceType.COME) {
            throw new BadRequest("yana tugadi");
        }

        attendanceService.updateForRabbit(request);
    }


}
