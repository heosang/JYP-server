package io.dot.jyp.server.application;

import io.dot.jyp.server.domain.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(
            GroupRepository groupRepository
    ) {
        this.groupRepository = groupRepository;
    }


}
