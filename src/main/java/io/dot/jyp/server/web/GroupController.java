package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GroupApplicationService;
import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.application.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupApplicationService groupApplicationSService;

    public GroupController(GroupApplicationService groupApplicationSService) {
        this.groupApplicationSService = groupApplicationSService;
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public GroupCreateResponse create(@RequestBody final GroupCreateRequest request) {
        return GroupCreateResponse.of(
                1,
                "똘망한 꽃사슴"
        );
    }
    @PostMapping("/in/{groupId}")
    @ResponseStatus(value = HttpStatus.OK)
    public GroupEntranceResponse entrance(
            @RequestBody final GroupCreateRequest request,
            @PathVariable final String groupId
    ) {
        return GroupEntranceResponse.of(
                2,
                "배고픈 두더지"
        );
    }
    @PostMapping("/message")
    @ResponseStatus(value = HttpStatus.OK)
    public GroupMessageResponse message(@RequestBody final GroupMessageRequest request) {
        return GroupMessageResponse.of(
                2,
                "안녕하세요",
                "배고픈 두더지"
        );
    }
}