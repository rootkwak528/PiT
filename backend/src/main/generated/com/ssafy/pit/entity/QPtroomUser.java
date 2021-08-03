package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPtroomUser is a Querydsl query type for PtroomUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPtroomUser extends EntityPathBase<PtroomUser> {

    private static final long serialVersionUID = -1547416581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPtroomUser ptroomUser = new QPtroomUser("ptroomUser");

    public final QPtroom ptroom;

    public final NumberPath<Integer> ptroomUserNo = createNumber("ptroomUserNo", Integer.class);

    public final QUser user;

    public QPtroomUser(String variable) {
        this(PtroomUser.class, forVariable(variable), INITS);
    }

    public QPtroomUser(Path<? extends PtroomUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPtroomUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPtroomUser(PathMetadata metadata, PathInits inits) {
        this(PtroomUser.class, metadata, inits);
    }

    public QPtroomUser(Class<? extends PtroomUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptroom = inits.isInitialized("ptroom") ? new QPtroom(forProperty("ptroom"), inits.get("ptroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

