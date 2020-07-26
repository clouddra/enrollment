package com.school.enrollment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @NotNull(message = "id is mandatory")
    Long id;

    @NotBlank(message = "firstName is mandatory")
    String firstName;

    @NotBlank(message = "lastName is mandatory")
    String lastName;

    String nationality;

    @JsonProperty("class")
    @NotBlank(message = "class is mandatory")
    @Pattern(regexp="^\\d\\s[A-Z]$", message = "class must be of format \"{number}<space>{uppercase alphabet}\"")
    String schoolClass;

}
