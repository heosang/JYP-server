package io.dot.jyp.server.domain;


import lombok.Getter;
import org.apache.coyote.Response;

import java.util.Arrays;

@Getter
public enum Permission {
    // Org Operation Permission
    CREATE_GROUP(Name.CREATE_GROUP, Resource.Type.GROUP),
    CREATE_GROUP_ACCOUNTS(Name.CREATE_GROUP_ACCOUNTS, Resource.Type.GROUP),

    // Common Group Permission
    CHANGE_GROUP_NAME(Name.CHANGE_GROUP_NAME, Resource.Type.GROUP),
    INVITE_GROUP_ACCOUNTS(Name.INVITE_GROUP_ACCOUNTS, Resource.Type.GROUP),
    RE_INVITE_GROUP_ACCOUNTS(Name.RE_INVITE_GROUP_ACCOUNTS, Resource.Type.GROUP),
    CANCEL_INVITE_GROUP_ACCOUNT(Name.CANCEL_INVITE_GROUP_ACCOUNT, Resource.Type.GROUP),
    DELETE_GROUP_ACCOUNT(Name.DELETE_GROUP_ACCOUNT, Resource.Type.GROUP),
    GET_GROUP_ACCOUNTS(Name.GET_GROUP_ACCOUNTS, Resource.Type.GROUP),
    CHANGE_GROUP_ACCOUNT_ROLES(Name.CHANGE_GROUP_ACCOUNT_ROLES, Resource.Type.GROUP),

    READY_GAME_ACCOUNTS(Name.READY_GAME_ACCOUNTS, Resource.Type.GAME),
    START_GAME(Name.START_GAME, Resource.Type.GAME),
    REQUEST_APPEAL_ACCOUNTS(Name.REQUEST_APPEAL_ACCOUNTS, Resource.Type.GAME),
    RESTART_GAME(Name.RESTART_GAME, Resource.Type.GAME),
    APPROVE_APPEAL(Name.APPROVE_APPEAL, Resource.Type.GAME),
    DECLINE_APPEAL(Name.DECLINE_APPEAL, Resource.Type.GAME);


    private final String name;
    private final Resource.Type target;

    Permission(String name, Resource.Type target) {
        this.name = name;
        this.target = target;
    }

    public static Permission of(String name) {
        return Arrays.stream(values())
                .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("'%s' is not supported permission", name))
                );
    }

    public static class Name {
        public static final String CREATE_GROUP = "CREATE_GROUP";
        public static final String CREATE_GROUP_ACCOUNTS = "CREATE_GROUP_ACCOUNTS";

        public static final String CHANGE_GROUP_NAME = "CHANGE_GROUP_NAME";
        public static final String INVITE_GROUP_ACCOUNTS = "INVITE_GROUP_ACCOUNTS";
        public static final String RE_INVITE_GROUP_ACCOUNTS = "RE_INVITE_GROUP_ACCOUNTS";
        public static final String CANCEL_INVITE_GROUP_ACCOUNT = "CANCEL_INVITE_GROUP_ACCOUNT";
        public static final String DELETE_GROUP_ACCOUNT = "DELETE_GROUP_ACCOUNT";
        public static final String GET_GROUP_ACCOUNTS = "GET_GROUP_ACCOUNTS";
        public static final String CHANGE_GROUP_ACCOUNT_ROLES = "CHANGE_GROUP_ACCOUNT_ROLES";

        public static final String READY_GAME_ACCOUNTS = "READY_GAME_ACCOUNTS";
        public static final String START_GAME = "START_GAME";
        public static final String REQUEST_APPEAL_ACCOUNTS = "REQUEST_APPEAL_ACCOUNTS";
        public static final String RESTART_GAME = "RESTART_GAME";
        public static final String APPROVE_APPEAL = "APPROVE_APPEAL";
        public static final String DECLINE_APPEAL = "DECLINE_APPEAL";
    }
}
