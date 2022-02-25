package com.example.demo.rabbit;


import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.service.impl.AttendanceServiceImpl;
import com.example.demo.service.impl.DepartmentServiceImpl;
import com.example.demo.service.impl.EmployeeServiceImpl;
import com.example.demo.service.impl.PositionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.demo.rabbit.Urls.*;


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
//        System.out.println(ATTENDANCE_SAVE + " = " + request);

        attendanceService.updateForRabbit(request);
    }

    @RabbitListener(queues = ATTENDANCE_UPDATE + FROM)
    public void listenerUpdate(AttendanceRequest request) {
//        System.out.println(ATTENDANCE_UPDATE + " = " + request);

        attendanceService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = DEPARTMENT_SAVE + FROM)
    public void listenerSave(DepartmentRequest request) {
//        System.out.println(DEPARTMENT_SAVE + " = " + request);

        departmentService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = DEPARTMENT_UPDATE + FROM)
    public void listenerUpdate(DepartmentRequest request) {
//        System.out.println(DEPARTMENT_UPDATE + " = " + request);

        departmentService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = EMPLOYEE_SAVE + FROM)
    public void listenerSave(EmployeeRequest request) {
//        System.out.println(EMPLOYEE_SAVE + " = " + request);

        employeeService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = EMPLOYEE_UPDATE + FROM)
    public void listenerUpdate(EmployeeRequest request) {
//        System.out.println(EMPLOYEE_UPDATE + " = " + request);

        employeeService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = POSITION_SAVE + FROM)
    public void listenerSave(PositionRequest request) {
//        System.out.println(POSITION_SAVE + " = " + request);

        positionService.updateForRabbit(request);
    }


    //    @RabbitListener(queues = POSITION_UPDATE + FROM)
    public void listenerUpdate(PositionRequest request) {
//        System.out.println(POSITION_UPDATE + " = " + request);

        positionService.updateForRabbit(request);
    }


}
