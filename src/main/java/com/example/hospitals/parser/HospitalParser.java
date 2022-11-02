package com.example.hospitals.parser;

import com.example.hospitals.domain.Hospital;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class HospitalParser implements Parser<Hospital>{
    @Override
    public Hospital parse(String line) {
        String[] row = line.split("\",\"");


        Hospital hospital = new Hospital();
        row[0] = row[0].replaceAll("\"", "");
        hospital.setId(Integer.parseInt(row[0]));
        hospital.setOpenServiceName(row[1]);
        hospital.setOpenLocalGovernmentCode(Integer.parseInt(row[3]));
        hospital.setManagementNumber(row[4]);
        hospital.setLicenseDate(LocalDateTime.of(Integer.parseInt(row[5].substring(0,4)), Integer.parseInt(row[5].substring(4,6)),
                Integer.parseInt(row[5].substring(6,8)), 0, 0, 0));
        hospital.setBusinessStatus(Integer.parseInt(row[7]));
        hospital.setBusinessStatusCode(Integer.parseInt(row[9]));
        hospital.setPhone(row[15]);
        hospital.setFullAddress(row[18]);
        hospital.setRoadNameAddress(row[19]);
        hospital.setHospitalName(row[21]);
        hospital.setBusinessTypeName(row[25]);
        hospital.setHealthcareProviderCount(Integer.parseInt(row[29]));
        hospital.setPatientRoomCount(Integer.parseInt(row[30]));
        hospital.setTotalNumberOfBeds(Integer.parseInt(row[31]));
        hospital.setTotalAreaSize(Float.parseFloat(row[32]));

        row[row.length-1] = row[row.length-1].replaceAll("\"", "");

        return hospital;

    }
}
