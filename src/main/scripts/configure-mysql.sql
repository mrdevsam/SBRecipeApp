CREATE DATABASE recipe_app_dev;
CREATE DATABASE recipe_app_prod;


CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'%' IDENTIFIED BY 'password';


GRANT SELECT ON recipe_app_dev.* to 'dev_user'@'localhost';
GRANT INSERT ON recipe_app_dev.* to 'dev_user'@'localhost';
GRANT DELETE ON recipe_app_dev.* to 'dev_user'@'localhost';
GRANT UPDATE ON recipe_app_dev.* to 'dev_user'@'localhost';
GRANT SELECT ON recipe_app_prod.* to 'prod_user'@'localhost';
GRANT INSERT ON recipe_app_prod.* to 'prod_user'@'localhost';
GRANT DELETE ON recipe_app_prod.* to 'prod_user'@'localhost';
GRANT UPDATE ON recipe_app_prod.* to 'prod_user'@'localhost';
GRANT SELECT ON recipe_app_dev.* to 'dev_user'@'%';
GRANT INSERT ON recipe_app_dev.* to 'dev_user'@'%';
GRANT DELETE ON recipe_app_dev.* to 'dev_user'@'%';
GRANT UPDATE ON recipe_app_dev.* to 'dev_user'@'%';
GRANT SELECT ON recipe_app_prod.* to 'prod_user'@'%';
GRANT INSERT ON recipe_app_prod.* to 'prod_user'@'%';
GRANT DELETE ON recipe_app_prod.* to 'prod_user'@'%';
GRANT UPDATE ON recipe_app_prod.* to 'prod_user'@'%';