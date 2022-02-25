package com.example.demo.rabbit;


import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.payload.requests.PositionRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class MessageListener {

//    @RabbitListener(queues = Urls.ATTENDANCE_SAVE)
//    public void listenerSave(AttendanceRequest request) {
//        System.out.println(Urls.ATTENDANCE_SAVE + " = " + request);
//
//    }
//
//    @RabbitListener(queues = Urls.ATTENDANCE_UPDATE)
//    public void listenerUpdate(AttendanceRequest request) {
//        System.out.println(Urls.ATTENDANCE_UPDATE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.DEPARTMENT_SAVE)
//    public void listenerSave(DepartmentRequest request) {
//        System.out.println(Urls.DEPARTMENT_SAVE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.DEPARTMENT_UPDATE)
//    public void listenerUpdate(DepartmentRequest request) {
//        System.out.println(Urls.DEPARTMENT_UPDATE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.EMPLOYEE_SAVE)
//    public void listenerSave(EmployeeRequest request) {
//        System.out.println(Urls.EMPLOYEE_SAVE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.EMPLOYEE_UPDATE)
//    public void listenerUpdate(EmployeeRequest request) {
//        System.out.println(Urls.EMPLOYEE_UPDATE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.POSITION_SAVE)
//    public void listenerSave(PositionRequest request) {
//        System.out.println(Urls.POSITION_SAVE + " = " + request);
//
//    }
//
//
//    @RabbitListener(queues = Urls.POSITION_UPDATE)
//    public void listenerUpdate(PositionRequest request) {
//        System.out.println(Urls.POSITION_UPDATE + " = " + request);
//
//    }

}
