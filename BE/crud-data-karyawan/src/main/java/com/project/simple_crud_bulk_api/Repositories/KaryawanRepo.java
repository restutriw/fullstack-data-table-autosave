package com.project.simple_crud_bulk_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simple_crud_bulk_api.Models.Karyawan;

public interface KaryawanRepo extends JpaRepository<Karyawan, Long> {
}

