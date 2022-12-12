package com.dogaozdemir.iodigitalapi.model;

import com.dogaozdemir.iodigitalapi.dto.TedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigInteger;


@Document(collection = "ted")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ted {

    @Id
    private String id;

    private String title;

    private String author;

    private String date;

    private BigInteger views;

    private BigInteger likes;

    private String link;

    public TedDto convertToDto(){
        return TedDto.builder()
                .author(author)
                .title(title)
                .date(date)
                .views(views)
                .likes(likes)
                .link(link)
                .build();
    }

}
