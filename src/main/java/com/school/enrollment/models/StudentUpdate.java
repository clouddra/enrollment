package com.school.enrollment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdate {
    @NotNull(message = "id is mandatory")
    Long id;

    @Nullable
    @Pattern(regexp="^(?=\\s*\\S).*$", message = "firstName must not be empty string")
    String firstName;

    @Nullable
    @Pattern(regexp="^(?=\\s*\\S).*$", message = "lastName must not be empty string")
    String lastName;

    @Nullable
    String nationality;

    @JsonProperty("class")
    @Pattern(regexp="^\\d\\s[A-Z]$", message = "class must be of format \"{number}<space>{uppercase alphabet}\"")
    String schoolClass;
}
