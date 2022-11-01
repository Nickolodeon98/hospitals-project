package com.example.hospitals.controller;

import com.example.hospitals.dao.HospitalDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hospital/v1/api")
public class HospitalController {
    private final HospitalDao hospitalDao;

    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @PostMapping("/info")
    public void insertInfo() throws IOException {
        hospitalDao.add();
    }

    @PostMapping("/blank")
    public void deleteInfo() {
        hospitalDao.delete();
    }


}
