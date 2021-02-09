create table article (
    id bigint not null auto_increment,
    body varchar(255) not null,
    created_at datetime(6),
    description varchar(255) not null,
    modified_at datetime(6),
    slug varchar(255) not null,
    title varchar(255) not null,
    author_id bigint,
    primary key (id)
) engine=InnoDB;

create table article_favorites (
    article_id bigint not null,
    member_id bigint not null,
    primary key (article_id, member_id)
) engine=InnoDB;

create table article_tags (
    article_id bigint not null,
    tags_id bigint not null,
    primary key (article_id, tags_id)
) engine=InnoDB;

create table comment (
    id bigint not null auto_increment,
    body varchar(255) not null,
    created_at datetime(6),
    modified_at datetime(6),
    article_id bigint,
    author_id bigint,
    primary key (id)
) engine=InnoDB;

create table member (
    id bigint not null auto_increment,
    bio varchar(255),
    created_at datetime(6),
    email varchar(255) not null,
    image varchar(255),
    modified_at datetime(6),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table member_follows (
    follower_id bigint not null,
    following_id bigint not null,
    primary key (follower_id, following_id)
) engine=InnoDB;

create table tag (
    id bigint not null auto_increment,
    name varchar(255) not null,
    primary key (id)
) engine=InnoDB;

alter table article
    add constraint UK_lc76j4bqg2jrk06np18eve5yj unique (slug);

alter table member
    add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email);

alter table member
    add constraint UK_gc3jmn7c2abyo3wf6syln5t2i unique (username);

alter table tag
    add constraint UK_1wdpsed5kna2y38hnbgrnhi5b unique (name);

alter table article
    add constraint FKhsk0dkar5bxukvagpa0pcolrw
        foreign key (author_id)
            references member (id);

alter table article_favorites
    add constraint FKo114sgu6owyhdq23qs0tkm3hc
        foreign key (member_id)
            references member (id);

alter table article_favorites
    add constraint FKdvc7dl41ymynfseyl5lebxa92
        foreign key (article_id)
            references article (id);

alter table article_tags
    add constraint FKp6owh2p5p9yllwwrc2hn7bnxr
        foreign key (tags_id)
            references tag (id);

alter table article_tags
    add constraint FKhd7fyhyhf5bpbgw8rri8j3grv
        foreign key (article_id)
            references article (id);

alter table comment
    add constraint FK5yx0uphgjc6ik6hb82kkw501y
        foreign key (article_id)
            references article (id);

alter table comment
    add constraint FK2bam3knj13ijq6eiskx55xtqh
        foreign key (author_id)
            references member (id);

alter table member_follows
    add constraint FK4esgi2atauqnvjau7mermmuqb
        foreign key (following_id)
            references member (id);

alter table member_follows
    add constraint FKsi8x0sf34ojshv4tjeeccp2me
        foreign key (follower_id)
            references member (id)