# Paste-Bin

## Installation
For creating a keyspace

<code>
CREATE KEYSPACE my_keyspace
  WITH REPLICATION = { 
   'class' : 'SimpleStrategy', 
   'replication_factor' : 1 
  };
</code>

For creating a user table

<code>
CREATE TABLE IF NOT EXISTS users (
    user_id uuid PRIMARY KEY,
    username text,
    password text,
    email text,
    created_at timestamp
);
</code>

For creating a notes table

<code>
CREATE TABLE IF NOT EXISTS notes (
    indext int,
    note_id uuid,
    user_id uuid,
    title text,
    content text,
    tags set'&lt;text&gt;',
    category text,
    created_at timestamp,
    modified_at timestamp,
    PRIMARY KEY((user_id,category),indext, note_id)
);

</code>