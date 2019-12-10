package com.yukismimi.rubbishmanagement.rubbsh;

import com.yukismimi.rubbishmanagement.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RubbishServiceImpl {

    private RubbishRepository repository;

    private Api api;

    public List<Rubbish> findByName(String name, int num, int page) throws IOException, InterruptedException {

        List<Rubbish> rubbishes = repository.findAllByName(name);
        if (rubbishes.size() > 0)
            return rubbishes;

        return api.call(name, num, page).getNewslist()
                .stream()
                .map(repository::save)
                .limit(num)
                .collect(Collectors.toList());
    }

    public List<Rubbish> findAllByType(int type) {
        return repository.findAllByType(type);
    }

    public List<Rubbish> findAll() {
        return repository.findAll();
    }

    public Rubbish save(Rubbish rubbish) {
        return repository.save(rubbish);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Autowired
    public RubbishServiceImpl(RubbishRepository repository, Api api) {
        this.repository = repository;
        this.api = api;
    }
}
