CREATE TABLE disponibilidade (
                                 id UUID NOT NULL,
                                 criado_por UUID,
                                 criado_em TIMESTAMP WITHOUT TIME ZONE,
                                 atualizado_por UUID,
                                 atualizado_em TIMESTAMP WITHOUT TIME ZONE,
                                 psicologo_id UUID NOT NULL,
                                 dia_semana VARCHAR(255) NOT NULL,
                                 codigo_dia INTEGER NOT NULL,
                                 hora_inicio TIME WITHOUT TIME ZONE NOT NULL,
                                 hora_fim TIME WITHOUT TIME ZONE NOT NULL,
                                 ativo BOOLEAN NOT NULL,
                                 CONSTRAINT disponibilidade_pkey PRIMARY KEY (id),
                                 CONSTRAINT fk_disponibilidade_psicologo FOREIGN KEY (psicologo_id) REFERENCES psicologo (id)
);

CREATE TABLE consulta (
                          id UUID NOT NULL,
                          criado_por UUID,
                          criado_em TIMESTAMP WITHOUT TIME ZONE,
                          atualizado_por UUID,
                          atualizado_em TIMESTAMP WITHOUT TIME ZONE,
                          psicologo_id UUID NOT NULL,
                          paciente_id UUID NOT NULL,
                          data_hora_inicio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          data_hora_fim TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          valor NUMERIC(38,2) NOT NULL,
                          convenio VARCHAR(255) NOT NULL,
                          observacoes VARCHAR(255),
                          CONSTRAINT consulta_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_consulta_psicologo FOREIGN KEY (psicologo_id) REFERENCES psicologo (id),
                          CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES paciente (id)
);