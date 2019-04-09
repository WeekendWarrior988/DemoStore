INSERT INTO clients (name, city, discount) VALUES('Google', 'Los Angeles', 25);
INSERT INTO clients (name, city, discount) VALUES('Microsoft', 'Washington', 10);
INSERT INTO clients (name, city, discount) VALUES('UBER', 'New York', 10);
INSERT INTO clients (name, city, discount) VALUES('Netflix', 'Chicago', 10);

INSERT INTO products(name, price, description, image) VALUES('Windows 3.11', 9.99, 'MS Windows 3.11', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIsNypuo6W50AdB6Bnz1c5Nz2iZxdvHB1-bIl8mghBs1gEWzlTOw');
INSERT INTO products(name, price, description, image) VALUES('Windows XP', 19.99, 'MS Windows XP', 'https://pronto-core-cdn.prontomarketing.com/2/wp-content/uploads/sites/1346/2016/05/Windows-XP-Wallpaper.jpg');
INSERT INTO products(name, price, description, image) VALUES('Windows 10', 99.99, 'MS Windows 10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOBr9pTD4uqdBOAtiJprVTFuYveN7HuqYbdZM2sgMx_g2mvMj5');
INSERT INTO products(name, price, description, image) VALUES('Windows 7', 9.99, 'MS Windows 7', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCJzcw3BLJYIjDH52B2NQZ5qsZrr607OYUF_pv9TRH6dAruglV');
INSERT INTO products(name, price, description, image) VALUES('Windows Vista', 19.99, 'MS Windows vista', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDPpzMhO6_Sj9cNEykYhYcP_GJ-rRlpcwvKONoM2K8NIN4YrANNw');
INSERT INTO products(name, price, description, image) VALUES('Windows 10', 99.99, 'MS Windows 10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOBr9pTD4uqdBOAtiJprVTFuYveN7HuqYbdZM2sgMx_g2mvMj5');


INSERT INTO invoices(date, number, sum, client_id) VALUES('2018-09-10', 'INV 00120', 129.97, 1);
INSERT INTO inv_lines(invoice_id, product_id, price, qty) VALUES(1, 3, 99.99, 10);
INSERT INTO inv_lines(invoice_id, product_id, price, qty) VALUES(1, 1, 9.99, 100);

INSERT INTO invoices(date, number, sum, client_id) VALUES('2018-09-26', 'INV 00128', 129.97, 1);
INSERT INTO inv_lines(invoice_id, product_id, price, qty) VALUES(1, 3, 99.99, 10);
INSERT INTO inv_lines(invoice_id, product_id, price, qty) VALUES(1, 1, 9.99, 100);

# INSERT INTO users(username, secret, role) VALUES ('vardas@firma.lt', '$2a$12$DWh1PyJsP6JzFzP0VDrPcOzOFHiI260u.2Zx5ksbFpQ/KW4t1xDKK', 'ADMIN');
#
# INSERT INTO carts(number, sum) VALUES ('5524', 19.99);
# INSERT INTO cart_lines (cart_id, product_id, qty, price) values (1, 4, 2, 9.99);
# INSERT INTO cart_lines (cart_id, product_id, qty, price) values (1, 2, 1, 19.99);
# INSERT INTO cart_lines (cart_id, product_id, qty, price) values (1, 1, 1, 9.99);

