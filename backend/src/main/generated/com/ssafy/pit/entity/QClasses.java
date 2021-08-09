package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClasses is a Querydsl query type for Classes
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClasses extends EntityPathBase<Classes> {

    private static final long serialVersionUID = 439654165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClasses classes = new QClasses("classes");

    public final NumberPath<Integer> classCcnt = createNumber("classCcnt", Integer.class);

    public final StringPath classCurri = createString("classCurri");

    public final StringPath classDay = createString("classDay");

    public final StringPath classDesc = createString("classDesc");

    public final DatePath<java.util.Date> classEndDate = createDate("classEndDate", java.util.Date.class);

    public final StringPath classEndTime = createString("classEndTime");

    public final StringPath classLevel = createString("classLevel");

    public final NumberPath<Integer> classLimit = createNumber("classLimit", Integer.class);

    public final StringPath classMaterial = createString("classMaterial");

    public final NumberPath<Integer> classNo = createNumber("classNo", Integer.class);

    public final StringPath classPermission = createString("classPermission");

    public final ListPath<ClassPhoto, QClassPhoto> classPhotos = this.<ClassPhoto, QClassPhoto>createList("classPhotos", ClassPhoto.class, QClassPhoto.class, PathInits.DIRECT2);

    public final NumberPath<Integer> classPrice = createNumber("classPrice", Integer.class);

    public final DatePath<java.util.Date> classStartDate = createDate("classStartDate", java.util.Date.class);

    public final StringPath classStartTime = createString("classStartTime");

    public final NumberPath<Integer> classTcnt = createNumber("classTcnt", Integer.class);

    public final StringPath classTeacherName = createString("classTeacherName");

    public final StringPath classTitle = createString("classTitle");

    public final StringPath classType = createString("classType");

    public final NumberPath<Integer> classUcnt = createNumber("classUcnt", Integer.class);

    public final QPtroom ptroom;

    public final QUser user;

    public final ListPath<UserClass, QUserClass> userClasses = this.<UserClass, QUserClass>createList("userClasses", UserClass.class, QUserClass.class, PathInits.DIRECT2);

    public final ListPath<UserLikes, QUserLikes> userLikes = this.<UserLikes, QUserLikes>createList("userLikes", UserLikes.class, QUserLikes.class, PathInits.DIRECT2);

    public QClasses(String variable) {
        this(Classes.class, forVariable(variable), INITS);
    }

    public QClasses(Path<? extends Classes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClasses(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClasses(PathMetadata metadata, PathInits inits) {
        this(Classes.class, metadata, inits);
    }

    public QClasses(Class<? extends Classes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptroom = inits.isInitialized("ptroom") ? new QPtroom(forProperty("ptroom"), inits.get("ptroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

