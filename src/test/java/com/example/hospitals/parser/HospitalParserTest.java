package com.example.hospitals.parser;

import com.example.hospitals.dao.HospitalDao;
import com.example.hospitals.domain.Hospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // @ExtendWith 와 @ConfigurationContext 를 대체한다.
class HospitalParserTest {
    String line1 = "\"1\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100004\",\"19990612\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-515-2875\",\"\",\"500881\",\"광주광역시 북구 풍향동 565번지 4호 3층\",\"광주광역시 북구 동문대로 24, 3층 (풍향동)\",\"61205\",\"효치과의원\",\"20211115113642\",\"U\",\"2021-11-17 02:40:00.0\",\"치과의원\",\"192630.735112\",\"185314.617632\",\"치과의원\",\"1\",\"0\",\"0\",\"52.29\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\"";
    String line2 = "\"2\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100005\",\"19990707\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-574-2802\",\"\",\"500867\",\"광주광역시 북구 일곡동 821번지 1호 2층\",\"광주광역시 북구 설죽로 518, 2층 (일곡동)\",\"61041\",\"일곡부부치과의원\",\"20170905183213\",\"I\",\"2018-08-31 23:59:59.0\",\"치과의원\",\"190646.777107\",\"189589.427851\",\"치과의원\",\"2\",\"0\",\"0\",\"200\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\"";
    String line3 = "\"3\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100006\",\"19990713\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-575-2875\",\"\",\"\",\"광주광역시 북구 일곡동 841번지 6호\",\"광주광역시 북구 설죽로 495, 3층 (일곡동)\",\"61040\",\"사랑이가득한치과의원\",\"20190730134859\",\"U\",\"2019-08-01 02:40:00.0\",\"치과의원\",\"190645.299575\",\"189353.47816\",\"치과의원\",\"1\",\"0\",\"0\",\"128\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\",";
    @Autowired
    ReadLineContext<Hospital> hospitalReadLineContext; // hospitalReadLineContext 라는 이름을 가진 메서드를 @Configuration 클래스의 @Bean 메서드 중에서 찾는다.

    @Autowired
    HospitalDao hospitalDao;

    @BeforeEach
    void setUp() throws IOException {
        hospitalDao.delete();
        HospitalParser hospitalParser = new HospitalParser();
        hospitalDao.add(hospitalParser.parse(line2));
        hospitalDao.add(hospitalParser.parse(line3));
    }

    @Test
    @DisplayName("Select")
    void findTest() throws IOException {
        String name = hospitalDao.findById("2");
        assertEquals("일곡부부치과의원", name);
    }

    @Test
    @DisplayName("Insert")
    void add() throws IOException {
        HospitalParser hospitalParser = new HospitalParser();
        hospitalDao.add(hospitalParser.parse(line1));
    }

    @Test
    @DisplayName("Count")
    void countAll() {
        assertEquals(2, hospitalDao.getCount());
    }

    @Test
    @DisplayName("Number of rows")
    void name() throws IOException {
        // 이러한 파일 명은 서버 환경에서 실행 시 문제가 될 수 있지만, 코드 자체에 포함시키기에는 부담스럽다.
        String filename = "C:\\LikeLion\\2022.10\\ing\\spring-boot\\fulldata_01_01_02_P_의원 (3).csv";
        List<Hospital> hospitalList = hospitalReadLineContext.readLines(filename);
        System.out.println(hospitalList.size());
        assertTrue(hospitalList.size() > 10000);
        assertTrue(hospitalList.size() > 100000);
    }

    @DisplayName("Parse test")
    @Test
    void convertToHospital() {
        HospitalParser hospitalParser = new HospitalParser();
        Hospital hospital = hospitalParser.parse(line1);

        assertEquals(1, hospital.getId()); // col:0
        assertEquals("의원", hospital.getOpenServiceName()); // col:1
        assertEquals(3620000, hospital.getOpenLocalGovernmentCode()); // col:3
        assertEquals("PHMA119993620020041100004",hospital.getManagementNumber()); // col:4
        assertEquals(LocalDateTime.of(1999, 6, 12, 0, 0, 0), hospital.getLicenseDate()); //19990612 //col:5
        assertEquals(1, hospital.getBusinessStatus()); //col:7
        assertEquals(13, hospital.getBusinessStatusCode());//col:9
        assertEquals("062-515-2875", hospital.getPhone());//col:14
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress()); //col:18
        assertEquals("광주광역시 북구 동문대로 24, 3층 (풍향동)", hospital.getRoadNameAddress());//col:19
        assertEquals("효치과의원", hospital.getHospitalName());//col:21
        assertEquals("치과의원", hospital.getBusinessTypeName());//col:25
        assertEquals(1, hospital.getHealthcareProviderCount()); //col:30
        assertEquals(0, hospital.getPatientRoomCount()); //col:31
        assertEquals(0, hospital.getTotalNumberOfBeds()); //col:32
        assertEquals(52.29f, hospital.getTotalAreaSize()); //col:33
    }
}