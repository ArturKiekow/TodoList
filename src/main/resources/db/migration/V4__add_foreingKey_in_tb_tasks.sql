ALTER TABLE tb_tasks
ADD CONSTRAINT fk_priority
FOREIGN KEY (priority_id)
REFERENCES tb_priority (priority_id);