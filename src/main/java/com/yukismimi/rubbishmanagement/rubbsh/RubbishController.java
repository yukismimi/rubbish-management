package com.yukismimi.rubbishmanagement.rubbsh;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class RubbishController {

    private RubbishServiceImpl rubbishService;

    @GetMapping("/rubbish")
    @ApiOperation(value = "根据名称查询垃圾", notes = "可通过num与page指定数量与页码,数据库中没有的垃圾数据会调用API进行查询并保存到数据库")
    public Iterable<Rubbish> findByName(@RequestParam(value = "name") String name,
                                        @RequestParam(value = "num", defaultValue = "1", required = false) int num,
                                        @RequestParam(value = "page", defaultValue = "1", required = false) int page)
            throws IOException, InterruptedException {
        return rubbishService.findByName(name, num, page);
    }

    @GetMapping("/rubbish/all/{type}")
    @ApiOperation(value = "根据类型查询全部垃圾", notes = "0为可回收、1为有害、2为厨余(湿)、3为其他(干)")
    public List<Rubbish> findAllByType(@PathVariable int type) {
        return rubbishService.findAllByType(type);
    }

    @ApiOperation("查询全部垃圾")
    @GetMapping("/rubbish/all")
    public List<Rubbish> findAll() {
        return rubbishService.findAll();
    }

    @ApiOperation("保存一种垃圾数据到数据库")
    @PostMapping("/rubbish")
    public Rubbish save(@RequestBody Rubbish rubbish) {
        return rubbishService.save(rubbish);
    }

    @ApiOperation("通过id删除数据库中的一条垃圾记录")
    @DeleteMapping("/rubbish/{id}")
    public void deleteById(@PathVariable Long id) {
        rubbishService.deleteById(id);
    }

    @Autowired
    public RubbishController(RubbishServiceImpl rubbishService) {
        this.rubbishService = rubbishService;
    }
}
