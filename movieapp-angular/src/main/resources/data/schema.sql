DROP TABLE IF EXISTS favorite;

CREATE TABLE favorite (
  userId INTEGER DEFAULT NULL,
  movieId INTEGER DEFAULT NULL
);

DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
  id INTEGER auto_increment,
  title varchar(100) DEFAULT NULL,
  description varchar(250) DEFAULT NULL,
  trailer varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user;

CREATE TABLE users (
  id INTEGER auto_increment,
  username varchar(45) DEFAULT NULL,
  password varchar(100) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user_roles (
  user_id INTEGER DEFAULT NULL,
  role_id INTEGER DEFAULT NULL
);

CREATE TABLE role (
  id INTEGER auto_increment,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);
