package org.debugroom.mynavi.sample.cloudformation.backend.domain.service;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.dynamodb.SampleTable;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.jpa.entity.User;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.repository.dynamodb.SampleRepository;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.repository.jpa.UserRepository;
import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SampleServiceImpl implements SampleService{

    @Autowired(required = false)
    SampleRepository sampleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<SampleResource> getSamples() {
        Iterable<SampleTable> sampleTables = sampleRepository.findAll();
        return StreamSupport.stream(sampleTables.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SampleResource addSample(String message) {
        return sampleRepository.save(SampleTable.builder()
                .samplePartitionKey(UUID.randomUUID().toString())
                .sampleSortKey("1")
                .sampleText("[message] : " + message +
                        " [added At] " + (new Timestamp(System.currentTimeMillis()).toString()))
                .build());
    }

}
