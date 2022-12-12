package com.dogaozdemir.iodigitalapi.service;

import com.dogaozdemir.iodigitalapi.dto.TedDto;
import com.dogaozdemir.iodigitalapi.exception.TedNotFoundException;
import com.dogaozdemir.iodigitalapi.model.Ted;
import com.dogaozdemir.iodigitalapi.repository.TedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TedServiceTest {

    Ted ted;
    Ted anotherTed;

    TedDto tedDto;

    final Pageable pageableRequest = PageRequest.of(0, 30);


    @Mock
    TedRepository tedRepository;

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    TedService tedService;

    @BeforeEach
    void init(){
        ted = ted.builder().title("Climate action needs new frontline leadership")
                .id("6393903deb858e5d734013d7")
                .author("Ozawa Bineshi Albert")
                .date("Dec-21")
                .views(BigInteger.valueOf(404000))
                .likes(BigInteger.valueOf(12000))
                .link("https://ted.com/talks/ozawa_bineshi_albert_climate_action_needs_new_frontline_leadership")
                .build();

        anotherTed = ted.builder().title("Climate action needs new frontline leadership")
                .id("6393903deb858e5d734013d8")
                .author("Ozawa Bineshi Albert")
                .date("Dec-21")
                .views(BigInteger.valueOf(404000))
                .likes(BigInteger.valueOf(12000))
                .link("https://ted.com/talks/ozawa_bineshi_albert_climate_action_needs_new_frontline_leadership")
                .build();

        tedDto = tedDto.builder().title("Climate action needs new frontline leadership")
                .author("Ozawa Bineshi Albert")
                .date("Dec-21")
                .views(BigInteger.valueOf(404000))
                .likes(BigInteger.valueOf(12000))
                .link("https://ted.com/talks/ozawa_bineshi_albert_climate_action_needs_new_frontline_leadership")
                .build();
    }

    @Test
    @DisplayName("Find Ted Talk by id")
    void GivenTedId_WhenFindingTed_ThenReturnTed(){
        when(tedRepository.findById(anyString())).thenReturn(Optional.of(ted));

        Ted result = tedService.findById(ted.getId());

        assertThat(result.getAuthor()).isEqualTo(ted.getAuthor());
        assertThat(result.getId()).isEqualTo(ted.getId());
        verify(tedRepository,times(1)).findById(anyString());

    }

    @Test
    @DisplayName("Find non-existing Ted Talk by id")
    void GivenNonExistingTedId_WhenFindingTed_ThenThrowException(){
        when(tedRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tedService.findById(anyString())).isInstanceOf(TedNotFoundException.class);
        verify(tedRepository,times(1)).findById(anyString());

    }

    @Test
    @DisplayName("Find all Ted Talks by parameter")
    void GivenTedSearchParameter_WhenFindingAllTeds_ThenReturnTedList(){

        List<Ted> tedTalks = new ArrayList<>();
        tedTalks.add(ted);
        tedTalks.add(anotherTed);

        lenient().when(this.mongoTemplate.find(any(Query.class), eq(Ted.class)))
                .thenReturn(tedTalks);

        List<TedDto> result = tedService.getTedTalks(anyString(),anyString(),null,null);

        assertThat(result.size()).isEqualTo(2);

    }


    @Test
    @DisplayName("Successfully create new Ted Talk")
    void GivenTitleAuthorDateViewsLikesAndLikes_WhenCreatingTed_ThenReturnTed(){

        when(tedRepository.save(any())).thenReturn(ted);

        Ted result = tedService.create(tedDto);

        assertThat(result.getAuthor()).isEqualTo(ted.getAuthor());
        assertThat(result.getLink()).isEqualTo(ted.getLink());
        verify(tedRepository,times(1)).save(any());

    }

    @Test
    @DisplayName("Update Ted Talk")
    void GivenUpdatedValues_WhenUpdatingTed_ThenReturnUpdatedTed(){


        when(tedRepository.findById(anyString())).thenReturn(Optional.of(ted));


        when(tedRepository.save(any())).thenReturn(ted);


        Ted result = tedService.update(tedDto,"6393903deb858e5d734013d7");

        assertThat(result.getAuthor()).isEqualTo(ted.getAuthor());
        assertThat(result.getLink()).isEqualTo(ted.getLink());
        verify(tedRepository,times(1)).save(any());

    }

    @Test
    @DisplayName("Delete Ted Talk")
    void GivenTed_WhenDeletingTed_ThenSucceed(){
        when(tedRepository.findById(anyString())).thenReturn(Optional.of(ted));

        tedService.delete(anyString());

        verify(tedRepository,times(1)).delete(any());

    }

    //TODO init data test


}