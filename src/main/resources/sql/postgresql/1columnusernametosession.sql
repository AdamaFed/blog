alter table session add Column user_id integer not null;

ALTER TABLE session
    ADD CONSTRAINT fk_userin_session
        FOREIGN KEY (user_id)
            REFERENCES userin(id);