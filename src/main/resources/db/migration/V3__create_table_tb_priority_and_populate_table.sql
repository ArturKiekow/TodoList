CREATE Table tb_priority (
    priority_id SERIAL,
    priority_name TEXT NOT NULL,
    priority_value INTEGER NOT NULL,
    PRIMARY KEY (priority_id)
);

INSERT INTO tb_priority (priority_id, priority_name, priority_value)
    VALUES
        (1, 'low', 1),
        (2, 'normal', 2),
        (3, 'important', 3),
        (4, 'urgent', 4)
    ON CONFLICT (priority_id) DO NOTHING;