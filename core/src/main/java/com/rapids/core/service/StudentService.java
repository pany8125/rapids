package com.rapids.core.service;

import com.rapids.core.domain.Knowledge;
import com.rapids.core.domain.Pack;
import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.StuPackRela;
import com.rapids.core.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by scott on 3/13/17.
 */
@Service
public class StudentService {
    @Autowired
    private StuPackRelaRepo stuPackRelaRepo;
    @Autowired
    private StuKnowledgeRelaRepo stuKnowledgeRelaRepo;

    public List<StuPackRela> getByStu(long studentId) {
        return stuPackRelaRepo.findByStudentId(studentId);
    }


    public List<StuKnowledgeRela> getKnowByStu(long studentId) {
        return stuKnowledgeRelaRepo.findByStudentIdAndPackId(studentId, -1L);
    }

    @Transactional
    public void delRela(long studentId, long packId) {
        stuKnowledgeRelaRepo.deleteByStudentIdAndPackId(studentId, packId);
        stuPackRelaRepo.deleteByStudentIdAndPackId(studentId, packId);
    }

    @Transactional
    public boolean savaRela(long studentId, Pack pack, List<Knowledge> knowledges) {
        StuPackRela stuPackRela = new StuPackRela();
        stuPackRela.setStudentId(studentId);
        stuPackRela.setPackId(pack.getId());
        stuPackRela.setCreateTime(new Date());
        stuPackRela.setPackName(pack.getName());
        stuPackRela.setLearnedNum(0);
        stuPackRela.setKnowledgeNum(knowledges.size());
        if (null == stuPackRelaRepo.save(stuPackRela)) {
            return false;
        }
        List<StuKnowledgeRela> stuKnowledgeRelas = new ArrayList<>();
        for (Knowledge k : knowledges) {
            StuKnowledgeRela stuKnowledgeRela = new StuKnowledgeRela();
            stuKnowledgeRela.setCreateTime(new Date());
            stuKnowledgeRela.setPackId(pack.getId());
            stuKnowledgeRela.setStudentId(studentId);
            stuKnowledgeRela.setKnowledgeId(k.getId());
            stuKnowledgeRela.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
            stuKnowledgeRelas.add(stuKnowledgeRela);
        }
        if (null == stuKnowledgeRelaRepo.save(stuKnowledgeRelas)) {
            return false;
        }
        return true;
    }
}
