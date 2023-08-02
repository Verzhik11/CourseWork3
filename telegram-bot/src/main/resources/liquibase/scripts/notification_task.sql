-- liquibase formatted sql

-- changeset verzhik:1
CREATE TABLE notification_task (
    id SERIAL,
    id_telegram BIGSERIAL,
    user_name TEXT,
    notification TEXT,
    notification_send_time TIMESTAMP
);

-- changeset verzhik: 2
ALTER TABLE notification_task
ADD PRIMARY KEY (id);