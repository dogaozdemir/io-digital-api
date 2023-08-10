package com.dogaozdemir.iodigitalapi.service;

import com.dogaozdemir.iodigitalapi.dto.TedDto;
import com.dogaozdemir.iodigitalapi.exception.TedNotFoundException;
import com.dogaozdemir.iodigitalapi.model.Ted;
import com.dogaozdemir.iodigitalapi.repository.TedRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TedService {

    private static final Logger LOG = LoggerFactory.getLogger(TedService.class);


    private ResourceLoader resourceLoader;

    private TedRepository tedRepository;

    private MongoTemplate mongoTemplate;

    final Pageable pageableRequest = PageRequest.of(0, 30);

    private List<Ted> tedList = new ArrayList<>();

    @PostConstruct
    public void initTedData(){
        Resource resource = resourceLoader.getResource("classpath:static/data.csv");
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);


        List<Record> parseAllRecords ;
        try {
            parseAllRecords = parser.parseAllRecords(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            parseAllRecords.forEach(record -> {
                Ted ted = new Ted();
                ted.setTitle(record.getString("title"));
                ted.setAuthor(record.getString("author"));
                ted.setDate(record.getString("date"));
                if(isNumeric(record.getString("views"))){
                    ted.setViews(record.getBigInteger("views"));
                }else{
                    ted.setViews(BigInteger.ZERO);
                }
                if(isNumeric(record.getString("likes"))){
                    ted.setLikes(record.getBigInteger("likes"));
                }else {
                    ted.setViews(BigInteger.ZERO);
                }

                ted.setLink(record.getString("link"));



                tedList.add(ted);


            });


        }catch (Exception e){

        LOG.error("Something went wrong in initTedData function: " + e.getMessage());

        }finally {
            tedRepository.saveAll(tedList);
        }

    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public List<TedDto> getTedTalks(String author, String title, BigInteger views, BigInteger likes) {

        Criteria criteria = new Criteria();
        Query query = new Query().with(pageableRequest);
        List<Ted> tedTalks;

        if(null != author){
            criteria.and("author").regex(author);
        }
        if(null != title){
            criteria.and("title").regex(title);
        }
        if(null != views){
            criteria.and("views").is(views);
        }
        if(null != likes){
            criteria.and("likes").is(likes);
        }
        if(null == criteria){
             tedTalks =  mongoTemplate.find(query, Ted.class);
        }else{
            tedTalks = mongoTemplate.find(Query.query(criteria).with(pageableRequest),Ted.class);
        }

        return tedTalks.stream().map(Ted::convertToDto).collect(Collectors.toList());
    }

    public Ted create(TedDto tedDto) {

        return tedRepository.save(Ted.builder()
                .title(tedDto.getTitle())
                .author(tedDto.getAuthor())
                .date(tedDto.getDate())
                .views(tedDto.getViews())
                .likes(tedDto.getLikes())
                .link(tedDto.getLink())
                .build());

    }

    public void delete(String id) {
        var ted = findById(id);
        tedRepository.delete(ted);
    }

    public Ted findById(String id){
        Optional<Ted> ted = tedRepository.findById(id);
        if(ted.isPresent()){
            return ted.get();
        }else {
            throw new TedNotFoundException(id + " id not found");
        }
    }

    public Ted update(TedDto tedDto, String id) {
        var ted = findById(id);
        if(StringUtils.isNoneEmpty(tedDto.getTitle())){
            ted.setTitle(tedDto.getTitle());
        }
        if(StringUtils.isNoneEmpty(tedDto.getAuthor())){
            ted.setAuthor(tedDto.getAuthor());
        }
        if(StringUtils.isNoneEmpty(tedDto.getDate())){
            ted.setDate(tedDto.getDate());
        }
        if(null != tedDto.getViews()){
            ted.setViews(tedDto.getViews());
        }
        if(null != tedDto.getLikes()){
            ted.setLikes(tedDto.getLikes());
        }
        if(StringUtils.isNoneEmpty(tedDto.getLink())){
            ted.setLink(tedDto.getLink());
        }
        return tedRepository.save(ted);

    }
}
