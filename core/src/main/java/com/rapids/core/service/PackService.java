package com.rapids.core.service;

import com.rapids.core.domain.*;
import com.rapids.core.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Service
public class PackService {
    @Autowired
    private PackRepo packRepo;
    @Autowired
    private KnowledgeRepo knowledgeRepo;

    public Pack save(Pack pack){
        return packRepo.save(pack);
    }

    public long countByPackId(Long packId){
        return knowledgeRepo.countByPackId(packId);
    }

    public long countPack(){
        return packRepo.count();
    }

    public Knowledge saveKnowledge(Knowledge knowledge){
        return knowledgeRepo.save(knowledge);
    }


    public Pack getById(Long id){
        return packRepo.findOne(id);
    }

    public Knowledge getKnowledgeById(Long id){
        return knowledgeRepo.findOne(id);
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
