package com.rapids.manage.controller;

import com.rapids.core.domain.*;
import com.rapids.core.service.AdminService;
import com.rapids.core.service.PackService;
import com.rapids.manage.dto.ExtEntity;
import com.rapids.manage.dto.ExtStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * Created by scott on 3/22/17.
 */
@RestController
@RequestMapping("/pack")
public class PackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackController.class);

    @Autowired
    private PackService packService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ExtEntity<Pack> getPackList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Pack> list = this.packService.getPackList(page, limit);
        ExtEntity<Pack> entity = new ExtEntity<>();
        entity.setResult(packService.countPack());
        entity.setRows(list);
        LOGGER.info("getPackList");
        return entity;
    }

    @RequestMapping(value = "/packKnowledge", method = RequestMethod.GET)
    public ExtEntity<Knowledge> getPackKnowledge(
            @RequestParam(value = "packid") Long packId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Knowledge> list = this.packService.getKnowledgeList(packId, page, limit);
        ExtEntity<Knowledge> entity = new ExtEntity<>();
        entity.setResult(packService.countByPackId(packId));
        entity.setRows(list);
        LOGGER.info("getKnowledgeList");
        return entity;
    }

    @RequestMapping(value = "/getKnowledge")
    public ExtEntity<Knowledge> getKnowledgeListByTitle(@RequestParam("title") String title) {
        List<Knowledge> list = this.packService.getKnowledgeListByTitle(title);
        ExtEntity<Knowledge> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getKnowledgeListByTitle");
        return entity;
    }


    @RequestMapping(value = "/savePack", method = RequestMethod.POST)
    public ExtStatusEntity savePack(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("name") String name,
                                     @RequestParam("type") String type,
                                     @RequestParam("description") String description,
                                     @RequestParam("adminName") String adminName) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            Pack packDTO = new Pack();
            if (id == null) {
                packDTO.setCreateBy( URLDecoder.decode(adminName, "UTF-8"));
            } else {
                packDTO = this.packService.getById(id);
            }
            packDTO.setName(name);
            packDTO.setType(Pack.Type.valueOf(type));
            packDTO.setDescription(description);
            packDTO.setCreateTime(new Date());
            Pack pack = this.packService.save(packDTO);
            if (null == pack) {
                result.setMsg("添加或修改账号失败");
                result.setSuccess(false);
            } else {
                result.setMsg("succeed");
                result.setSuccess(true);
            }
        } catch (Exception ex) {
            LOGGER.error("save admin error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        LOGGER.info("savePack");
        return result;
    }

    @RequestMapping(value = "/saveKnowledge", method = RequestMethod.POST)
    public ExtStatusEntity saveKnowledge(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("packIdF") Long packId,
                                     @RequestParam("name") String name,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("descPic") String descPic,
                                     @RequestParam("memo") String memo,
                                     @RequestParam("memoPic") String memoPic,
                                     @RequestParam("adminName") String adminName) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            Knowledge knowledgeDTO = new Knowledge();
            if (id == null) {
                knowledgeDTO.setEditor(URLDecoder.decode(adminName, "UTF-8"));
                knowledgeDTO.setPackId(packId);
            } else {
                knowledgeDTO = this.packService.getKnowledgeById(id);
            }
            knowledgeDTO.setName(name);
            knowledgeDTO.setTitle(title);
            knowledgeDTO.setDescription(description);
            knowledgeDTO.setDescPic(descPic);
            knowledgeDTO.setMemo(memo);
            knowledgeDTO.setMemoPic(memoPic);
            Knowledge knowledge = this.packService.saveKnowledge(knowledgeDTO);
            if (null == knowledge) {
                result.setMsg("添加或修改账号失败");
                result.setSuccess(false);
            } else {
                result.setMsg("succeed");
                result.setSuccess(true);
            }
        } catch (Exception ex) {
            LOGGER.error("save admin error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        LOGGER.info("saveKnowledge");
        return result;
    }



    @RequestMapping(value = "/delKnowledge")
    public ExtStatusEntity delKnowledge(@RequestParam("id") Long id) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.packService.delKnowledge(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delKnowledge error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delKnowledge");
        return entity;
    }


    @RequestMapping(value = "/delPack")
    public ExtStatusEntity delPack(@RequestParam("id") Long id) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.packService.delPack(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delPack error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delPack");
        return entity;
    }

}
