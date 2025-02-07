ALTER TABLE tasks
    RENAME TO tb_tasks;

ALTER TABLE tb_tasks
    RENAME id TO task_id;

ALTER TABLE tb_tasks
    RENAME priority TO priority_id;

ALTER TABLE tb_tasks
    ALTER COLUMN priority_id TYPE INTEGER
    USING priority_id::integer;