package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 2013170492L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<Classes, QClasses> classesList = this.<Classes, QClasses>createList("classesList", Classes.class, QClasses.class, PathInits.DIRECT2);

    public final QComment comment;

    public final ListPath<PtroomUser, QPtroomUser> ptroomUsers = this.<PtroomUser, QPtroomUser>createList("ptroomUsers", PtroomUser.class, QPtroomUser.class, PathInits.DIRECT2);

    public final ListPath<UserClass, QUserClass> userClasses = this.<UserClass, QUserClass>createList("userClasses", UserClass.class, QUserClass.class, PathInits.DIRECT2);

    public final StringPath userDesc = createString("userDesc");

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userGender = createString("userGender");

    public final ListPath<UserLikes, QUserLikes> userLikes = this.<UserLikes, QUserLikes>createList("userLikes", UserLikes.class, QUserLikes.class, PathInits.DIRECT2);

    public final StringPath userName = createString("userName");

    public final StringPath userNickname = createString("userNickname");

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public final StringPath userPhone = createString("userPhone");

    public final StringPath userProfile = createString("userProfile");

    public final StringPath userPwd = createString("userPwd");

    public final StringPath userType = createString("userType");

    public final ListPath<UserVideo, QUserVideo> userVideos = this.<UserVideo, QUserVideo>createList("userVideos", UserVideo.class, QUserVideo.class, PathInits.DIRECT2);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
    }

}

