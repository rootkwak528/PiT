package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassPhoto is a Querydsl query type for ClassPhoto
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClassPhoto extends EntityPathBase<ClassPhoto> {

    private static final long serialVersionUID = -1932634549L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassPhoto classPhoto = new QClassPhoto("classPhoto");

    public final QClasses classes;

    public final BooleanPath photoIsthumbnail = createBoolean("photoIsthumbnail");

    public final NumberPath<Integer> PhotoNo = createNumber("PhotoNo", Integer.class);

    public final StringPath photoUrl = createString("photoUrl");

    public QClassPhoto(String variable) {
        this(ClassPhoto.class, forVariable(variable), INITS);
    }

    public QClassPhoto(Path<? extends ClassPhoto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassPhoto(PathMetadata metadata, PathInits inits) {
        this(ClassPhoto.class, metadata, inits);
    }

    public QClassPhoto(Class<? extends ClassPhoto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classes = inits.isInitialized("classes") ? new QClasses(forProperty("classes"), inits.get("classes")) : null;
    }

}

