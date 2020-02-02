-- DDL --
-- Удаляем таблицы, чтобы пересоздать заново. http://www.postgresqltutorial.com/postgresql-drop-table/
DROP TABLE IF EXISTS robots_group_robots;

-- Создание таблиц. http://www.postgresqltutorial.com/postgresql-create-table/
CREATE TABLE public.robots_group_robots (
  robot_id INTEGER REFERENCES public.robot(id) NOT NULL, -- https://www.postgresql.org/docs/10/datatype-character.html
  robots_group_id INTEGER REFERENCES public.robots_group(id) NOT NULL,
  PRIMARY KEY(robot_id, robots_group_id)
);
--CREATE USER wui WITH encrypted password 'q1w2e3';
GRANT ALL PRIVILEGES on ALL SEQUENCES IN SCHEMA public to wui;
GRANT ALL PRIVILEGES on ALL TABLES IN SCHEMA public to wui;

