
create table books (
       id integer not null,
        description varchar(255),
        item varchar(255),
        primary key (id)
    );

INSERT INTO books (id, item, description)
  VALUES
      (1, 'Camel',    'Camel in Action'),
      (2, 'ActiveMQ', 'ActiveMQ in Action');
