-- DDL --
-- Удаляем таблицы, чтобы пересоздать заново. http://www.postgresqltutorial.com/postgresql-drop-table/
DROP TABLE IF EXISTS run;

-- Создание таблиц. http://www.postgresqltutorial.com/postgresql-create-table/
CREATE TABLE public.run (
  id SERIAL PRIMARY KEY, -- http://www.postgresqltutorial.com/postgresql-primary-key/
  start_time timestamp NOT NULL,
  pipeline_id INTEGER REFERENCES public.pipeline(id) NOT NULL,
  stage_id INTEGER REFERENCES public.stage(id) NOT NULL,
  job_id INTEGER REFERENCES public.job(id) NOT NULL,
  status VARCHAR(16) NOT NULL,
  description TEXT
);
--CREATE USER wui WITH encrypted password 'q1w2e3';
GRANT ALL PRIVILEGES on ALL SEQUENCES IN SCHEMA public to wui;
GRANT ALL PRIVILEGES on ALL TABLES IN SCHEMA public to wui;
