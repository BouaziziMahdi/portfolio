package com.portilo.portilo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
@Entity
public class User {
    @GeneratedValue
    @Id
    private Integer id ;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String password ;

}
