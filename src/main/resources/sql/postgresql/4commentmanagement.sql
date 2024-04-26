CREATE TABLE comment (
    id INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    comment_content VARCHAR(2000) NOT NULL,
    written_at TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL,
    post_id INTEGER
);

ALTER TABLE comment
    ADD CONSTRAINT fk_userin_comment
        FOREIGN KEY (user_id)
            REFERENCES userin(id);

ALTER TABLE comment
    ADD CONSTRAINT fk_post_comment
        FOREIGN KEY (post_id)
            REFERENCES post(id);