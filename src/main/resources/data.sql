-- Inserir categorias
INSERT INTO categoria (nome) VALUES
    ('FAUNA'),
    ('FLORA'),
    ('POLUIÇÃO'),
    ('ORDENAMENTO URBANO E PATRIMÔNIO CULTURAL'),
    ('ADMINISTRAÇÃO AMBIENTAL');

-- Inserir atividades ambientais para FAUNA
INSERT INTO atividade (descricao) VALUES
    ('Do transporte e comercialização de animais abatidos de forma ilegal.'),
    ('Pesca ilegal, predatória ou por meio de explosivos, ou substâncias que em contato com a água produzem efeito semelhante. Assim como, transportar ou comercializar espécies provenientes de tais atos.'),
    ('Caça ilegal ou predatória, de animais em extinção ou fora de época, bem como entrar em locais de conservação portando instrumentos próprios para a atividade.'),
    ('Ferir, praticar maus-tratos, abuso ou mutilação de qualquer animal silvestre.'),
    ('Experiências que possam causar dor e sofrimento aos animais.'),
    ('Emissão de efluentes, substâncias tóxicas ou outro meio proibido que possa provocar a morte ou extinção de espécies aquáticas.');

insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,1);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,2);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,3);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,4);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,5);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (1,6);

-- Inserir atividades ambientais para FLORA
INSERT INTO atividade (descricao) VALUES
    ('Destruir ou danificar florestas de preservação permanente, independentemente do estágio de formação.'),
    ('Destruir ou danificar qualquer vegetação do Bioma Mata Atlântica.'),
    ('Cortar árvores em florestas de preservação permanente, sem a devida permissão.'),
    ('Fabricar, vender, transportar ou soltar balões que podem provocar incêndios.'),
    ('Destruir, danificar, lesar ou maltratar, por qualquer meio ou modo, plantas de ordenação de espaços públicos ou em propriedades privadas alheias.');

insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (2,7);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (2,8);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (2,9);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (2,10);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (2,11);

-- Inserir atividades ambientais para POLUIÇÃO
INSERT INTO atividade (descricao) VALUES
    ('Causar poluição atmosférica ou híbrida.'),
    ('Dificultar ou impedir o uso público das praias.'),
    ('Realizar pesquisa, lavra ou extração de recursos minerais sem autorização legal.'),
    ('Produzir, processar, embalar, importar, exportar, comercializar, fornecer, transportar, armazenar, guardar, ter em depósito ou usar substância tóxica perigosa, ou nociva à saúde humana ou ao meio ambiente, em desacordo com as exigências estabelecidas.'),
    ('Construir, reformar, ampliar, instalar ou fazer funcionar, estabelecimentos, obras ou serviços potencialmente poluidores, sem licença.'),
    ('Disseminar doença ou praga que cause dano à agricultura, pecuária, fauna, flora e aos ecossistemas.');

insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,12);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,13);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,14);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,15);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,16);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (3,17);

-- Inserir atividades ambientais para ORDENAMENTO URBANO E PATRIMÔNIO CULTURAL
INSERT INTO atividade (descricao) VALUES
    ('Pixação em áreas urbanas.'),
    ('Alterar o aspecto ou estrutura bem como promover a construção em solo de locais protegidos em razão do seu valor paisagístico, ecológico, turístico, artístico, histórico, cultural, religioso, arqueológico, etnográfico ou monumental, sem autorização prévia da autoridade competente.'),
    ('Mineração, Ruído e Vibração Industrial.');

insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (4,18);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (4,19);
insert into categoria_atividade_ambientais (categoria_id,atividade_ambientais_id) values (4,20);

-- Inserir atividades ambientais para ADMINISTRAÇÃO AMBIENTAL
INSERT INTO atividade (descricao, categoria_id) VALUES
    ('Práticas como afirmações falsas ou enganosas.', 5),
    ('Concessões de licenças, autorizações ou permissões emitidas pelos funcionários, porém em desacordo com as normas ambientais.', 5);
