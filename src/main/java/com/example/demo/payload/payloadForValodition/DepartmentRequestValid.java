package com.example.demo.payload.payloadForValodition;

import com.example.demo.payload.requests.BaseRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequestValid extends BaseRequest {

    @NotBlank
    String name;

}
