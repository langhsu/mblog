-- MySQL procedure
DROP PROCEDURE IF EXISTS procedure_v32;

DELIMITER //
CREATE PROCEDURE procedure_v32()
BEGIN
  IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema= DATABASE() AND table_name='mto_message' AND COLUMN_NAME='own_id'
  ) THEN
    ALTER TABLE `mto_message` CHANGE COLUMN `own_id` `user_id` bigint(20) NULL DEFAULT NULL;
  END IF;
  IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema= DATABASE() AND table_name='mto_comment' AND COLUMN_NAME='to_id'
  ) THEN
    ALTER TABLE `mto_comment` CHANGE COLUMN `to_id` `post_id` bigint(20) NULL DEFAULT NULL;
  END IF;
  IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema= DATABASE() AND table_name='mto_favorite' AND COLUMN_NAME='own_id'
  ) THEN
    ALTER TABLE `mto_favorite` CHANGE COLUMN `own_id` `user_id` bigint(20) NULL DEFAULT NULL;
  END IF;
END; //
DELIMITER;

CALL procedure_v32();
DROP PROCEDURE IF EXISTS procedure_v32;