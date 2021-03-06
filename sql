CREATE TABLE
  t_user
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  address VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  biz_date VARCHAR(8) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON
  UPDATE
  CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE=InnoDB DEFAULT CHARSET=utf8;