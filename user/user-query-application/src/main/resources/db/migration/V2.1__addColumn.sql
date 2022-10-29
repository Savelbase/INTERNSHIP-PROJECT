ALTER TABLE user_profile DROP COLUMN sms_notification;
ALTER TABLE user_profile DROP COLUMN push_notification;
alter table user_profile add column notifications jsonb;