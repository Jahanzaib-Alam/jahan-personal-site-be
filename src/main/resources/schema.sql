CREATE TABLE mosque (
	id INT NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	jumah_time time NOT NULL,
	jumah_time_dst time NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE prayer_times (
  id int NOT NULL AUTO_INCREMENT,
  mosque_id int NOT NULL,
  date date NOT NULL,
  fajr_start time NOT NULL,
  sunrise time NOT NULL,
  dhuhr_start time NOT NULL,
  asr_start time NOT NULL,
  maghrib_start time NOT NULL,
  isha_start time NOT NULL,
  fajr_jamat time NOT NULL,
  dhuhr_jamat time NOT NULL,
  asr_jamat time NOT NULL,
  maghrib_jamat time NOT NULL,
  isha_jamat time NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (mosque_id) REFERENCES mosque(id)
);