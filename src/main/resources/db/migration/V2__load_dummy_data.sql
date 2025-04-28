DO $$
DECLARE
    i INTEGER;
    num_iterations INTEGER := 1000; -- Defina o número de inserções desejadas
BEGIN
    FOR i IN 1..num_iterations LOOP
        IF i % 2 = 0 THEN
            -- Para iterações pares, insere a data reduzida em 60 dias
            INSERT INTO tasks (created_at, descricao) VALUES (NOW() - INTERVAL '60 days', 'Tarefa ' || i);
        ELSE
            -- Para iterações ímpares, insere a data atual
            INSERT INTO tasks (created_at, descricao) VALUES (NOW(), 'Tarefa ' || i);
        END IF;
    END LOOP;
END $$;