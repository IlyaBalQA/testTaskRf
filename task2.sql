--1
SELECT fio where
FROM client
WHERE email IS NULL OR email = '';

--2
SELECT name, price
FROM product
WHERE price=
        (SELECT MAX(price)
         FROM product);
--OR
SELECT name, price
FROM product
ORDER BY price DESC
LIMIT 1;
--3
SELECT name
FROM product
WHERE id
NOT IN (SELECT product_id
        FROM client_and_product);