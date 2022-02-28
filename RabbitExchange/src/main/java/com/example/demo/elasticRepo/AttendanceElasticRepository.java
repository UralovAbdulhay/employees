package com.example.demo.elasticRepo;

import com.example.demo.entity.Attendance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AttendanceElasticRepository extends ElasticsearchRepository<Attendance, String > {
}
