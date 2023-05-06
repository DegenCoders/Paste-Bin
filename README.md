# Paste-Bin

## Installation
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
    note_id uuid PRIMARY KEY,
    user_id uuid,
    title text,
    content text,
    tags set<text>,
    category text,
    created_at timestamp,
    modified_at timestamp
);

</code>