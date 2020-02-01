-- DDL --
-- Удаляем таблицы, чтобы пересоздать заново. http://www.postgresqltutorial.com/postgresql-drop-table/
DROP TABLE IF EXISTS job CASCADE;

-- Создание таблиц. http://www.postgresqltutorial.com/postgresql-create-table/
CREATE TABLE public.job (
  id SERIAL PRIMARY KEY, -- http://www.postgresqltutorial.com/postgresql-primary-key/
  name VARCHAR(255) UNIQUE NOT NULL, -- https://www.postgresql.org/docs/10/datatype-character.html
  task_name VARCHAR(255) NOT NULL,
  stage_id INTEGER REFERENCES public.stage(id) NOT NULL,
  repository_url VARCHAR(255) NOT NULL,
  robots_group_id INTEGER REFERENCES public.robots_group(id) NOT NULL,
  start_time timestamp NOT NULL,
  count INTEGER NOT NULL

);
--CREATE USER wui WITH encrypted password 'q1w2e3';
GRANT ALL PRIVILEGES on ALL SEQUENCES IN SCHEMA public to wui;
GRANT ALL PRIVILEGES on ALL TABLES IN SCHEMA public to wui;
