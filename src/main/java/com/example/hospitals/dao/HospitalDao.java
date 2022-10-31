package com.example.hospitals.dao;

import com.example.hospitals.domain.Hospital;
import com.example.hospitals.parser.HospitalParser;
import com.example.hospitals.parser.ReadLineContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HospitalDao {

    private JdbcTemplate jdbcTemplate;
    private ReadLineContext<Hospital> readLineContext;
    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.readLineContext = new ReadLineContext<>(new HospitalParser());
    }

    public void add() {
        List<Hospital> hospitalList = readLineContext.readLines("C:\\LikeLion\\2022.10\\ing\\spring-boot\\fulldata_01_01_02_P_의원 (2).csv");

        for (Hospital hospital : hospitalList) {
            jdbcTemplate.update("INSERT INTO nation_wide_hospitals VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    hospital.getId(), hospital.getOpenServiceName(), hospital.getOpenLocalGovernmentCode(),
                    hospital.getManagementNumber(), hospital.getLicenseDate(), hospital.getBusinessStatus(),
                    hospital.getBusinessStatusCode(), hospital.getPhone(), hospital.getFullAddress(),
                    hospital.getRoadNameAddress(), hospital.getHospitalName(), hospital.getBusinessTypeName(),
                    hospital.getHealthcareProviderCount(), hospital.getPatientRoomCount(), hospital.getTotalNumberOfBeds(),
                    hospital.getTotalAreaSize());
        }

    }
}
