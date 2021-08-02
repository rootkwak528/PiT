package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLikes is a Querydsl query type for UserLikes
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserLikes extends EntityPathBase<UserLikes> {

    private static final long serialVersionUID = 1931701728L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLikes userLikes = new QUserLikes("userLikes");

    public final QClasses classes;

    public final QUser user;

    public final NumberPath<Integer> userClassNo = createNumber("userClassNo", Integer.class);

    public QUserLikes(String variable) {
        this(UserLikes.class, forVariable(variable), INITS);
    }

    public QUserLikes(Path<? extends UserLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLikes(PathMetadata metadata, PathInits inits) {
        this(UserLikes.class, metadata, inits);
    }

    public QUserLikes(Class<? extends UserLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classes = inits.isInitialized("classes") ? new QClasses(forProperty("classes"), inits.get("classes")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

