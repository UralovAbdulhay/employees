package com.example.demo.payload.payloadForValodition;

import com.example.demo.payload.requests.BaseRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionRequestValid extends BaseRequest {

    String name;

    Long departmentId;

}
