package com.example.hospitals.service;

import com.example.hospitals.dao.HospitalDao;
import com.example.hospitals.domain.Hospital;
import com.example.hospitals.parser.ReadLineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class HospitalService {

    private final ReadLineContext<Hospital> hospitalReadLineContext;
    private final HospitalDao hospitalDao;

    public HospitalService(ReadLineContext<Hospital> hospitalReadLineContext, HospitalDao hospitalDao) {
        this.hospitalReadLineContext = hospitalReadLineContext;
        this.hospitalDao = hospitalDao;
    }

    @Transactional
    public int insertLargeVolumeHospitalData(String filename) throws IOException {
        int cnt = 0;
        List<Hospital> hospitalList = null;
        try {
            hospitalList = hospitalReadLineContext.readLines(filename);
            for (Hospital hospital : hospitalList) {
                try {
                    hospitalDao.add(hospital);
                    cnt++;
                } catch (IOException e) {
                    System.out.println("파일 내용 %s 에 문제가 있습니다.");
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cnt;
    }

    public boolean isClosed(int businessStatusCode) {
        return businessStatusCode == 13;
    }
}
