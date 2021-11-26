package com.example.Onboarding.DTO;

import lombok.Data;

@Data
public class SignUpDto {
    private String nombre;
    private String apellidoPat;
    private String apellidoMat;
    private Integer edad;
    private String email;
    private String telefono;
    private String username;
    private String password;
}
