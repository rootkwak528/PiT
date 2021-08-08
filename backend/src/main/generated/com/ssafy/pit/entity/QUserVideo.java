package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserVideo is a Querydsl query type for UserVideo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserVideo extends EntityPathBase<UserVideo> {

    private static final long serialVersionUID = 1940930207L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserVideo userVideo = new QUserVideo("userVideo");

    public final ListPath<Feedback, QFeedback> feedbacks = this.<Feedback, QFeedback>createList("feedbacks", Feedback.class, QFeedback.class, PathInits.DIRECT2);

    public final QPtroom ptroom;

    public final QUser user;

    public final NumberPath<Integer> videoNo = createNumber("videoNo", Integer.class);

    public final DateTimePath<java.util.Date> videoSavetime = createDateTime("videoSavetime", java.util.Date.class);

    public final StringPath videoUrl = createString("videoUrl");

    public QUserVideo(String variable) {
        this(UserVideo.class, forVariable(variable), INITS);
    }

    public QUserVideo(Path<? extends UserVideo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserVideo(PathMetadata metadata, PathInits inits) {
        this(UserVideo.class, metadata, inits);
    }

    public QUserVideo(Class<? extends UserVideo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptroom = inits.isInitialized("ptroom") ? new QPtroom(forProperty("ptroom"), inits.get("ptroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

