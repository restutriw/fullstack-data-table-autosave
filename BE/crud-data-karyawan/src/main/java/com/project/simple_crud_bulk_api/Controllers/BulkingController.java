package com.project.simple_crud_bulk_api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.simple_crud_bulk_api.Models.Karyawan;
import com.project.simple_crud_bulk_api.Services.KaryawanService;

import lombok.Data;

import java.util.List;

@RestController
@RequestMapping("/api/karyawan")
public class BulkingController {

    @Autowired
    private KaryawanService karyawanService;

    @PostMapping("/bulk-crud")
    public ResponseEntity<String> batchOperation(@RequestBody BulkCRUDRequest bulkCRUDRequest) {
        try {
            // Proses operasi batch
            karyawanService.processBulkCRUD(
                bulkCRUDRequest.getCreates(), 
                bulkCRUDRequest.getUpdates(), 
                bulkCRUDRequest.getDeletes()
            );
            return ResponseEntity.ok("Create, update, dan delete data karyawan berhasil dilakukan.");
        } catch (Exception e) {
            // Log error dan response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Terjadi kesalahan saat memproses data: " + e.getMessage());
        }
    }

    @Data
    public static class BulkCRUDRequest {
        private List<Karyawan> creates;
        private List<UpdateRequest> updates;
        private List<Long> deletes;

        @Data
        public static class UpdateRequest {
            private Long id;
            private Karyawan karyawan;
        }
    }
}

