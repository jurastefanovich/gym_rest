package com.jura_stefanovic.zavrsni.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<String> specs;
    private String desc;


}
