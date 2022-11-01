package com.example.hospitals.dao;

import com.example.hospitals.domain.Hospital;
import com.example.hospitals.parser.HospitalParser;
import com.example.hospitals.parser.ReadLineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component // @SpringBootApplication 에서는 @Component 가 달려 있으면 빈으로 등록한다.
public class HospitalDao {

    private final JdbcTemplate jdbcTemplate;
    private ReadLineContext<Hospital> readLineContext;
    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.readLineContext = new ReadLineContext<>(new HospitalParser());
    }

    public void add(Hospital hospital) throws IOException {
        String sql = "INSERT INTO `nation_wide_hospitals` (`id`, `open_service_name`, `open_local_government_code`, `management_number`, `license_date`, `business_status`, `business_status_code`, `phone`, `full_address`, `road_name_address`, `hospital_name`, `business_type_name`, `healthcare_provider_count`, `patient_room_count`, `total_number_of_beds`, `total_area_size`)" +
                " VALUES (?, ?, ?," +
                " ?, ?, ?," +
                " ?, ?, ?," +
                " ?, ?, ?," +
                " ?, ?, ?," +
                " ?);";

        jdbcTemplate.update(sql,
                hospital.getId(), hospital.getOpenServiceName(), hospital.getOpenLocalGovernmentCode(),
                hospital.getManagementNumber(), hospital.getLicenseDate(), hospital.getBusinessStatus(),
                hospital.getBusinessStatusCode(), hospital.getPhone(), hospital.getFullAddress(),
                hospital.getRoadNameAddress(), hospital.getHospitalName(), hospital.getBusinessTypeName(),
                hospital.getHealthcareProviderCount(), hospital.getPatientRoomCount(), hospital.getTotalNumberOfBeds(),
                hospital.getTotalAreaSize());
        }

    public void delete() {
        jdbcTemplate.update("DELETE FROM nation_wide_hospitals");
    }

    public String findById(String id) {
        String sql = "SELECT `id`, `open_service_name`, `open_local_government_code`, `management_number`, `license_date`, `business_status`, `business_status_code`, `phone`, `full_address`, `road_name_address`, `hospital_name`, `business_type_name`, `healthcare_provider_count`, `patient_room_count`, `total_number_of_beds`, `total_area_size`" +
                "FROM nation_wide_hospitals WHERE id = ?";
        Hospital queryHospital = jdbcTemplate.queryForObject(sql, new RowMapper<Hospital>() {
            @Override
            public Hospital mapRow(ResultSet rs, int rowNum) throws SQLException {
                Hospital hospital = new Hospital();
                hospital.setId(rs.getInt("id"));
                hospital.setOpenServiceName(rs.getString("open_service_name"));
                hospital.setOpenLocalGovernmentCode(rs.getInt("open_local_government_code"));
                hospital.setManagementNumber(rs.getString("management_number"));
                hospital.setLicenseDate(LocalDateTime.of(Integer.parseInt(rs.getString("license_date").substring(0,4)), Integer.parseInt(rs.getString("license_date").substring(5,7)),
                        Integer.parseInt(rs.getString("license_date").substring(8,10)), 0, 0, 0));
                hospital.setBusinessStatus(rs.getInt("business_status"));
                hospital.setBusinessStatusCode(rs.getInt("business_status_code"));
                hospital.setPhone(rs.getString("phone"));
                hospital.setFullAddress(rs.getString("full_address"));
                hospital.setRoadNameAddress(rs.getString("road_name_address"));
                hospital.setHospitalName(rs.getString("hospital_name"));
                hospital.setBusinessTypeName(rs.getString("business_type_name"));
                hospital.setHealthcareProviderCount(rs.getInt("healthcare_provider_count"));
                hospital.setPatientRoomCount(rs.getInt("patient_room_count"));
                hospital.setTotalNumberOfBeds(rs.getInt("total_number_of_beds"));
                hospital.setTotalAreaSize(rs.getFloat("total_area_size"));

                return hospital;
            }
        }, id);

        return queryHospital.getHospitalName();
    }
}
