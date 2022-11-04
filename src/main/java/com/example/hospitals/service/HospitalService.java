package com.example.hospitals.service;

import com.example.hospitals.dao.HospitalDao;
import com.example.hospitals.domain.Hospital;
import com.example.hospitals.parser.ReadLineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            /* 병렬 방식으로 리스트 내의 모든 문제가 없는 항목들을 DB 에 인서트한다. */
            hospitalList
                    .stream()
                    .parallel()
                    .forEach(hospital -> {
                        try {
                            this.hospitalDao.add(hospital);
                        } catch (IOException ex) {
                            System.out.printf("파일 아이디 %d 에 문제가 있습니다.\n", hospital.getId());
                            throw new RuntimeException(ex);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!Optional.of(hospitalList).isEmpty()) cnt = hospitalList.size();

        return cnt;
    }

    public boolean isClosed(int businessStatusCode) {
        return businessStatusCode == 13;
    }
}
