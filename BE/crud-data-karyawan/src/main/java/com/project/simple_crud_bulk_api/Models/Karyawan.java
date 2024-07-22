package com.project.simple_crud_bulk_api.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Karyawan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nama;
    private String role;
    private String status_pekerjaan;
}

