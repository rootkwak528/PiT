package com.ssafy.pit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -2030911159L;

    public static final QEvent event = new QEvent("event");

    public final StringPath eventContent = createString("eventContent");

    public final DateTimePath<java.util.Date> eventEndTime = createDateTime("eventEndTime", java.util.Date.class);

    public final StringPath eventImage = createString("eventImage");

    public final NumberPath<Integer> eventNo = createNumber("eventNo", Integer.class);

    public final DateTimePath<java.util.Date> eventRegDate = createDateTime("eventRegDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> eventStartTime = createDateTime("eventStartTime", java.util.Date.class);

    public final StringPath eventTitle = createString("eventTitle");

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

