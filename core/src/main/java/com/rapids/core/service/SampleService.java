package com.rapids.core.service;

import com.rapids.core.domain.Sample;
import com.rapids.core.repo.SampleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author David on 17/2/24.
 */
@Service
public class SampleService {
    private @Autowired SampleRepo sampleRepo;

    @Transactional
    public void save(Sample sample) {
        sampleRepo.save(sample);
    }
}
