
CREATE KEYSPACE my_keyspace
  WITH REPLICATION = { 
   'class' : 'SimpleStrategy', 
   'replication_factor' : 1 
  };

USE my_keyspace;

CREATE TABLE IF NOT EXISTS users (
    user_id uuid PRIMARY KEY,
    username text,
    password text,
    email text,
    created_at timestamp
);

CREATE TABLE IF NOT EXISTS notes (
    indext int,
    note_id uuid,
    user_id uuid,
    title text,
    content text,
    tags set <text>,
    category text,
    created_at timestamp,
    modified_at timestamp,
    PRIMARY KEY((user_id,category),indext, note_id)
);