CREATE TABLE prayer_times (
  id INT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  fajr_start TIME NOT NULL,
  sunrise TIME NOT NULL,
  dhuhr_start TIME NOT NULL,
  asr_start TIME NOT NULL,
  maghrib_start TIME NOT NULL,
  isha_start TIME NOT NULL,
  fajr_jamat TIME NOT NULL,
  dhuhr_jamat TIME NOT NULL,
  asr_jamat TIME NOT NULL,
  maghrib_jamat TIME NOT NULL,
  isha_jamat TIME NOT NULL
);