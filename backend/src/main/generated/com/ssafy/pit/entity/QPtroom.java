package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPtroom is a Querydsl query type for Ptroom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPtroom extends EntityPathBase<Ptroom> {

    private static final long serialVersionUID = 1779725328L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPtroom ptroom = new QPtroom("ptroom");

    public final QClasses classes;

    public final NumberPath<Integer> ptroomLimit = createNumber("ptroomLimit", Integer.class);

    public final NumberPath<Integer> ptroomNo = createNumber("ptroomNo", Integer.class);

    public final DateTimePath<java.util.Date> ptroomSavetime = createDateTime("ptroomSavetime", java.util.Date.class);

    public final StringPath ptroomSessionName = createString("ptroomSessionName");

    public final NumberPath<Integer> ptroomUcnt = createNumber("ptroomUcnt", Integer.class);

    public final ListPath<PtroomUser, QPtroomUser> ptroomUsers = this.<PtroomUser, QPtroomUser>createList("ptroomUsers", PtroomUser.class, QPtroomUser.class, PathInits.DIRECT2);

    public final ListPath<UserVideo, QUserVideo> userVideos = this.<UserVideo, QUserVideo>createList("userVideos", UserVideo.class, QUserVideo.class, PathInits.DIRECT2);

    public QPtroom(String variable) {
        this(Ptroom.class, forVariable(variable), INITS);
    }

    public QPtroom(Path<? extends Ptroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPtroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPtroom(PathMetadata metadata, PathInits inits) {
        this(Ptroom.class, metadata, inits);
    }

    public QPtroom(Class<? extends Ptroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classes = inits.isInitialized("classes") ? new QClasses(forProperty("classes"), inits.get("classes")) : null;
    }

}

