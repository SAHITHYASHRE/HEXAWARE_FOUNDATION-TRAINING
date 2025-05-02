--TASK 2

insert into venue 
values ('1','Marina','123, MG road, chennai'),
	   ('2','VR mall','456, kl road, chennai'),
       ('3','Phoenix','286, xy road, chennai'),
       ('4','Express Avenue','149, kk road, chennai'),
       ('5','Grand mall','241, ad road, chennai');
       
select * from venue;

insert into event_table
values ('01','Grand launch','2025-05-12','09:30:00','2','60','45','499.00','Sports'),
	   ('02','Opera','2025-06-14','10:30:00','1','100','70','799.00','Concert'),
       ('03','Morining Play','2025-05-19','09:00:00','4','50','30','699.00','Concert'),
       ('04','la la land','2025-04-22','11:30:00','3','100','20','299.00','Movie'),
       ('05','Inception','2025-05-13','08:30:00','5','60','55','999.00','Movie');
       
select * from event_table;

insert into customer
values('201','Praveen','praveen@gmail.com','9876543217'),
      ('202','Naveen','naveen@gmail.com','9876987321'),
      ('203','Harry','harry03@gmail.com','9876512317'),
      ('204','Shalini','shalini@gmail.com','9870003217'),
      ('205','John','johnny@gmail.com','9871113217');
      
select * from customer;


insert into booking
values('91','203','05','4','3996.00','2025-05-13'),
	  ('92','204','03','2','1398.00','2025-05-19'),
      ('93','205','02','1','799.00','2025-06-14'),
      ('94','202','01','3','1497.00','2025-05-12'),
      ('95','201','04','1','299.00','2025-04-22');
      
select * from booking;

select event_name from event_table; # 2

select event_name, available_seats from event_table where available_seats >= 1; # 3

select * from event_table where event_name like '%cup%'; # 4

select * from event_table where ticket_price between 1000.00 and 2500.00; # 5

select * from event_table where event_date between '2025-05-03' and '2025-06-01'; # 6

select event_name, available_seats from event_table where available_seats>0 and event_name like '%concert%'; # 7

select * from booking where num_tickets > 4 ; # 9

select * from customer where phone_no like '%000'; # 10 

select event_id, event_name, total_seats
from event_table
where total_seats > 15000
order by total_seats desc;   # 11

select * from event_table where event_name not like 'x%'
									and	event_name not like 'y%'
                                    and event_name not like 'z%'; # 12 
                                    
select * from customer limit 5 offset 5; # 8


-- TASK 3

select event_name, Avg(ticket_price) as Avg_price 
from event_table
group by event_name;  #1

select sum(total_cost) as revenue 
from booking;  #2

select event_name,max(total_cost) as Highest_sale 
from event_table 
inner join booking on event_table.event_id = booking.event_id
group by event_name
order by Highest_sale desc
limit 1;             #3

select event_id,event_name,(total_seats-available_seats) as tickets_sold from event_table; #4

select event_id,event_name from event_table where total_seats = available_seats; #5

select customer_name,max(num_tickets) as highest_sale 
from customer 
inner join booking 
on customer.customer_id = booking.customer_id 
group by customer_name
order by highest_sale desc 
limit 1;                  #6


select event_name,month(event_date) as each_month, num_tickets
from event_table 
join booking using (event_id)
order by each_month;  #7

SELECT 
  MONTH(e.event_date) AS month,
  SUM(b.num_tickets) AS total_tickets
FROM event_table e
JOIN booking b ON e.event_id = b.event_id
GROUP BY month
ORDER BY month;  #7

select venue_id,event_id,avg(ticket_price) as average_price
from venue
join event_table using (venue_id)
group by event_id
order by event_id; #8

select sum(total_seats-available_seats) as tickets_sold, event_type
from event_table 
group by event_type; #9

SELECT 
  e.event_type,
  SUM(b.num_tickets) AS total_tickets_sold
FROM event_table e
left JOIN booking b ON e.event_id = b.event_id
GROUP BY e.event_type
ORDER BY total_tickets_sold DESC; #9

select event_name, event_id, year(event_date) as each_year, sum(num_tickets * ticket_price) as revenue
from event_table
left join booking using (event_id)
group by each_year,event_id
order by each_year desc;  #10

SELECT 
  customer_id,
  customer_name,
  COUNT(DISTINCT event_id) AS event_count
FROM customer
JOIN booking using (customer_id)
GROUP BY customer_id, customer_name
HAVING event_count > 1;  #11
 
select customer_id, sum(ticket_price * num_tickets) as revenue
from booking
join event_table using (event_id)
group by customer_id
order by revenue desc; #12  

select venue_id,event_id,event_type,avg(ticket_price) as Avg_price 
from event_table
join venue using(venue_id) 
group by event_id
order by Avg_price desc; #13

SELECT 
  e.event_type,
  v.venue_name,
  AVG(e.ticket_price) AS average_ticket_price
FROM event_table e
JOIN venue v using (venue_id)
GROUP BY e.event_type, v.venue_name
ORDER BY e.event_type, v.venue_name; # 13

#month >= CURDATE() - INTERVAL 30 DAY;  #14

select * from booking;

SELECT 
  customer_id,
  SUM(num_tickets) AS total_tickets_purchased
FROM booking
WHERE booking_date >= DATE('2025-06-14') - INTERVAL 30 DAY
GROUP BY customer_id
ORDER BY total_tickets_purchased DESC; #14


-- TAsk 4

SELECT venue_id, 
       (SELECT AVG(ticket_price) 
        FROM event_table e 
        WHERE e.venue_id = v.venue_id) AS avg_ticket_price
FROM venue v;    #1

SELECT event_id, event_name
FROM event_table
WHERE (total_seats - available_seats) > (total_seats / 2);  #2

SELECT event_id, SUM(num_tickets) AS total_tickets_sold
FROM booking
GROUP BY event_id;  #3

SELECT customer_id, customer_name
FROM customer c
WHERE NOT EXISTS (
    SELECT 1 FROM booking b
    WHERE b.customer_id = c.customer_id
);  #4

SELECT event_id, event_name
FROM event_table
WHERE event_id NOT IN (
    SELECT DISTINCT event_id FROM booking
);   #5

SELECT e.event_type, SUM(b.num_tickets) AS total_tickets_sold
FROM (
    SELECT event_id, event_type
    FROM event_table
) e
JOIN booking b ON e.event_id = b.event_id
GROUP BY e.event_type;   #6

SELECT event_id, event_name, ticket_price
FROM event_table
WHERE ticket_price > (
    SELECT AVG(ticket_price) FROM event_table
);   #7

SELECT c.customer_id, c.customer_name,
       (SELECT SUM(b.total_cost)
        FROM booking b
        WHERE b.customer_id = c.customer_id) AS total_revenue
FROM customer c;  #8
 
 SELECT customer_id, customer_name
FROM customer
WHERE customer_id IN (
    SELECT b.customer_id
    FROM booking b
    JOIN event_table e ON b.event_id = e.event_id
    WHERE e.venue_id = 1  #9
);


SELECT event_type, SUM(total_tickets) AS total_tickets
FROM (
    SELECT e.event_type, SUM(b.num_tickets) AS total_tickets
    FROM booking b
    JOIN event_table e ON b.event_id = e.event_id
    GROUP BY e.event_id, e.event_type
) AS event_tickets
GROUP BY event_type;  #10

SELECT DISTINCT customer_id, customer_name
FROM customer c
WHERE customer_id IN (
    SELECT b.customer_id
    FROM booking b
    WHERE DATE_FORMAT(b.booking_date, '%Y-%m') = '2024-05' -- 
);   #11

SELECT venue_id, 
       (SELECT AVG(ticket_price) 
        FROM event_table e 
        WHERE e.venue_id = v.venue_id) AS avg_ticket_price
FROM venue v;    #12
