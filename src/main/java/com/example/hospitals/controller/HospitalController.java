package com.example.hospitals.controller;

import com.example.hospitals.dao.HospitalDao;
import com.example.hospitals.domain.Hospital;
import com.example.hospitals.parser.HospitalParser;
import com.example.hospitals.parser.ReadLineContext;
import com.example.hospitals.service.HospitalService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospital/v1/api")
public class HospitalController {
    private final HospitalDao hospitalDao;
    private final HospitalService hospitalService;

    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
        this.hospitalService = new HospitalService(new ReadLineContext<>(new HospitalParser()), hospitalDao);
    }

    @DeleteMapping("/blank")
    public void deleteInfo() {
        hospitalDao.delete();
    }

    @GetMapping("/info/{id}")
    public String getFromId(@PathVariable String id) {
        Hospital hospital = hospitalDao.findById(id);
        return String.format("이름: %s\n주소: %s\n도로명주소: %s\n의료진 수: %s\n병상 수: %s\n면적: %s\n폐업 여부: %b\n", hospital.getHospitalName(),
                hospital.getFullAddress(), hospital.getRoadNameAddress(), hospital.getHealthcareProviderCount(),
                hospital.getTotalNumberOfBeds(), hospital.getTotalAreaSize(), hospitalService.isClosed(hospital.getBusinessStatusCode()));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Hospital> getIfNotNull(@PathVariable String id) {
        Hospital hospital = hospitalDao.findById(id);
        Optional<Hospital> opt = Optional.of(hospital);
        if (!opt.isEmpty()) return ResponseEntity.ok().body(hospital);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Hospital());
    }
    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        int cnt = hospitalDao.getCount();
        return ResponseEntity.ok().body(cnt);
    }


}

