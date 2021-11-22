package com.example.Onboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Usuarios")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer idusuario;
    private String nombre;
    private String apellidoPat;
    private String apellidoMat;
    private Integer edad;
    private String mail;
    private String telefono;
    private String usuario;
    private String contraseña;


}
