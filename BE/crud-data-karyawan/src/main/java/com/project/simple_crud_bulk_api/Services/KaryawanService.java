package com.project.simple_crud_bulk_api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.simple_crud_bulk_api.Models.Karyawan;
import com.project.simple_crud_bulk_api.Repositories.KaryawanRepo;

import java.util.List;
import java.util.Optional;

@Service
public class KaryawanService {

    @Autowired
    private KaryawanRepo karyawanRepo;

    public List<Karyawan> getAllKaryawan() {
        return karyawanRepo.findAll();
    }

    public Optional<Karyawan> getKaryawanById(Long id) {
        return karyawanRepo.findById(id);
    }

    public Karyawan createKaryawan(Karyawan karyawan) {
        // Validasi dan pengecekan sebelum penyimpanan
        return karyawanRepo.save(karyawan);
    }

    public Karyawan updateKaryawan(Long id, Karyawan karyawanDetails) {
        Karyawan karyawan = karyawanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Karyawan tidak ditemukan"));
        karyawan.setNama(karyawanDetails.getNama());
        karyawan.setRole(karyawanDetails.getRole());
        karyawan.setStatus_pekerjaan(karyawanDetails.getStatus_pekerjaan());
        return karyawanRepo.save(karyawan);
    }

    public void deleteKaryawan(Long id) {
        if (karyawanRepo.existsById(id)) {
            karyawanRepo.deleteById(id);
        } else {
            throw new RuntimeException("Karyawan tidak ditemukan");
        }
    }

    @Transactional
    public void processBulkCRUD(List<Karyawan> creates, List<com.project.simple_crud_bulk_api.Controllers.BulkingController.BulkCRUDRequest.UpdateRequest> list, List<Long> deletes) {
        if (creates != null) {
            creates.forEach(this::createKaryawan);
        }

        if (list != null) {
            list.forEach(updateRequest -> updateKaryawan(updateRequest.getId(), updateRequest.getKaryawan()));
        }

        if (deletes != null) {
            deletes.forEach(this::deleteKaryawan);
        }
    }

    public static class UpdateRequest {
        private Long id;
        private Karyawan karyawan;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Karyawan getKaryawan() {
            return karyawan;
        }

        public void setKaryawan(Karyawan karyawan) {
            this.karyawan = karyawan;
        }
    }
}
