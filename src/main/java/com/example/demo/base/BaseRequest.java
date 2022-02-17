package com.example.demo.base;


import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseRequest {

    @NotNull(groups = UpdateValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = {UpdateValidation.class, SaveValidation.class}, message = "Id 1 dan kichik bo'lishi mumkin emas")
    protected Long id;

}
