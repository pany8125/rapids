package com.rapids.manage.controller;

import com.rapids.core.domain.Sample;
import com.rapids.core.repo.SampleRepo;
import com.rapids.core.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author David on 17/2/24.
 */
@RestController
@RequestMapping("/sample")
@SuppressWarnings("unused")
public class SampleController {

    private @Autowired SampleRepo sampleRepo;
    private @Autowired SampleService sampleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void c(@RequestBody Sample sample) {
        sampleRepo.save(sample);

    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<Sample> r(@PageableDefault(page = 1) Pageable page, PagedResourcesAssembler assembler) {
        return assembler.toResource(sampleRepo.findAll(page));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void u(@PathVariable Integer id, @RequestBody Sample sample) {
        Sample dataBean = sampleRepo.findOne(id);
        LOGGER.info("get sample: {}", sample);
        BeanUtils.copyProperties(sample, dataBean, "id");
        sampleService.save(dataBean);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void d(@PathVariable Integer id) {
        sampleRepo.delete(id);
    }


}
