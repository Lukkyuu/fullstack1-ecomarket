INSERT INTO categories (name, description) VALUES
('Alimentos Orgánicos', 'Alimentos producidos sin pesticidas ni químicos sintéticos.'),
('Hogar Sostenible', 'Productos ecológicos y reutilizables para el hogar.'),
('Cuidado Personal Eco', 'Productos de aseo y cuidado personal biodegradables y naturales.');

INSERT INTO products (name, description, price, stock, category_id) VALUES
('Miel de Abeja Orgánica 500g', 'Miel 100% pura y orgánica cosechada localmente.', 6500.00, 50, 1),
('Quinoa Orgánica 1kg', 'Superalimento orgánico rico en proteínas.', 4800.00, 100, 1),
('Cepillo de Dientes de Bambú', 'Cepillo biodegradable con cerdas de carbón activado.', 2500.00, 200, 3),
('Champú Sólido de Ortiga', 'Champú zero-waste libre de parabenos y sulfatos.', 5900.00, 80, 3),
('Bolsa de Algodón Orgánico', 'Bolsa reutilizable para compras diarias.', 3000.00, 150, 2),
('Detergente Biodegradable 1L', 'Limpia profundamente cuidando el agua de desecho.', 4500.00, 30, 2);
