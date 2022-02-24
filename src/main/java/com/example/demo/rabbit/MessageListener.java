package com.example.demo.rabbit;


import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.payload.requests.PositionRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = Urls.ATTENDANCE_QUE)
    public void listener(AttendanceRequest request) {
        System.out.println("CAR = " + request);
    }


    @RabbitListener(queues = Urls.DEPARTMENT_QUE)
    public void listener(DepartmentRequest request) {
        System.out.println("STUDENT = " + request);
    }


    @RabbitListener(queues = Urls.EMPLOYEE_QUE)
    public void listener(EmployeeRequest request) {
        System.out.println("WORKER = " + request);
    }


    @RabbitListener(queues = Urls.POSITION_QUE)
    public void listener(PositionRequest request) {
        System.out.println("COMMON = " + request);
    }

}
