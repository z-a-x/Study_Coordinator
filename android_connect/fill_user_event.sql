ALTER TABLE user_event
ADD FOREIGN KEY (event_id)
REFERENCES event (event_id)

ALTER TABLE user_event
ADD FOREIGN KEY (user_id)
REFERENCES user (user_id)


INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('1', '5')
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('2', '5')
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('3', '5');
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('4', '5');
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('5', '5');
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('1', '4');
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('2', '4');
INSERT INTO `study_coordinator`.`user_event` (`user_id`, `event_id`) VALUES ('3', '4');