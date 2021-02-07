package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GroupService;
import io.dot.jyp.server.application.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public GroupCreateResponse create(@RequestBody final GroupCreateRequest request) {
        return GroupCreateResponse.of(
                request.getTitle(),
                "testtestaaa"
        );
    }
}