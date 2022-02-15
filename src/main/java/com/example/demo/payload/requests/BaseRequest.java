package com.example.demo.payload.requests;


import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseRequest {

    Long id;

}
