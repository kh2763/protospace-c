ALTER TABLE prototype ADD COLUMN user_id INTEGER;
ALTER TABLE prototype 
ADD CONSTRAINT fk_prototype_user_id 
FOREIGN KEY (user_id) REFERENCES users(id);