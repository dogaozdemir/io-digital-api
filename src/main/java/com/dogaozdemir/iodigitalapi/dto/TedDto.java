package com.dogaozdemir.iodigitalapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Builder
@Getter
@Setter
public class TedDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String date;
    @Min(value = 0, message = "Views must be at least {min} ")
    private BigInteger views;
    @Min(value = 0, message = "Likes must be at least {min} ")
    private BigInteger likes;
    @NotEmpty
    private String link;
}
