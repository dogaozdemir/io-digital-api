package com.dogaozdemir.iodigitalapi.controller;

import com.dogaozdemir.iodigitalapi.dto.ActionStatus;
import com.dogaozdemir.iodigitalapi.dto.TedDto;
import com.dogaozdemir.iodigitalapi.model.Ted;
import com.dogaozdemir.iodigitalapi.service.TedService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

import static com.dogaozdemir.iodigitalapi.util.ApiConstants.*;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TedController {

    private final TedService tedService;


    @GetMapping("/search")
    @Operation(summary = API_SEARCH_TED_SUMMARY,
            description = API_SEARCH_TED_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = API_SEARCH_TED_200)
    })
    public ResponseEntity<List<TedDto>> search(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) BigInteger views,
                                               @RequestParam(required = false) BigInteger likes){

        var tedTalks= tedService.getTedTalks(author,title,views,likes);

        return ResponseEntity.ok(tedTalks);

    }

    @PostMapping("/create")
    @Operation(description = API_CREATE_TED_DESCRIPTION,
            summary = API_CREATE_TED_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = API_CREATE_TED_201),
            @ApiResponse(code = 404, message = API_CREATE_TED_404)
    })
    public ResponseEntity<Ted> create(@Valid @RequestBody TedDto tedDto){
        var tedTalk = tedService.create(tedDto);

        return ResponseEntity.ok(tedTalk);

    }
    @PutMapping("/update/{id}")
    @Operation(description = API_UPDATE_TED_DESCRIPTION,
            summary = API_UPDATE_TED_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = API_UPDATE_TED_200),
            @ApiResponse(code = 404, message = API_UPDATE_TED_404)
    })
    public ResponseEntity<Ted> update(@RequestBody TedDto tedDto,@PathVariable String id){
        var tedTalk = tedService.update(tedDto,id);

        return ResponseEntity.ok(tedTalk);

    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = API_DELETE_TED_DESCRIPTION,
            summary = API_DELETE_TED_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = API_DELETE_TED_200)
    })
    public ResponseEntity<ActionStatus> delete(@PathVariable String id){
        tedService.delete(id);
        return ResponseEntity.ok(ActionStatus.builder().status(true).message("Deleted").build());
    }


}
