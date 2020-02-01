-- DDL --
-- Удаляем таблицы, чтобы пересоздать заново. http://www.postgresqltutorial.com/postgresql-drop-table/
DROP TABLE IF EXISTS stage CASCADE;

-- Создание таблиц. http://www.postgresqltutorial.com/postgresql-create-table/
CREATE TABLE public.stage (
  id SERIAL PRIMARY KEY, -- http://www.postgresqltutorial.com/postgresql-primary-key/
  name VARCHAR(255) UNIQUE NOT NULL, -- https://www.postgresql.org/docs/10/datatype-character.html
  pipeline_id INTEGER REFERENCES public.pipeline(id) NOT NULL,
  previous_stage_id INTEGER,
  next_stage_id INTEGER
);
--CREATE USER wui WITH encrypted password 'q1w2e3';
GRANT ALL PRIVILEGES on ALL SEQUENCES IN SCHEMA public to wui;
GRANT ALL PRIVILEGES on ALL TABLES IN SCHEMA public to wui;
