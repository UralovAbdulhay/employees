package com.example.demo.rabbit;


import com.example.demo.entity.Attendance;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import com.example.demo.requests.AttendanceRequest;
import com.example.demo.requests.DepartmentRequest;
import com.example.demo.requests.EmployeeRequest;
import com.example.demo.requests.PositionRequest;
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
        System.out.println(ATTENDANCE_SAVE + " = " + request);

        Attendance saved = attendanceService.save(request);
        rabbitSender.sendObject(attendanceService.convertToPayload(saved), TOPIC_EXCHANGE, ATTENDANCE_SAVE);
    }

    @RabbitListener(queues = ATTENDANCE_UPDATE + FROM)
    public void listenerUpdate(AttendanceRequest request) {
        System.out.println(ATTENDANCE_UPDATE + " = " + request);

        Attendance saved = attendanceService.save(request);
        rabbitSender.sendObject(attendanceService.convertToPayload(saved), TOPIC_EXCHANGE, ATTENDANCE_UPDATE);
    }


//    @RabbitListener(queues = DEPARTMENT_SAVE + FROM)
    public void listenerSave(DepartmentRequest request) {
        System.out.println(DEPARTMENT_SAVE + "  listening  = " + request);

        Department saved = departmentService.save(request);
        System.out.println("listener  saved object  = " + saved);
        DepartmentRequest departmentRequest = departmentService.convertToPayload(saved);
        System.out.println("convertToPayload = " + departmentRequest);
        rabbitSender.sendObject(departmentRequest, TOPIC_EXCHANGE, DEPARTMENT_SAVE);
    }


//    @RabbitListener(queues = DEPARTMENT_UPDATE + FROM)
    public void listenerUpdate(DepartmentRequest request) {
        System.out.println(DEPARTMENT_UPDATE + " = " + request);

        Department saved = departmentService.save(request);
        rabbitSender.sendObject(departmentService.convertToPayload(saved), TOPIC_EXCHANGE, DEPARTMENT_UPDATE);
    }


//    @RabbitListener(queues = EMPLOYEE_SAVE + FROM)
    public void listenerSave(EmployeeRequest request) {
        System.out.println(EMPLOYEE_SAVE + " = " + request);
        Employee saved = employeeService.save(request);
        rabbitSender.sendObject(employeeService.convertToPayload(saved), TOPIC_EXCHANGE, EMPLOYEE_SAVE);
    }


//    @RabbitListener(queues = EMPLOYEE_UPDATE + FROM)
    public void listenerUpdate(EmployeeRequest request) {
        System.out.println(EMPLOYEE_UPDATE + " = " + request);

        Employee saved = employeeService.save(request);
        rabbitSender.sendObject(employeeService.convertToPayload(saved), TOPIC_EXCHANGE, EMPLOYEE_UPDATE);
    }


//    @RabbitListener(queues = POSITION_SAVE + FROM)
    public void listenerSave(PositionRequest request) {
        System.out.println(POSITION_SAVE + " = " + request);

        Position saved = positionService.save(request);
        rabbitSender.sendObject(positionService.convertToPayload(saved), TOPIC_EXCHANGE, POSITION_SAVE);
    }


//    @RabbitListener(queues = POSITION_UPDATE + FROM)
    public void listenerUpdate(PositionRequest request) {
        System.out.println(POSITION_UPDATE + " = " + request);

        Position saved = positionService.save(request);
        rabbitSender.sendObject(positionService.convertToPayload(saved), TOPIC_EXCHANGE, POSITION_UPDATE);
    }

}
