package com.example.demo.base;


import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRequest {

    @NotNull(groups = UpdateValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = UpdateValidation.class, message = "Id 1 dan kichik bo'lishi mumkin emas")
    @Null(groups = SaveValidation.class)
    @JsonProperty(value = "remote_id")
    protected Long id;

    @JsonProperty(value = "id")
    protected Long remote_id;

}
