package com.project.simple_crud_bulk_api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.simple_crud_bulk_api.Models.Karyawan;
import com.project.simple_crud_bulk_api.Services.KaryawanService;

import java.util.List;

@RestController
@RequestMapping("/api/karyawan")
public class KaryawanController {

    @Autowired
    private KaryawanService karyawanService;

    @GetMapping
    public List<Karyawan> getAllKaryawan() {
        return karyawanService.getAllKaryawan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Karyawan> getKaryawanById(@PathVariable Long id) {
        Karyawan karyawan = karyawanService.getKaryawanById(id).orElseThrow();
        return ResponseEntity.ok(karyawan);
    }

    @PostMapping
    public Karyawan createKaryawan(@RequestBody Karyawan karyawan) {
        return karyawanService.createKaryawan(karyawan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Karyawan> updateKaryawan(@PathVariable Long id, @RequestBody Karyawan karyawanDetails) {
        Karyawan updatedKaryawan = karyawanService.updateKaryawan(id, karyawanDetails);
        return ResponseEntity.ok(updatedKaryawan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKaryawan(@PathVariable Long id) {
        karyawanService.deleteKaryawan(id);
        return ResponseEntity.noContent().build();
    }
}
