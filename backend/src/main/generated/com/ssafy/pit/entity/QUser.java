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

    public static final QUser user = new QUser("user");

    public final ListPath<Classes, QClasses> classesList = this.<Classes, QClasses>createList("classesList", Classes.class, QClasses.class, PathInits.DIRECT2);

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
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

