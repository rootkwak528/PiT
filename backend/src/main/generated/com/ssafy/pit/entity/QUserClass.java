package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserClass is a Querydsl query type for UserClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserClass extends EntityPathBase<UserClass> {

    private static final long serialVersionUID = 1923470236L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserClass userClass = new QUserClass("userClass");

    public final QClasses classes;

    public final StringPath classPermission = createString("classPermission");

    public final ListPath<Comment, QComment> Comments = this.<Comment, QComment>createList("Comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final QUser user;

    public final NumberPath<Integer> userClassNo = createNumber("userClassNo", Integer.class);

    public QUserClass(String variable) {
        this(UserClass.class, forVariable(variable), INITS);
    }

    public QUserClass(Path<? extends UserClass> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserClass(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserClass(PathMetadata metadata, PathInits inits) {
        this(UserClass.class, metadata, inits);
    }

    public QUserClass(Class<? extends UserClass> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classes = inits.isInitialized("classes") ? new QClasses(forProperty("classes"), inits.get("classes")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

