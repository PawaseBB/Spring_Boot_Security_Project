USE `employee_directory`;

DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `members`;

CREATE TABLE `members`
                      (
   `user_id` varchar (50) NOT NULL,
   `pw` char (68) NOT NULL,
   `active` tinyint NOT NULL,
	PRIMARY KEY (`user_id`)
  )ENGINE=InnoDB DEFAULT CHARSET=latin1;
  
-- This bcryt password is `test123`
  
INSERT INTO `members`
VALUES
('john','{bcrypt}$2a$10$V29V35VZBivZhfc2Xf50neultcCcVHZDHJOJe4OwQYt6jvF1zxW7K',1),
('mary','{bcrypt}$2a$10$13OwGnVMUJAI8Ag/J8ztvuxYARMTsGfJEG5qJZwPdNmGDeDKx5dfO',1),
('susan','{bcrypt}$2a$10$H/16RRyseaHyKyBH/gl77.yrnAuvYAmpkHRh/LEJeASZh5syuuT4O',1);

CREATE TABLE `roles` (
   `user_id` varchar (50) NOT NULL,
   `role` varchar (50) NOT NULL,
   unique key `authorities_idx_1` (`user_id`, `role`),
   constraint `authorities_ibfk_1` foreign key (`user_id`) references `members` (`user_id`)
   )ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
INSERT INTO `roles`
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');
   

