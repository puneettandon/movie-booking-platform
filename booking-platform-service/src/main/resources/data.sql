-- =========================
-- CITY
-- =========================
INSERT INTO cities (id, name, country) VALUES (1, 'Bangalore', 'India');

-- =========================
-- THEATRES
-- =========================
INSERT INTO theatres (id, name, address, city_id)
VALUES (1, 'PVR Phoenix', 'Whitefield, Bangalore', 1);

INSERT INTO theatres (id, name, address, city_id)
VALUES (2, 'INOX Forum', 'Koramangala, Bangalore', 1);

-- =========================
-- SCREENS
-- =========================
INSERT INTO screens (id, name, total_seats, theatre_id)
VALUES (1, 'Audi 1', 6, 1);

INSERT INTO screens (id, name, total_seats, theatre_id)
VALUES (2, 'Audi 2', 6, 2);

-- =========================
-- SEATS (Screen 1)
-- =========================
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (1, 'A1', 'A', 'REGULAR', 1);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (2, 'A2', 'A', 'REGULAR', 1);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (3, 'A3', 'A', 'REGULAR', 1);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (4, 'B1', 'B', 'PREMIUM', 1);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (5, 'B2', 'B', 'PREMIUM', 1);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (6, 'B3', 'B', 'PREMIUM', 1);

-- =========================
-- SEATS (Screen 2)
-- =========================
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (7, 'A1', 'A', 'REGULAR', 2);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (8, 'A2', 'A', 'REGULAR', 2);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (9, 'A3', 'A', 'REGULAR', 2);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (10, 'B1', 'B', 'PREMIUM', 2);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (11, 'B2', 'B', 'PREMIUM', 2);
INSERT INTO seats (id, seat_number, seat_row, seat_type, screen_id) VALUES (12, 'B3', 'B', 'PREMIUM', 2);

-- =========================
-- MOVIES
-- =========================
INSERT INTO movies (id, title, language, genre, duration_minutes, certificate, release_date)
VALUES (1, 'Dune 2', 'English', 'Sci-Fi', 166, 'UA', '2026-03-01');

INSERT INTO movies (id, title, language, genre, duration_minutes, certificate, release_date)
VALUES (2, 'Animal', 'Hindi', 'Action', 180, 'A', '2026-02-15');

-- =========================
-- SHOWS
-- =========================
INSERT INTO shows (id, movie_id, screen_id, show_date, start_time, end_time, language, base_price, status)
VALUES (1, 1, 1, CURRENT_DATE, '13:00:00', '15:46:00', 'English', 250.00, 'ACTIVE');

INSERT INTO shows (id, movie_id, screen_id, show_date, start_time, end_time, language, base_price, status)
VALUES (2, 1, 2, CURRENT_DATE, '19:00:00', '21:46:00', 'English', 300.00, 'ACTIVE');

INSERT INTO shows (id, movie_id, screen_id, show_date, start_time, end_time, language, base_price, status)
VALUES (3, 2, 1, CURRENT_DATE, '16:00:00', '19:00:00', 'Hindi', 220.00, 'ACTIVE');

-- =========================
-- SHOW SEAT INVENTORY FOR SHOW 1 (Screen 1 seats)
-- =========================
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (1, 1, 1, 'AVAILABLE', 250.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (2, 1, 2, 'AVAILABLE', 250.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (3, 1, 3, 'AVAILABLE', 250.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (4, 1, 4, 'AVAILABLE', 350.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (5, 1, 5, 'BOOKED',    350.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (6, 1, 6, 'AVAILABLE', 350.00, NULL);

-- =========================
-- SHOW SEAT INVENTORY FOR SHOW 2 (Screen 2 seats)
-- =========================
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (7, 2, 7, 'AVAILABLE', 300.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (8, 2, 8, 'AVAILABLE', 300.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (9, 2, 9, 'AVAILABLE', 300.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (10, 2, 10, 'AVAILABLE', 400.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (11, 2, 11, 'AVAILABLE', 400.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (12, 2, 12, 'AVAILABLE', 400.00, NULL);

-- =========================
-- SHOW SEAT INVENTORY FOR SHOW 3 (Screen 1 seats reused)
-- =========================
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (13, 3, 1, 'AVAILABLE', 220.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (14, 3, 2, 'AVAILABLE', 220.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (15, 3, 3, 'BOOKED',    220.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (16, 3, 4, 'AVAILABLE', 320.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (17, 3, 5, 'AVAILABLE', 320.00, NULL);
INSERT INTO show_seat_inventory (id, show_id, seat_id, status, price, lock_until) VALUES (18, 3, 6, 'AVAILABLE', 320.00, NULL);