package com.rapids.core.service;

import com.rapids.core.domain.*;
import com.rapids.core.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class PackService {
    @Autowired
    private PackRepo packRepo;
    @Autowired
    private KnowledgeRepo knowledgeRepo;
    @Autowired
    private StuPackRelaRepo stuPackRelaRepo;
    @Autowired
    private StuKnowledgeRelaRepo stuKnowledgeRelaRepo;

    public Pack save(Pack pack){
        return packRepo.save(pack);
    }

    public long countByPackId(Long packId){
        return knowledgeRepo.countByPackId(packId);
    }

    public long countPack(){
        return packRepo.count();
    }

    @Transactional
    public Knowledge saveKnowledge(Knowledge knowledge){
        Knowledge knowledgeR =  knowledgeRepo.save(knowledge);
        //添加关联学生
        List<StuPackRela> stuPackRelas = stuPackRelaRepo.findByPackId(knowledge.getPackId());
        List<StuKnowledgeRela> stuKnowledgeRelas = new ArrayList<>();
        for(StuPackRela stuPackRela : stuPackRelas){
            stuPackRela.setKnowledgeNum(stuPackRela.getKnowledgeNum()+1);//知识点总数+1
            stuPackRelaRepo.save(stuPackRela);
            StuKnowledgeRela stuKnowledgeRela = new StuKnowledgeRela();
            stuKnowledgeRela.setCreateTime(new Date());
            stuKnowledgeRela.setPackId(stuPackRela.getPackId());
            stuKnowledgeRela.setStudentId(stuPackRela.getStudentId());
            stuKnowledgeRela.setKnowledgeId(knowledgeR.getId());
            stuKnowledgeRela.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
            stuKnowledgeRelas.add(stuKnowledgeRela);
        }
        stuKnowledgeRelaRepo.save(stuKnowledgeRelas);
        return knowledgeR;
    }


    public Pack getById(Long id){
        return packRepo.findOne(id);
    }

    public Knowledge getKnowledgeById(Long id){
        return knowledgeRepo.findOne(id);
    }

    public List<Knowledge> getKnowledgeByName(String name){
        return knowledgeRepo.findByByName(name);
    }

    public List<Pack> getPackList(){
        return (List)packRepo.findAll();
    }

    public List<Pack> getPackList(Integer page, Integer limit){
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return packRepo.findAll(pageRequest).getContent();
    }

    public List<Knowledge> getKnowledgeList(Long packId, Integer page, Integer limit){
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return knowledgeRepo.findByPackId(packId, pageRequest).getContent();
    }


    @Transactional
    public void delPack(Long id){
        this.packRepo.delete(id);
    }


    @Transactional
    public void delKnowledge(Long id){
        this.knowledgeRepo.delete(id);
    }

    @Transactional
    public List<Knowledge> getKnowledgeListByTitle(String title){
        return this.knowledgeRepo.queryKnowledgeByTitle(title);
    }

    @Transactional
    public List<Knowledge> getKnowledgeListByPack(long packId){
        return this.knowledgeRepo.findByPackId(packId);
    }


}
